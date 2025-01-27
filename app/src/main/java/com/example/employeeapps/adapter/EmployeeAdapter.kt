package com.example.employeeapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeapps.databinding.EmployeeLayoutBinding
import com.example.employeeapps.fragment.HomeFragmentDirections
import com.example.employeeapps.model.Employee

class EmployeeAdapter: RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {
    class EmployeeViewHolder(val itemBinding: EmployeeLayoutBinding) :RecyclerView.ViewHolder(itemBinding.root)

    // to refresh recycleview efficiently
    private var differCallback = object : DiffUtil.ItemCallback<Employee>(){
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.firstName == newItem.firstName &&
                    oldItem.lastName == newItem.lastName &&
                    oldItem.role == newItem.role
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem == newItem
        }
    }
    // to refresh recycleview efficiently
    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        return EmployeeViewHolder(
            EmployeeLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val currentEmployee = differ.currentList[position]

        holder.itemBinding.employeeFirstName.text = currentEmployee.firstName
        holder.itemBinding.employeeLastName.text = currentEmployee.lastName
        holder.itemBinding.employeeRole.text = currentEmployee.role

        holder.itemView.setOnClickListener {
            val direction = HomeFragmentDirections
                .actionHomeFragmentToEditEmployeeFragment()
            it.findNavController().navigate(direction)
        }
    }
}