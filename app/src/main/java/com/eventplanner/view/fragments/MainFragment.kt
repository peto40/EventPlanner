package com.eventplanner.view.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eventplanner.R
import com.eventplanner.databinding.FragmentMainBinding
import com.eventplanner.model.models.EventModel
import com.eventplanner.utils.ViewModelProviderFactory
import com.eventplanner.view.adapters.EventRecyclerAdapter
import com.eventplanner.viewmodel.SharedViewModel
import javax.inject.Inject


class MainFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: EventRecyclerAdapter

//        private val mSharedViewModel: SharedViewModel by viewModels {
//        ShareViewModelFactory(
//            requireActivity().application,
//            (requireActivity().application as MyApplication).repository)
//    }
    private lateinit var mSharedViewModel: SharedViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        adapter = EventRecyclerAdapter(context)

       // mSharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]



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

            override fun onSettingsClick(position: Int, items: MutableList<EventModel>) {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToUpdateFragment(
                        items[position]
                    )
                )
            }

        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()


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

    private fun setupViewModel() {
        mSharedViewModel = ViewModelProvider(this, providerFactory)[SharedViewModel::class.java]
    }
}