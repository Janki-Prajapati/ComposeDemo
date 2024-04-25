package com.jp.test.composedemo.bottomnavigationview

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController


@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<BottomNavItem>, currentRoute: String
) {
    NavigationBar(containerColor = Color(0xff3366ff)) {
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = stringResource(id = screen.label)
                    )
                },
                label = { Text(stringResource(id = screen.label), color = Color.White) },
                selected = currentRoute == screen.route,
                colors = NavigationBarItemColors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    disabledIconColor = Color.Gray,
                    disabledTextColor = Color.Gray,
                    selectedIndicatorColor = Color.White,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                ),
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