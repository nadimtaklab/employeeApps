package com.example.employeeapps.repository

import com.example.employeeapps.database.EmployeeDatabase
import com.example.employeeapps.model.Employee

class EmployeeRepository(private val db: EmployeeDatabase) {

    suspend fun insertEmployee(employee: Employee) = db.getEmployeeDao().insertEmployee(employee)
    suspend fun deleteEmployee(employee: Employee) = db.getEmployeeDao().deleteEmployee(employee)
    suspend fun updateEmployee(employee: Employee) = db.getEmployeeDao().updateEmployee(employee)

    fun getAllEmployee() = db.getEmployeeDao().getAllEmployee()
    fun searchEmployee(query: String?) = db.getEmployeeDao().searchEmployee(query)
}