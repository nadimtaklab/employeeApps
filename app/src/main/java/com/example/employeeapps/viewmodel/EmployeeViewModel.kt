package com.example.employeeapps.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeeapps.model.Employee
import com.example.employeeapps.repository.EmployeeRepository
import kotlinx.coroutines.launch

class EmployeeViewModel(
    app: Application,
    private val employeeRepository:EmployeeRepository
):AndroidViewModel(app) {
    fun addEmployee(employee: Employee) =
        viewModelScope.launch {
            employeeRepository.insertEmployee(employee)
        }

    fun updateEmployee(employee: Employee) =
        viewModelScope.launch {
            employeeRepository.updateEmployee(employee)
        }

    fun deleteEmployee(employee: Employee) =
        viewModelScope.launch {
            employeeRepository.deleteEmployee(employee)
        }

    fun getAllEmployee() = employeeRepository.getAllEmployee()

    fun searchEmployee(query: String?) = employeeRepository.searchEmployee(query)

    fun doesEmployeeExist(firstName: String, lastName: String) = employeeRepository.doesEmployeeExist(firstName, lastName)
}