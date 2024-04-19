package com.jp.test.composedemo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.jp.test.composedemo.navControllers.Routes
import com.jp.test.composedemo.screen.AppBar
import com.jp.test.composedemo.screen.ForgotPassword
import com.jp.test.composedemo.screen.Login
import com.jp.test.composedemo.screen.ScreenMain
import com.jp.test.composedemo.screen.SignUp
import com.jp.test.composedemo.ui.theme.ComposeDemoTheme
import com.jp.test.composedemo.utils.Extensions.makeToast
import com.jp.test.composedemo.utils.PreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                val context = LocalContext.current
                val showTopAppBar = remember { mutableStateOf(false) }
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val preferencesManager = remember { PreferencesManager(context) }

                val currentScreen = Routes.valueOf(
                    if (preferencesManager.getBooleanData(
                            "isLoggedIn",
                            false
                        )
                    ) Routes.ScreenMain.name else backStackEntry?.destination?.route
                        ?: Routes.Login.name
                )
                Scaffold(
                    topBar = {
                        if (showTopAppBar.value) {
                            AppBar(
                                modifier = Modifier.shadow(
                                    3.dp,
                                    spotColor = Color.DarkGray
                                ).background(Color(0xff66b3ff)),
                                currentScreen = currentScreen,
                                canNavigateBack = navController.previousBackStackEntry != null,
                                navigateUp = { navController.navigateUp() }
                            )
                        }
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        MyApp(navController = navController, showTopAppBar, context = context)
                    }
                }
            }
        }
    }
}

@Composable
fun MyApp(
    navController: NavHostController,
    showTopAppBar: MutableState<Boolean>,
    context: Context
) {
    val preferencesManager = remember { PreferencesManager(context) }
    val currentScreen = if (preferencesManager.getBooleanData(
            "isLoggedIn",
            false
        )
    ) Routes.ScreenMain.name else Routes.Login.name
    NavHost(navController = navController, startDestination = currentScreen,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }) {
        composable(Routes.Login.name) {
            Login {
                when (it) {
                    "googleSignUp" -> {
                        context.makeToast("Google Sign up Clicked!!")

                    }

                    "forgotPassword" -> {
                        navController.navigate(route = Routes.ForgotPassword.name)

                    }

                    "login" -> {
                        // Update data and save to SharedPreferences
                        preferencesManager.saveBooleanData("isLoggedIn", true)
                        navController.navigate(route = Routes.ScreenMain.name)
                    }

                    "signup" -> {
                        navController.navigate(route = Routes.SignUp.name)
                    }
                }
            }

            showTopAppBar.value = false
        }

        composable(Routes.SignUp.name) {
            SignUp {
                when (it) {
                    "login" -> {
                        navController.navigateUp()
                    }

                    "signup" -> {
                        navController.navigateUp()
                    }
                }
            }

            showTopAppBar.value = false
        }

        composable(Routes.ScreenMain.name) {

            showTopAppBar.value = false
            ScreenMain()
        }

        composable(Routes.ForgotPassword.name) {

            showTopAppBar.value = true
            ForgotPassword {
                when (it) {
                    "passwordChanged" -> {
                        navController.navigateUp()
                    }
                }
            }
        }
    }
}