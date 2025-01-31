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
import com.example.employeeapps.database.DuplicateCheckCallback
import com.example.employeeapps.databinding.FragmentAddEmployeeBinding
import com.example.employeeapps.model.Employee
import com.example.employeeapps.viewmodel.EmployeeViewModel
import java.util.regex.Pattern

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

        if (firstName.isEmpty() || lastName.isEmpty() || role.isEmpty()) {
            Toast.makeText(context,"All Fields Required",Toast.LENGTH_SHORT).show()
            return
        }
        if (!isValidName(firstName)) {
            Toast.makeText(context,"First name must contain only alphabetic characters",Toast.LENGTH_SHORT).show()
            return
        }
        if (!isValidName(lastName)) {
            Toast.makeText(context,"Last name must contain only alphabetic characters",Toast.LENGTH_SHORT).show()
            return
        }

        // Check for duplicate employee
        isDuplicateEmployee(firstName, lastName, object : DuplicateCheckCallback {
            override fun onResult(isDuplicate: Boolean) {
                if (isDuplicate) {
                    // If duplicate, do not proceed with saving
                    Toast.makeText(context,"An employee with the same name already exists",Toast.LENGTH_SHORT).show()
                }else{
                    // If not a duplicate, proceed to save the employee
                    val employee = Employee(0, firstName, lastName, role)
                    employeeViewModel.addEmployee(employee)

                    Toast.makeText(activity, "Employee Saved Successfully", Toast.LENGTH_SHORT).show()
                    view.findNavController().popBackStack(R.id.homeFragment, false)
                }
            }
        })
    }

    private fun isValidName(name: String): Boolean {
        val pattern = Pattern.compile("^[a-zA-Z]+$")
        return pattern.matcher(name).matches()
    }

    private fun isDuplicateEmployee(firstName: String, lastName: String, callback: DuplicateCheckCallback) {
        employeeViewModel.doesEmployeeExist(firstName, lastName).observe(viewLifecycleOwner) { exist ->
            callback.onResult(exist) // Call the callback with the result
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