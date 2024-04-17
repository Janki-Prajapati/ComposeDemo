package com.jp.test.composedemo

import android.util.Patterns

// this fun use for check if value is number
fun isNumber(value: String): Boolean {
    return value.isEmpty() || Regex("^\\d+\$").matches(value)
}
fun isNumberLength(value: String): Boolean {
    return  value.length == 10
}

fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isPasswordValid(password: String): Boolean {
    return password.any { it.isDigit() } &&
            password.any { it.isLetter() }
}

fun isCodeLength(value: String): Boolean {
    return  value.length == 4
}


fun generatePIN() : String {

    //generate a 4 digit integer 1000 <10000
    return ((Math.random() * 9000).toInt() + 1000).toString()
}