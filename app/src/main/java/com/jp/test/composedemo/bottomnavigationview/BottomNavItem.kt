package com.jp.test.composedemo.bottomnavigationview

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.jp.test.composedemo.R

sealed class BottomNavItem(val route : String, val icon : ImageVector, val label : Int) {
    object Home : BottomNavItem("home", Icons.Filled.Home, R.string.strHome)
    object Search : BottomNavItem("search", Icons.Filled.Search, R.string.strSearch)
    object Profile : BottomNavItem("profile", Icons.Filled.Person, R.string.strProfile)
}