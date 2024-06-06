package com.jp.test.composedemo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.jp.test.composedemo.navControllers.Routes
import com.jp.test.composedemo.screen.Home
import com.jp.test.composedemo.screen.Profile
import com.jp.test.composedemo.screen.RecipeDetails
import com.jp.test.composedemo.screen.RecipesByTags
import com.jp.test.composedemo.screen.Search
import com.jp.test.composedemo.viewmodels.RecipesViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    recipesViewModel: RecipesViewModel
) {
    NavHost(
        navController = navController,
        route = Routes.BottomNav.route,
        startDestination = Routes.Home.route
    ) {

        composable(Routes.Home.route) {
            Home(recipesViewModel, navController)
        }
        composable(Routes.Search.route) {
            Search(navController)
        }
        composable(Routes.Profile.route) {
            Profile()
        }

        homeNavGraph(recipesViewModel = recipesViewModel, navController = navController)
    }
}

fun NavGraphBuilder.homeNavGraph(
    recipesViewModel: RecipesViewModel, navController: NavHostController
) {
    navigation(startDestination = Routes.RecipesByTags.route, route = Routes.HomeNav.route) {
        composable(
            Routes.RecipesByTags.route,
            arguments = listOf(navArgument("recipeTag") { type = NavType.StringType })
        ) { backStackEntry ->
            val recipeTag = backStackEntry.arguments?.getString("recipeTag")
            RecipesByTags(
                recipesViewModel = recipesViewModel,
                recipeTag = recipeTag, navController = navController
            )
        }

        composable(route = Routes.RecipesDetails.route) { backStackEntry ->
            val recipeDetailsJson = backStackEntry.arguments?.getString("recipeDetails")
            println("recipe details ==>> $recipeDetailsJson")
            RecipeDetails(backStackEntry, recipesViewModel)
        }
    }
}
