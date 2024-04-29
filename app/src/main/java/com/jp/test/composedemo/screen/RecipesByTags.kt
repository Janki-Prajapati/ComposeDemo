package com.jp.test.composedemo.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jp.test.composedemo.components.ErrorAlertComposable
import com.jp.test.composedemo.components.LoaderComposable
import com.jp.test.composedemo.domain.model.ApiRecipesFromTags
import com.jp.test.composedemo.navControllers.Routes
import com.jp.test.composedemo.network.ApiState
import com.jp.test.composedemo.ui.theme.colorCardBg
import com.jp.test.composedemo.utils.Extensions.makeToast
import com.jp.test.composedemo.viewmodels.RecipesViewModel

@Composable
fun RecipesByTags(recipesViewModel: RecipesViewModel, recipeTag: String?) {

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
                RenderViewByTag(it)
            }

        }

        is ApiState.Error -> {
            val error = (recipesByTagsState.value as ApiState.Error<ApiRecipesFromTags>).message
            // Show error message
            ErrorAlertComposable(error)
        }
    }
}

@Composable
fun RenderViewByTag(data: ApiRecipesFromTags) {
    Column(modifier = Modifier.padding(top = 70.dp)) {
        /* Spacer(modifier = Modifier.height(20.dp))

         LazyVerticalGrid(
             columns = GridCells.Fixed(2),
             modifier = Modifier
                 .fillMaxSize()
                 .padding(bottom = 80.dp),
             contentPadding = PaddingValues(7.dp),
             verticalArrangement = Arrangement.spacedBy(12.dp),
             horizontalArrangement = Arrangement.spacedBy(12.dp)
         ) {
             items(dataList?.size ?: 0) {
                 Card(
                     modifier = Modifier.fillMaxWidth(),
                     shape = RoundedCornerShape(0.dp),
                     colors = CardDefaults.cardColors(containerColor = colorCardBg),
                     elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                     onClick = {
                         mContext.makeToast("selected tag is ==>> ${dataList?.get(it)}")
                         navController.navigate(
                             "${Routes.RecipesByTags.route}/${dataList?.get(it)}")
                     }
                 ) {
                     Column(
                         modifier = Modifier
                             .fillMaxWidth()
                             .padding(5.dp)
                     ) {
                         Text(
                             modifier = Modifier.fillMaxWidth(),
                             text = dataList?.get(it) ?: "",
                             fontWeight = FontWeight.Normal,
                             fontSize = 20.sp,
                             fontFamily = FontFamily.Serif,
                             textAlign = TextAlign.Center
                         )
                     }
                 }
             }
         }*/
    }
}