package com.jp.test.composedemo.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jp.test.composedemo.network.ApiState
import com.jp.test.composedemo.viewmodels.RecipesViewModel

@Composable
fun Home(recipesViewModel: RecipesViewModel) {

    val recipesTagsState = recipesViewModel.recipesTags.collectAsState()


    LaunchedEffect(key1 = Unit) {
        recipesViewModel.fetchRecipeTags()
    }


    when (recipesTagsState.value) {
        is ApiState.Loading -> {
            // Show loading indicator
            Box(modifier = Modifier.fillMaxSize(1f), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is ApiState.Success -> {
            val dataList = (recipesTagsState.value as ApiState.Success<List<String>>).data
            // Display the fetched data using Jetpack Compose components
            Column {
                Text("User List:")
                LazyColumn {
                    items(dataList?.size ?: 0){
                        Text(dataList?.get(it) ?: "")
                    }
                }
            }
        }

        is ApiState.Error -> {
            val error = (recipesTagsState.value as ApiState.Error<List<String>>).message
            // Show error message
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
    }
}