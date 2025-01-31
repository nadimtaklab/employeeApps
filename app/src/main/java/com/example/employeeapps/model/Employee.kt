package com.example.employeeapps.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "employee")
data class Employee(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "firstName") val firstName: String,
    @ColumnInfo(name = "lastName") val lastName: String,
    @ColumnInfo(name = "role") val role: String
): Parcelable
