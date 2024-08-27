package com.example.winit_kotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.winit_kotlin.dataobject.Customer

class SharedViewModel : ViewModel() {
    val customerData  = MutableLiveData<Customer>()
    val customerListData = MutableLiveData<List<Customer>>()

    fun setCustomerData(customer: Customer){
        customerData.value = customer
    }
    fun setCustomerListData(customerlist : List<Customer>){
        customerListData.value = customerlist
    }
}