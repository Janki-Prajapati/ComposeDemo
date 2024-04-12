package com.jp.test.composedemo.utils

import android.content.Context
import android.widget.Toast

object Extenstions {

   fun Context.makeToast(message : String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}