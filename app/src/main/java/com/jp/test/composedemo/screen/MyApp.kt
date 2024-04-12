package com.jp.test.composedemo.screen

import android.content.Context
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jp.test.composedemo.navControllers.Routes
import com.jp.test.composedemo.ui.theme.ComposeDemoTheme
import com.jp.test.composedemo.utils.Extenstions.makeToast

@Composable
fun MyApp(
    navController: NavHostController,
    showTopAppBar: MutableState<Boolean>,
    context: Context
) {
    NavHost(navController = navController, startDestination = Routes.Login.name,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }) {
        composable(Routes.Login.name) {
            Login(onClick = {
                when (it) {
                    "forgotPassword" -> {
                        context.makeToast("Forgot Password Clicked!!")

                    }

                    "login" -> {
                        context.makeToast("Login Clicked!!")
                    }

                    "signup" -> {
                        navController.navigate(route = Routes.SignUp.name)
                    }
                }
            })
        }

        composable(Routes.SignUp.name) {
            SignUp(onClick = {
                when (it) {
                    "googleSignUp" -> {
                        context.makeToast("Google Sign up Clicked!!")

                    }

                    "login" -> {
                        navController.navigateUp()
                    }

                    "signup" -> {
                        navController.navigateUp()
                    }
                }
            })
        }
    }
}