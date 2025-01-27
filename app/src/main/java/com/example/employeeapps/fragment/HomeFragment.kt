package com.example.employeeapps.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.employeeapps.MainActivity
import com.example.employeeapps.R
import com.example.employeeapps.adapter.EmployeeAdapter
import com.example.employeeapps.databinding.FragmentHomeBinding
import com.example.employeeapps.model.Employee
import com.example.employeeapps.viewmodel.EmployeeViewModel

class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener, MenuProvider {

    //Declare binding
    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!

    //Declare ViewModel
    private lateinit var employeeViewModel: EmployeeViewModel

    //Declare Adapter
    private lateinit var employeeAdapter: EmployeeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        //ViewModel
        employeeViewModel =(activity as MainActivity).employeeViewModel
        setupHomeRecycleView()

        //FAB Button
        binding.addEmployeeFab.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_addEmployeeFragment)
        }

        // Swipe Refresh
        binding.swiperRefresh.setOnRefreshListener {
            setupHomeRecycleView()
            binding.swiperRefresh.isRefreshing = false
        }
    }

    private fun updateUI(note: List<Employee>?){
        if (note != null){
            if (note.isNotEmpty()){
                binding.emptyNotesBackground.visibility = View.GONE
                binding.swiperRefresh.visibility = View.VISIBLE
            }
            else{
                binding.emptyNotesBackground.visibility = View.VISIBLE
                binding.swiperRefresh.visibility = View.GONE
            }
        }
    }

    private fun setupHomeRecycleView(){
        employeeAdapter = EmployeeAdapter()
        binding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = employeeAdapter
        }

        activity.let {
            employeeViewModel.getAllNotes().observe(viewLifecycleOwner){
                    note->employeeAdapter.differ.submitList(note)
                updateUI(note)
            }
        }
    }

    private fun searchEmployee(query: String?){
        val searchQuery = "%$query%"

        employeeViewModel.searchNote(searchQuery).observe(this){
                list -> employeeAdapter.differ.submitList(list)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null){
            searchEmployee(newText)
        }
        return true
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu,menu)

        val menuSearch = menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }
}