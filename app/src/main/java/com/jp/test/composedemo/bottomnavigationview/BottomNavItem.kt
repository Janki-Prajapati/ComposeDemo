package com.jp.test.composedemo.bottomnavigationview

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.jp.test.composedemo.R
import com.jp.test.composedemo.navControllers.Routes

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: Int) {
    data object HomeItem :
        BottomNavItem(Routes.Home.route, Icons.Filled.Home, R.string.strHome)

    data object SearchItem :
        BottomNavItem(Routes.Search.route, Icons.Filled.Search, R.string.strSearch)

    data object ProfileItem :
        BottomNavItem(Routes.Profile.route, Icons.Filled.Person, R.string.strProfile)
}