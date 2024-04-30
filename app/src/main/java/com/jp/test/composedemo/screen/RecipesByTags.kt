package com.jp.test.composedemo.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.jp.test.composedemo.components.ErrorAlertComposable
import com.jp.test.composedemo.components.ImageFromURLWithPlaceHolder
import com.jp.test.composedemo.components.LoaderComposable
import com.jp.test.composedemo.domain.model.ApiRecipesFromTags
import com.jp.test.composedemo.network.ApiState
import com.jp.test.composedemo.viewmodels.RecipesViewModel

@Composable
fun RecipesByTags(recipesViewModel: RecipesViewModel, recipeTag: String?) {

    val recipesByTagsState = recipesViewModel.recipesByTags.collectAsState()


    LaunchedEffect(key1 = Unit) {
        println("recipe tag is from detail screen ==>> $recipeTag")
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(data.recipes?.size ?: 0) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(0.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        data.recipes?.get(it)?.image?.let { it1 ->

                            Row {
                                ImageFromURLWithPlaceHolder(
                                    imageUrl = it1
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                Column {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text =  "Name : ${data.recipes?.get(it)?.name}",
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 13.sp,
                                        fontFamily = FontFamily.Serif,
                                        textAlign = TextAlign.Start
                                    )

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text =  "Cuisine : ${data.recipes?.get(it)?.cuisine}",
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 13.sp,
                                        fontFamily = FontFamily.Serif,
                                        textAlign = TextAlign.Start
                                    )

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text =  "Servings : ${data.recipes?.get(it)?.servings}",
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 13.sp,
                                        fontFamily = FontFamily.Serif,
                                        textAlign = TextAlign.Start
                                    )

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text =  "Difficulty : ${data.recipes?.get(it)?.difficulty}",
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 13.sp,
                                        fontFamily = FontFamily.Serif,
                                        textAlign = TextAlign.Start
                                    )
                                }

                            }

                        }
                    }
                }
            }

        }
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