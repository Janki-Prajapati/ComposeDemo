package com.jp.test.composedemo.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.lang.Error


@Composable
fun ErrorAlertComposable(error: String) {
    Box(modifier = Modifier.fillMaxSize(1f), contentAlignment = Alignment.Center) {
        Text(
            text = "Failed to fetch data: $error",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(Alignment.Center)
        )
    }
}