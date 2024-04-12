package com.jp.test.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jp.test.composedemo.navControllers.Routes
import com.jp.test.composedemo.screen.AppBar
import com.jp.test.composedemo.screen.Login
import com.jp.test.composedemo.screen.MyApp
import com.jp.test.composedemo.screen.SignUp
import com.jp.test.composedemo.ui.theme.ComposeDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                val context = LocalContext.current
                val showTopAppBar = remember { mutableStateOf(false) }
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()

                val currentScreen = Routes.valueOf(
                    backStackEntry?.destination?.route ?: Routes.Login.name
                )
                Scaffold(
                    topBar = {
                        if (showTopAppBar.value) {
                            AppBar(
                                modifier = Modifier.shadow(
                                    1.dp,
                                    spotColor = Color.DarkGray
                                ),
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