package com.jp.test.composedemo.screen

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jp.test.composedemo.components.ErrorAlertComposable
import com.jp.test.composedemo.components.LoaderComposable
import com.jp.test.composedemo.navControllers.Routes
import com.jp.test.composedemo.network.ApiState
import com.jp.test.composedemo.ui.theme.colorCardBg
import com.jp.test.composedemo.utils.Extensions.makeToast
import com.jp.test.composedemo.viewmodels.RecipesViewModel

@Composable
fun Home(recipesViewModel: RecipesViewModel, navController: NavHostController) {

    val recipesTagsState = recipesViewModel.recipesTags.collectAsState()
    val mContext = LocalContext.current


    LaunchedEffect(key1 = Unit) {
        recipesViewModel.fetchRecipeTags()
    }


    when (recipesTagsState.value) {
        is ApiState.Loading -> {
            // Show loading indicator
            LoaderComposable()
        }

        is ApiState.Success -> {
            val dataList = (recipesTagsState.value as ApiState.Success<List<String>>).data
            // Display the fetched data using Jetpack Compose components
            RenderView(dataList, navController, mContext)

        }

        is ApiState.Error -> {
            val error = (recipesTagsState.value as ApiState.Error<List<String>>).message
            // Show error message
            ErrorAlertComposable(error)
        }
    }
}

@Composable
fun RenderView(dataList: List<String>?, navController: NavHostController, mContext: Context) {
    Column(modifier = Modifier.padding(top = 70.dp)) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp),
            text = "Recipes Categories:",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))

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
        }
    }
}

@Preview
@Composable
private fun RenderViewPreview() {

}
