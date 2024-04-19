package com.jp.test.composedemo.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jp.test.composedemo.R
import com.jp.test.composedemo.bottomnavigationview.BottomNavItem
import com.jp.test.composedemo.bottomnavigationview.BottomNavigationBar
import com.jp.test.composedemo.navControllers.Routes

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenMain() {

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen =
        backStackEntry?.destination?.route
            ?: BottomNavItem.Home.route


    val bottomNavigationItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Profile
    )

    Scaffold(topBar = {

        TopAppBar(
            title = { Text(currentScreen) },
            modifier = Modifier
                .shadow(
                    3.dp,
                    spotColor = Color.DarkGray
                )
                .background(Color(0xff66b3ff)),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xff3366ff),
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White,
                titleContentColor = Color.White
            ),
            navigationIcon = {
                if (navController.previousBackStackEntry != null) {
                    Icon(
                        modifier = Modifier.clickable {
                            navController.navigateUp()
                        },
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        )
    },
        bottomBar = {
            BottomNavigationBar(
                navController,
                bottomNavigationItems,
                currentScreen
            )
        }) {
        MainScreenNavigationConfigurations(navController)
    }
}


@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController
) {
    NavHost(navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
//            ScaryScreen(ScaryAnimation.Frankendroid)
        }
        composable(BottomNavItem.Search.route) {
//            ScaryScreen(ScaryAnimation.Pumpkin)
        }
        composable(BottomNavItem.Profile.route) {
            Profile()
//            ScaryScreen(ScaryAnimation.Ghost)
        }
    }
}

