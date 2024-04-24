package com.jp.test.composedemo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.jp.test.composedemo.navControllers.Routes
import com.jp.test.composedemo.navigation.RootNav
import com.jp.test.composedemo.ui.theme.ComposeDemoTheme
import com.jp.test.composedemo.utils.PreferencesManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                val context = LocalContext.current

                Scaffold { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        MyApp(context = context)
                    }
                }
            }
        }
    }
}

@Composable
fun MyApp(
    context: Context
) {
   /* val preferencesManager = remember { PreferencesManager(context) }
    val currentScreen = if (preferencesManager.getBooleanData(
            Constants.PREF_KEY_IS_LOGGED_IN,
            false
        )
    ) Routes.Home.route else Routes.Login.route*/

    RootNav()
}