package com.jp.test.composedemo.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jp.test.composedemo.Constants
import com.jp.test.composedemo.R
import com.jp.test.composedemo.bottomnavigationview.BottomNavItem
import com.jp.test.composedemo.navigation.HomeNavGraph
import com.jp.test.composedemo.ui.theme.colorAppTheme
import com.jp.test.composedemo.ui.theme.colorBlue
import com.jp.test.composedemo.utils.PreferencesManager

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenMain(logout: () -> Unit) {
    val context = LocalContext.current
    val preferencesManager = remember {
        PreferencesManager(context = context)
    }
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen =
        backStackEntry?.destination?.route
            ?: BottomNavItem.HomeItem.route


    val bottomNavigationItems = listOf(
        BottomNavItem.HomeItem,
        BottomNavItem.SearchItem,
        BottomNavItem.ProfileItem
    )

    Scaffold(topBar = {

        TopAppBar(
            title = { Text(text = currentScreen, fontSize = 18.sp) },
            modifier = Modifier
                .shadow(
                    3.dp,
                    spotColor = Color.DarkGray
                )
                .background(colorBlue),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorAppTheme,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White,
                titleContentColor = Color.White
            ),
            navigationIcon = {
                if (navController.previousBackStackEntry != null) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }

                }
            },
            actions = {
                IconButton(onClick = {
                    //Logout
                    logout.invoke()
                    preferencesManager.saveStringData(Constants.PREF_KEY_EMAIL, "")
                    preferencesManager.saveBooleanData(
                        Constants.PREF_KEY_IS_LOGGED_IN,
                        false
                    )
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = stringResource(R.string.logout_button)
                    )
                }

            }
        )
    },
        bottomBar = {
            NavigationBar(modifier = Modifier.height(65.dp), containerColor = colorAppTheme) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavigationItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentScreen == item.route,
                        onClick = {
                            if (currentScreen != item.route) {
                                navController.navigate(item.route)
                            }
                            /* navController.navigate(item.route) {
                                *//* // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true*//*
                            }*/
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = stringResource(id = item.label)
                            )
                        },
                        label = { Text(stringResource(id = item.label), color = Color.White) },
                        colors = NavigationBarItemColors(
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.White,
                            disabledIconColor = Color.LightGray,
                            disabledTextColor = Color.LightGray,
                            selectedIndicatorColor = Color.Transparent,
                            unselectedIconColor = Color.LightGray,
                            unselectedTextColor = Color.LightGray
                        ),
                        alwaysShowLabel = false, // This hides the title for the unselected items
                    )
                }
            }
        }) {
        HomeNavGraph(
            navController
        )
    }
}


@Preview
@Composable
private fun ScreenMainPreview() {
    ScreenMain {
        
    }
}
