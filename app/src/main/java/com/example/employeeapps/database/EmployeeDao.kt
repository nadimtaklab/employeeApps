package com.example.employeeapps.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.employeeapps.model.Employee

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: Employee)

    @Update
    suspend fun updateEmployee(employee: Employee)

    @Delete
    suspend fun deleteEmployee(employee: Employee)

    @Query("SELECT * FROM employee ORDER BY id ASC")
    fun getAllEmployee(): LiveData<List<Employee>>

    @Query("SELECT * FROM employee " +
            "WHERE firstName LIKE :searchQuery " +
            "OR lastName LIKE :searchQuery " +
            "OR role LIKE :searchQuery")

    fun searchEmployee(searchQuery: String?): LiveData<List<Employee>>

    @Query("SELECT EXISTS(SELECT 1 FROM employee WHERE firstName LIKE :firstName AND lastName LIKE :lastName)")
    fun doesEmployeeExist(firstName: String, lastName: String): LiveData<Boolean>
}