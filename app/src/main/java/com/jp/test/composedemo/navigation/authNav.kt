package com.jp.test.composedemo.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jp.test.composedemo.navControllers.Routes
import com.jp.test.composedemo.screen.ForgotPassword
import com.jp.test.composedemo.screen.Login
import com.jp.test.composedemo.screen.SignUp

fun NavGraphBuilder.authNav(navController: NavHostController) {
    navigation(startDestination = Routes.Login.route, route = Routes.AuthNav.route) {
        composable(Routes.Login.route) {
            Login(navController)
        }

        composable(Routes.SignUp.route) {
            SignUp(navController)
        }

        composable(Routes.ForgotPassword.route) {
            ForgotPassword(navController)
        }
    }
}