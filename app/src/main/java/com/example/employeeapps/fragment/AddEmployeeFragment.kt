package com.example.employeeapps.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.employeeapps.MainActivity
import com.example.employeeapps.R
import com.example.employeeapps.databinding.FragmentAddEmployeeBinding
import com.example.employeeapps.model.Employee
import com.example.employeeapps.viewmodel.EmployeeViewModel

class AddEmployeeFragment : Fragment(R.layout.fragment_add_employee), MenuProvider {

    //Declare binding
    private var addEmployeeBinding: FragmentAddEmployeeBinding? = null
    private val binding get() = addEmployeeBinding!!

    //Declare ViewModel
    private lateinit var employeeViewModel: EmployeeViewModel

    //Declare View
    private lateinit var addEmployee: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addEmployeeBinding = FragmentAddEmployeeBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        employeeViewModel = (activity as MainActivity).employeeViewModel
        addEmployee = view
    }

    private fun saveEmployee(view: View){
        val firstName = binding.addEmployeeFirstName.text.toString().trim()
        val lastName = binding.addEmployeeLastName.text.toString().trim()
        val role = binding.addEmployeeRole.text.toString().trim()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && role.isNotEmpty()){
            val employee = Employee(0, firstName, lastName, role)
            employeeViewModel.addEmployee(employee)

            Toast.makeText(activity, "Employee Saved Successfully", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment, false)
        } else {
            Toast.makeText(context,"Please Fill All Fields",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_employee, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.saveMenu -> {
                saveEmployee(addEmployee)
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addEmployeeBinding = null
    }

}