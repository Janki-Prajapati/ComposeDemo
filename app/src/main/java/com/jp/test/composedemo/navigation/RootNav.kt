package com.jp.test.composedemo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jp.test.composedemo.Constants
import com.jp.test.composedemo.navControllers.Routes
import com.jp.test.composedemo.screen.ScreenMain
import com.jp.test.composedemo.utils.PreferencesManager

@Composable
fun RootNav() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val preferencesManager = remember { PreferencesManager(context) }
    val currentScreen = if (preferencesManager.getBooleanData(
            Constants.PREF_KEY_IS_LOGGED_IN,
            false
        )
    ) Routes.HomeNav.route else Routes.AuthNav.route


    NavHost(navController = navController, startDestination = currentScreen) {
        AuthNav(navController)

        composable(route = Routes.HomeNav.route) {
            ScreenMain(
                logout = {
                    navController.navigate(Routes.AuthNav.route) {
                        popUpTo(0) {}
                    }
                }
            )
        }

    }

}