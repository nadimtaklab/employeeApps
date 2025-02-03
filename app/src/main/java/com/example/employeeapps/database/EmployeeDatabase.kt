package com.example.employeeapps.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.employeeapps.model.Employee

@Database(entities = [Employee::class], version = 1)
abstract class EmployeeDatabase: RoomDatabase() {

    abstract fun getEmployeeDao(): EmployeeDao

    // Companion object to provide a singleton instance of the database
    companion object{

        @Volatile //guaranty only one thread access
        private var instance: EmployeeDatabase? = null
        private val LOCK = Any()

        // Method to get the database instance
        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?:
            createDatabase(context).also{
                instance = it
            }
        }

        // Method to create the database
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                EmployeeDatabase::class.java,
                "employee_db"
            ).build()

    }
}