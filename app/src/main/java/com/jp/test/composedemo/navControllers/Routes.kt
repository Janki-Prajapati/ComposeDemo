package com.jp.test.composedemo.navControllers

import androidx.annotation.StringRes
import com.jp.test.composedemo.R

sealed class Routes(val route : String) {
    //Screen Routes
    data object Login : Routes("Login")
    data object SignUp : Routes("Signup")
    data object ForgotPassword : Routes("ForgotPassword")
    data object ScreenMain: Routes("ScreenMain")
    data object Home : Routes("Home")
    data object Search : Routes("Search")
    data object Profile : Routes("Profile")

    //Graph Routes
    data object AuthNav : Routes("AUTH_NAV_GRAPH")
    data object HomeNav : Routes("HOME_NAV_GRAPH")

}