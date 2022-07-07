package com.eventplanner.view.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.*
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eventplanner.R
import com.eventplanner.databinding.FragmentUpdateBinding
import com.eventplanner.model.models.EventModel
import com.eventplanner.view.adapters.EventRecyclerAdapter
import com.eventplanner.viewmodel.SharedViewModel
import com.squareup.picasso.Picasso

class UpdateFragment : Fragment(),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    var day = 0
    var month = 0
    var year = 0
    var minute = 0
    var hour = 0
    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedminute = 0
    var savedhour = 0
    val calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun setDateAndTimeListeners() {
        with(binding) {
            dateBtn.setOnClickListener {
                day = calendar.get(android.icu.util.Calendar.DAY_OF_MONTH)
                month = calendar.get(android.icu.util.Calendar.MONTH)
                year = calendar.get(android.icu.util.Calendar.YEAR)
                DatePickerDialog(requireContext(), this@UpdateFragment, year, month, day).show()
            }
            timeBtn.setOnClickListener {
                TimePickerDialog(requireContext(), this@UpdateFragment, hour, minute, true).show()
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month + 1
        savedYear = year
        binding.tvUpdatedDate.text = "$savedDay-$savedMonth-$savedYear"
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        savedhour = p1
        savedminute = p2
        binding.tvUpdatedTime.text = "$savedhour:$savedminute:00 "
    }


    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var eventRecyclerAdapter: EventRecyclerAdapter
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        eventRecyclerAdapter = EventRecyclerAdapter(requireContext())
        setDateAndTimeListeners()

        binding.etUpdatedEventName.setText(args.currentEvent.eventName)
        binding.etUpdatedDescription.setText(args.currentEvent.description)
        binding.etUpdatedLocation.setText(args.currentEvent.location)
        binding.tvUpdatedDate.text = args.currentEvent.date
        binding.tvUpdatedTime.text = args.currentEvent.time
        binding.updatedWeatherDescription.text = args.currentEvent.weather

        Picasso.get()
            .load(args.currentEvent.icon)
            .into(binding.ivIcon)

        binding.btnUpdate.setOnClickListener {
            updateCurrentEvent()
        }
        binding.getWeatherBtn.setOnClickListener {
            val location = binding.etUpdatedLocation.text.toString()
            sharedViewModel.getWeatherData(location)
        }

        return binding.root
    }

    private fun updateCurrentEvent() {
        val eventName = binding.etUpdatedEventName.text.toString()
        val eventDescription = binding.etUpdatedDescription.text.toString()
        val location = binding.etUpdatedLocation.text.toString()
        val date = binding.tvUpdatedDate.text.toString()
        val time = binding.tvUpdatedTime.text.toString()
        val weatherDescription = binding.updatedWeatherDescription.text.toString()
        val weatherIcon = args.currentEvent.icon

        val updatedEvent = EventModel(
            date,
            time,
            weatherDescription,
            eventName,
            eventDescription,
            location,
            weatherIcon
        )

        sharedViewModel.updateEvent(updatedEvent)

        if (inputCheck(
                eventName,
                eventDescription,
                location,
                date,
                time,
                weatherDescription
            )
        ) {
            sharedViewModel.updateEvent(updatedEvent)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_mainFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_LONG).show()
        }


    }

    private fun inputCheck(
        eventName: String,
        eventDescription: String,
        location: String,
        date: String,
        time: String,
        weatherDescription: String,
    ): Boolean {
        return ((eventName == "")
                || (eventDescription == "")
                || (location == "")
                || (date == "")
                || (time == "")
                || (weatherDescription == ""))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.weatherLiveData.observe(viewLifecycleOwner) { data ->
            data?.let {
                val temperature = (data.main.temp - 273.15).toFloat().toString()
                binding.weatherTemperature.text = "$temperature ºC"
                binding.updatedWeatherDescription.text = data.weather[0].description
                binding.weatherClouds.text = "${data.visibility} m"
                binding.weatherWind.text = "${data.wind.speed} m/h"

                Picasso.get()
                    .load("https://openweathermap.org/img/wn/" + data.weather[0].icon + "@2x.png")
                    .into(binding.ivIcon)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_event_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_event) {
            deleteEvent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteEvent() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            sharedViewModel.deleteEventFromDB(args.currentEvent)
            Toast.makeText(
                requireContext(),
                "Successfully removed" + " ${args.currentEvent.eventName}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().popBackStack()
        }
        builder.setNegativeButton("No") { _, _ -> }

        builder.setTitle("Delete  ${args.currentEvent.eventName}?")
        builder.setMessage("Are you sure you want to delete ${args.currentEvent.eventName}?")
        builder.create().show()
    }
}






