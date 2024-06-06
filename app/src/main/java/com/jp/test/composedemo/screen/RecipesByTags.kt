package com.jp.test.composedemo.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.jp.test.composedemo.components.ErrorAlertComposable
import com.jp.test.composedemo.components.ImageFromURLWithPlaceHolder
import com.jp.test.composedemo.components.LoaderComposable
import com.jp.test.composedemo.domain.model.ApiRecipesFromTags
import com.jp.test.composedemo.navControllers.Routes
import com.jp.test.composedemo.network.ApiState
import com.jp.test.composedemo.viewmodels.RecipesViewModel
import java.net.URLEncoder

@Composable
fun RecipesByTags(
    recipesViewModel: RecipesViewModel,
    recipeTag: String?, navController: NavHostController
) {

    val recipesByTagsState = recipesViewModel.recipesByTags.collectAsState()


    LaunchedEffect(key1 = Unit) {
        recipeTag?.let {
            recipesViewModel.fetchRecipeByTags(it)
        }
    }

    when (recipesByTagsState.value) {
        is ApiState.Loading -> {
            // Show loading indicator
            LoaderComposable()
        }

        is ApiState.Success -> {
            val data = (recipesByTagsState.value as ApiState.Success<ApiRecipesFromTags>).data
            // Display the fetched data using Jetpack Compose components
            data?.let {
                it.recipes?.let { it1 -> RecipeListView(it1, navController,modifier = Modifier.fillMaxSize().padding(top = 70.dp,bottom = 80.dp)) }
            }

        }

        is ApiState.Error -> {
            val error = (recipesByTagsState.value as ApiState.Error<ApiRecipesFromTags>).message
            // Show error message
            ErrorAlertComposable(error)
        }
    }
}