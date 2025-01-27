package com.example.learnapps.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.employeeapps.repository.EmployeeRepository
import com.example.employeeapps.viewmodel.EmployeeViewModel

class EmployeeViewModelFactory(val app:Application, private val employeeRepository: EmployeeRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EmployeeViewModel(app, employeeRepository) as T
    }
}