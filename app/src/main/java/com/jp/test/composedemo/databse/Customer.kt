package com.jp.test.composedemo.databse

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Customer(
    @PrimaryKey(autoGenerate = true) @ColumnInfo val id: Int = 0,
    @ColumnInfo val firstName: String,
    @ColumnInfo val lastName: String,
    @ColumnInfo val phone: String,
    @ColumnInfo val email: String,
    @ColumnInfo val password: String
)
