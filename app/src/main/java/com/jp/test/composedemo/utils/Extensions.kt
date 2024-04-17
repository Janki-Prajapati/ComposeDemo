package com.jp.test.composedemo.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object Extensions {

    fun Context.makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun Activity.shouldShowRationale(name: String): Boolean {
        return shouldShowRequestPermissionRationale(name)
    }

    fun Context.findActivity(): Activity? {
        return when (this) {
            is Activity -> this
            is ContextWrapper -> {
                baseContext.findActivity()
            }

            else -> null
        }
    }

    fun SnackbarHostState.showSnackBar(
        message: String? = null,
        action: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short,
        coroutineScope: CoroutineScope,
        onSnackBarAction: () -> Unit = {},
        onSnackBarDismiss: () -> Unit = {},
    ) {
        if (!message.isNullOrEmpty()) {

            coroutineScope.launch {

                when (showSnackbar(
                    message = message,
                    duration = duration,
                    actionLabel = action,
                    withDismissAction = duration == SnackbarDuration.Indefinite,
                )) {
                    SnackbarResult.Dismissed -> onSnackBarDismiss.invoke()
                    SnackbarResult.ActionPerformed -> onSnackBarAction.invoke()
                }
            }
        }
    }

    fun Context.gotoApplicationSettings() {
        startActivity(Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.parse("package:${packageName}")
        })
    }

    private fun Context.isPermissionGranted(name: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this, name
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun Context.hasPickMediaPermission(): Boolean {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            isPermissionGranted(name = Manifest.permission.POST_NOTIFICATIONS)
        } else {
            true
        }
    }
}