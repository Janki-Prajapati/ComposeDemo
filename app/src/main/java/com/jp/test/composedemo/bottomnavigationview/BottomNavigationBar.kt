package com.jp.test.composedemo.bottomnavigationview

import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<BottomNavItem>,  currentRoute : String
) {
    BottomNavigation(backgroundColor = Color(0xff3366ff)) {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { androidx.compose.material.Icon(imageVector = screen.icon, contentDescription = stringResource(id = screen.label)) },
                label = { Text(stringResource(id = screen.label), color = Color.White) },
                selected = currentRoute == screen.route,
                selectedContentColor = Color.White,
                alwaysShowLabel = false, // This hides the title for the unselected items
                onClick = {
                    // This if check gives us a "singleTop" behavior where we do not create a
                    // second instance of the composable if we are already on that destination
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}