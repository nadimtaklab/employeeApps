package com.example.employeeapps.database

interface DuplicateCheckCallback {
    fun onResult(isDuplicate: Boolean)
}