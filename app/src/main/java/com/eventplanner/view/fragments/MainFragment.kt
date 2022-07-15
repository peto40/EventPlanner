package com.eventplanner.view.fragments

import android.os.Bundle
import android.view.*
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eventplanner.R
import com.eventplanner.databinding.FragmentMainBinding
import com.eventplanner.di.DaggerAppComponent
import com.eventplanner.model.EventModel
import com.eventplanner.view.activities.MainActivity
import com.eventplanner.view.adapters.EventRecyclerAdapter
import com.eventplanner.viewmodel.SharedViewModel


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: EventRecyclerAdapter

    private val appComponent by lazy {
        DaggerAppComponent.builder()
            .application(requireActivity().application)
            .context(requireContext())
            .build()
    }

    private val mSharedViewModel by viewModels<SharedViewModel>({ activity as MainActivity }) {
        appComponent.viewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        adapter = EventRecyclerAdapter(context)

        binding.recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
        adapter.setOnClickListener(object : EventRecyclerAdapter.OnClickListener {
            override fun onItemClick(position: Int, items: MutableList<EventModel>) {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDisplayFragment(
                        items[position]
                    )
                )
            }

            override fun onUpdateClick(position: Int, items: MutableList<EventModel>) {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToUpdateFragment(
                        items[position]
                    )
                )
            }

            override fun onChooseStateClick(
                position: Int,
                items: MutableList<EventModel>,
                view: RadioButton
            ) {
                val eventName = items[position].eventName
                val eventDescription = items[position].description
                val location = items[position].location
                val date = items[position].date
                val time = items[position].time
                val weatherDescription = items[position].weatherDescription
                val weatherIcon = items[position].icon

                when (view.id) {
                    R.id.radio_visited -> {
                        val updatedEvent = EventModel(
                            date,
                            time,
                            weatherDescription,
                            eventName,
                            eventDescription,
                            location,
                            weatherIcon,
                            state = true
                        )
                        updatedEvent.id = items[position].id
                        mSharedViewModel.updateEvent(updatedEvent)
                        Toast.makeText(
                            requireContext(),
                            "Event change to Visited",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    R.id.radio_missed -> {
                        val updatedEvent = EventModel(
                            date,
                            time,
                            weatherDescription,
                            eventName,
                            eventDescription,
                            location,
                            weatherIcon,
                            state = false
                        )
                        updatedEvent.id = items[position].id
                        mSharedViewModel.updateEvent(updatedEvent)
                        Toast.makeText(
                            requireContext(),
                            "Event change to Missed",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            }

        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSharedViewModel.allEventLiveData.observe(viewLifecycleOwner) { item ->
            adapter.setItems(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_event_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_event -> {
                findNavController().navigate(R.id.action_mainFragment_to_addEventFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}