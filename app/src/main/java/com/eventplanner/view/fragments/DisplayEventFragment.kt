package com.eventplanner.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.eventplanner.databinding.FragmentDisplayEventBinding
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment

class DisplayEventFragment : Fragment() {

    private val args by navArgs<DisplayEventFragmentArgs>()


    private var _binding: FragmentDisplayEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDisplayEventBinding.inflate(inflater, container, false)


        binding.displayEventName.text = args.currentEvent.eventName
        binding.displayDirection.text = args.currentEvent.description
        binding.displayLocation.text = args.currentEvent.location
        binding.displayDate.text = args.currentEvent.date
        binding.displayTime.text = args.currentEvent.time
        binding.weather.text = args.currentEvent.weather

        Picasso.get().load(args.currentEvent.icon).into(binding.ivWeather)

        binding.id.text = args.currentEvent.id.toString()



        return binding.root
    }


}