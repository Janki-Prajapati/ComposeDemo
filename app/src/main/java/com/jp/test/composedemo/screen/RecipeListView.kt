package com.jp.test.composedemo.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.jp.test.composedemo.components.ImageFromURLWithPlaceHolder
import com.jp.test.composedemo.domain.model.ApiRecipesFromTags
import com.jp.test.composedemo.navControllers.Routes
import java.net.URLEncoder

@Composable
fun RecipeListView(data: List<ApiRecipesFromTags.Recipe?>, navController: NavHostController, modifier: Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(data.size) {
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
                    data[it]?.image?.let { it1 ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .clickable(indication = null,
                                    interactionSource = remember { MutableInteractionSource() })  {
                                    val recipeDetailsJson = Gson().toJson(data[it])
                                    val encodedRecipeDetailsJson =
                                        URLEncoder.encode(recipeDetailsJson, "UTF-8")
                                    val deepLink = Routes.RecipesDetails.route.replace(
                                        "{recipeDetails}",
                                        encodedRecipeDetailsJson ?: ""
                                    )
                                    navController.navigate(deepLink)
                                }
                        ) {
                            ImageFromURLWithPlaceHolder(
                                imageUrl = it1
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Name : ${data[it]?.name}",
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily.Serif,
                                    textAlign = TextAlign.Start
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Cuisine : ${data[it]?.cuisine}",
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily.Serif,
                                    textAlign = TextAlign.Start
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Servings : ${data[it]?.servings}",
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily.Serif,
                                    textAlign = TextAlign.Start
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Difficulty : ${data[it]?.difficulty}",
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
}