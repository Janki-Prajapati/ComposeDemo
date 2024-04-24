package com.jp.test.composedemo.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jp.test.composedemo.bottomnavigationview.BottomNavItem
import com.jp.test.composedemo.navControllers.Routes
import com.jp.test.composedemo.screen.Home
import com.jp.test.composedemo.screen.Profile
import com.jp.test.composedemo.screen.Search

@Composable
fun HomeNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = Routes.HomeNav.route,
        startDestination = Routes.Home.route
    ) {

        composable(Routes.Home.route) {
            Home()
        }
        composable(Routes.Search.route) {
            Search()
        }
        composable(Routes.Profile.route) {
            Profile()
        }
    }
}