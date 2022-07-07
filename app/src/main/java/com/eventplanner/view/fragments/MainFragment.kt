package com.eventplanner.view.fragments

import android.app.AlertDialog
import android.app.DirectAction
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.eventplanner.R
import com.eventplanner.databinding.FragmentMainBinding
import com.eventplanner.model.models.EventModel
import com.eventplanner.view.adapters.EventRecyclerAdapter
import com.eventplanner.viewmodel.SharedViewModel


class MainFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: EventRecyclerAdapter
    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        adapter = EventRecyclerAdapter(context)

        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]


        binding.recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
        adapter.setOnClickListener(object : EventRecyclerAdapter.OnClickListener {
            override fun onItemClick(position: Int, items: MutableList<EventModel>) {
                val action = MainFragmentDirections.actionMainFragmentToDisplayFragment(items[position])
                findNavController().navigate(action)
            }

            override fun onSettingsClick(position: Int, items: MutableList<EventModel>) {
                val action = MainFragmentDirections.actionMainFragmentToUpdateFragment(items[position])
                findNavController().navigate(action)            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.allEventLiveData.observe(viewLifecycleOwner) { item ->
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