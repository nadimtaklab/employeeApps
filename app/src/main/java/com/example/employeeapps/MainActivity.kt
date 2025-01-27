package com.example.employeeapps

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.employeeapps.database.EmployeeDatabase
import com.example.employeeapps.databinding.ActivityMainBinding
import com.example.employeeapps.repository.EmployeeRepository
import com.example.employeeapps.viewmodel.EmployeeViewModel
import com.example.learnapps.viewmodel.EmployeeViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var employeeViewModel: EmployeeViewModel
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //init binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupViewModel()
    }

    private fun setupViewModel(){
        val employeeRepository = EmployeeRepository(EmployeeDatabase(this))
        val viewModelProviderFactory = EmployeeViewModelFactory(application, employeeRepository)
        employeeViewModel = ViewModelProvider(this, viewModelProviderFactory).get(EmployeeViewModel::class.java)
    }
}