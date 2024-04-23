package com.jp.test.composedemo.databse

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCustomer(customer: Customer)

    @Query("SELECT COUNT(*) FROM customer WHERE email = :email")
    fun findCustomer(email: String): LiveData<Int>

    @Query("SELECT * FROM customer WHERE email = :email")
    fun getCustomerData(email: String): LiveData<Customer>

    @Query("SELECT * FROM customer")
    fun getCustomers(): List<Customer>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCustomerDetails(customer: Customer)

    @Delete
    suspend fun deleteCustomer(customer: Customer)

    @Query("UPDATE customer SET password = :password WHERE email = :email")
    suspend fun updatePassword(password: String, email: String)
}