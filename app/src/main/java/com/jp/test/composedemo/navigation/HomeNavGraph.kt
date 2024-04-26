package com.jp.test.composedemo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jp.test.composedemo.navControllers.Routes
import com.jp.test.composedemo.screen.Home
import com.jp.test.composedemo.screen.Profile
import com.jp.test.composedemo.screen.Search
import com.jp.test.composedemo.viewmodels.RecipesViewModel

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    recipesViewModel: RecipesViewModel
) {
    NavHost(
        navController = navController,
        route = Routes.HomeNav.route,
        startDestination = Routes.Home.route
    ) {

        composable(Routes.Home.route) {
            Home(recipesViewModel)
        }
        composable(Routes.Search.route) {
            Search()
        }
        composable(Routes.Profile.route) {
            Profile()
        }
    }
}