package com.jp.test.composedemo

sealed class Routes(val route : String) {
    object Login : Routes("Login")
}