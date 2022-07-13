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
import com.eventplanner.utils.ViewModelProviderFactory
import com.eventplanner.viewmodel.SharedViewModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

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

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private lateinit var mSharedViewModel: SharedViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        //mSharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        setDateAndTimeListeners()

        binding.etUpdatedEventName.setText(args.currentEvent.eventName)
        binding.etUpdatedDescription.setText(args.currentEvent.description)
        binding.etUpdatedLocation.setText(args.currentEvent.location)
        binding.tvUpdatedDate.text = args.currentEvent.date
        binding.tvUpdatedTime.text = args.currentEvent.time
        binding.updatedWeatherDescription.text = args.currentEvent.weather

        Picasso.get()
            .load(args.currentEvent.icon)
            .into(binding.ivUpdatedIcon)

        binding.btnUpdate.setOnClickListener {

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
            updatedEvent.id = args.currentEvent.id
            mSharedViewModel.updateEvent(updatedEvent)
            findNavController().popBackStack()
        }

        binding.getWeatherBtn.setOnClickListener {
            val location = binding.etUpdatedLocation.text.toString()
            mSharedViewModel.getWeatherData(location)
        }

        return binding.root
    }

//    private fun inputCheck(
//        eventName: String,
//        eventDescription: String,
//        location: String,
//        date: String,
//        time: String,
//        weatherDescription: String,
//    ): Boolean {
//        return ((eventName == "")
//                || (eventDescription == "")
//                || (location == "")
//                || (date == "")
//                || (time == "")
//                || (weatherDescription == ""))
//    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()

        mSharedViewModel.weatherLiveData.observe(viewLifecycleOwner) { data ->
            data?.let {
                val temperature = (data.main.temp - 273.15).toFloat().toString()
                binding.weatherTemperature.text = "$temperature ºC"
                binding.updatedWeatherDescription.text = data.weather[0].description
                binding.weatherClouds.text = "${data.visibility} m"
                binding.weatherWind.text = "${data.wind.speed} m/h"

                Picasso.get()
                    .load("https://openweathermap.org/img/wn/" + data.weather[0].icon + "@2x.png")
                    .into(binding.ivUpdatedIcon)
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
            mSharedViewModel.deleteEventFromDB(args.currentEvent)
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

    private fun setupViewModel() {
        mSharedViewModel = ViewModelProvider(this, providerFactory)[SharedViewModel::class.java]
    }
}






