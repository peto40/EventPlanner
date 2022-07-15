package com.eventplanner.view.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eventplanner.databinding.FragmentAddEventBinding
import com.eventplanner.di.DaggerAppComponent
import com.eventplanner.model.EventModel
import com.eventplanner.view.activities.MainActivity
import com.eventplanner.view.adapters.EventRecyclerAdapter
import com.eventplanner.viewmodel.SharedViewModel
import com.squareup.picasso.Picasso

class AddEventFragment : Fragment(),
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

    private val component by lazy {
        DaggerAppComponent.builder()
            .application(requireActivity().application)
            .context(requireContext())
            .build()
    }

    private val mSharedViewModel by viewModels<SharedViewModel> ({ activity as MainActivity }) {
        component.viewModelFactory()
    }

    private var _binding: FragmentAddEventBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: EventRecyclerAdapter

    private var iconName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEventBinding.inflate(layoutInflater, container, false)
        adapter = EventRecyclerAdapter(context)
        setDateAndTimeListeners()

        fun getWeather(location: String) {
            mSharedViewModel.getWeatherData(location)
        }

        fun setIconURIForDispatch(iconName: String) = "https://openweathermap.org/img/wn/$iconName@2x.png"

        fun addEvent() {
            val eventName = binding.etEventName.text.toString()
            val description = binding.etDescription.text.toString()
            val weather = binding.weatherDescription.text.toString()
            val date = binding.tvDate.text.toString()
            val time = binding.tvTime.text.toString()
            val location = binding.etLocation.text.toString()
            val icon = setIconURIForDispatch(iconName)

            val simpleItem = EventModel(
                date,
                time,
                weather,
                eventName,
                description,
                location,
                icon
            )
            if (!inputCheck(eventName, description, location, date, time, weather)) {
                mSharedViewModel.addItemListToDB(simpleItem)
                findNavController().popBackStack()
                Toast.makeText(requireContext(), "Adding Successfully!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_LONG)
                    .show()
            }
        }

        binding.getWeatherBtn.setOnClickListener {
            val location = binding.etLocation.text.toString()
            getWeather(location)
            binding.cardViewDescription.visibility = View.VISIBLE
        }

        binding.btnAddEvent.setOnClickListener {
            addEvent()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()
    }

    private fun observers() {
        mSharedViewModel.weatherLiveData.observe(viewLifecycleOwner) { data ->
            data?.let {
                val temperature = (data.main.temp - 273.15).toFloat().toString()
                binding.weatherTemperature.text = "$temperature ºC"
                binding.weatherDescription.text = data.weather[0].description
                binding.weatherClouds.text = "${data.visibility} m"
                binding.weatherWind.text = "${data.wind.speed} m/h"
                iconName = data.weather[0].icon
                Picasso.get()
                    .load("https://openweathermap.org/img/wn/" + data.weather[0].icon + "@2x.png")
                    .into(binding.ivIcon)
            }
        }
    }

    private fun setDateAndTimeListeners() {
        with(binding) {
            dateBtn.setOnClickListener {
                day = calendar.get(Calendar.DAY_OF_MONTH)
                month = calendar.get(Calendar.MONTH)
                year = calendar.get(Calendar.YEAR)
                DatePickerDialog(requireContext(), this@AddEventFragment, year, month, day).show()
            }
            timeBtn.setOnClickListener {
                TimePickerDialog(requireContext(), this@AddEventFragment, hour, minute, true).show()
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month + 1
        savedYear = year
        binding.tvDate.text = "$savedDay-$savedMonth-$savedYear"
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        savedhour = p1
        savedminute = p2
        binding.tvTime.text = "$savedhour:$savedminute:00 "
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
}
