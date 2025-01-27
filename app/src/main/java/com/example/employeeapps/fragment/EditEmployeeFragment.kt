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
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.employeeapps.MainActivity
import com.example.employeeapps.R
import com.example.employeeapps.databinding.FragmentEditEmployeeBinding
import com.example.employeeapps.model.Employee
import com.example.employeeapps.viewmodel.EmployeeViewModel


class EditEmployeeFragment : Fragment(R.layout.fragment_edit_employee), MenuProvider {
    // Declare Binding
    private var editEmployeeBinding: FragmentEditEmployeeBinding? = null
    private val binding get() = editEmployeeBinding!!

    // Declare ViewModel
    private lateinit var employeeViewModel: EmployeeViewModel

    private lateinit var currentEmployee: Employee

    private val args: EditEmployeeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editEmployeeBinding = FragmentEditEmployeeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        employeeViewModel = (activity as MainActivity).employeeViewModel
        currentEmployee = args.employee!!

        binding.editEmployeeFirstName.setText(currentEmployee.firstName)
        binding.editEmployeeLastName.setText(currentEmployee.lastName)
        binding.addEmployeeRole.setText(currentEmployee.role)

        binding.editEmployeeFab.setOnClickListener {
            val firstName = binding.editEmployeeFirstName.text.toString().trim()
            val lastName = binding.editEmployeeLastName.text.toString().trim()
            val role = binding.addEmployeeRole.text.toString().trim()

            if (firstName.isNotEmpty() && lastName.isNotEmpty() && role.isNotEmpty()) {
                val employee = Employee(currentEmployee.id, firstName, lastName, role)
                employeeViewModel.updateEmployee(employee)
                Toast.makeText(activity, "Employee Update Successfully", Toast.LENGTH_SHORT).show()
                view.findNavController().popBackStack(R.id.homeFragment, false)
            } else {
                Toast.makeText(context, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteEmployee(){
        val activity = requireActivity() // Get the enclosing activity

        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Are you sure you want to delete this note?")
            setPositiveButton("Delete"){_,_ ->
                employeeViewModel.deleteEmployee(currentEmployee)
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)
                Toast.makeText(context, currentEmployee.firstName +" deleted", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("Cancel",null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_employee,menu)

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteEmployee -> {
                deleteEmployee()
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editEmployeeBinding = null
    }
}