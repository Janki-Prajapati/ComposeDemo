package com.jp.test.composedemo.databse

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class CustomerRepository @Inject constructor(private val customerDao: CustomerDao) {

    fun addCustomer(customer: Customer) {
        CoroutineScope(Dispatchers.IO).launch {
            customerDao.addCustomer(customer)
        }
    }

     fun findCustomer(email: String): LiveData<Int> {
       return customerDao.findCustomer(email)
    }
}