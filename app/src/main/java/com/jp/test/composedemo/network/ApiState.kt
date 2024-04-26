package com.jp.test.composedemo.network


sealed class ApiState<out T> {

    data class Success<T>(val data: T?) : ApiState<T>()

    data class Error<T>(val message: String) : ApiState<T>()

    object Loading : ApiState<Nothing>()

}