package com.jp.test.composedemo.screen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import com.google.gson.Gson
import com.jp.test.composedemo.R
import com.jp.test.composedemo.components.ImageFromURLWithPlaceHolderDetails
import com.jp.test.composedemo.domain.model.ApiTagRecipesDetail
import com.jp.test.composedemo.ui.theme.colorWhite
import com.jp.test.composedemo.viewmodels.RecipesViewModel
import java.net.URLDecoder

@Composable
fun RecipeDetails(navBackStackEntry: NavBackStackEntry, recipesViewModel: RecipesViewModel) {
    val recipeDetailsJson = navBackStackEntry.arguments?.getString("recipeDetails")
    val decodedRecipeDetailsJson = URLDecoder.decode(recipeDetailsJson, "UTF-8")

    val context = LocalContext.current

    val details = Gson().fromJson(decodedRecipeDetailsJson, ApiTagRecipesDetail.Recipe::class.java)

    println("Recipe details are ==>> $details")

    LazyColumn(
        modifier = Modifier
            .padding(top = 70.dp, start = 10.dp, end = 10.dp)
    ) {
        item {
            HeaderView(details = details, recipesViewModel = recipesViewModel, context = context)
        }

        item {
            Spacer(modifier = Modifier.height(5.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(details.ingredients?.size ?: 0) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(5.dp),
                        colors = CardDefaults.cardColors(containerColor = colorWhite),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = details.ingredients?.get(it) ?: "",
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                fontFamily = FontFamily.Serif,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Instructions:",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(5.dp))
        }

        items(details.instructions?.size ?: 0) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${it + 1}. ${details.instructions?.get(it)}",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Start
            )
        }
    }

}


@Composable
fun HeaderView(
    details: ApiTagRecipesDetail.Recipe,
    recipesViewModel: RecipesViewModel,
    context: Context
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            details.image?.let {
                ImageFromURLWithPlaceHolderDetails(
                    imageUrl = it
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Name : ${details?.name}",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Cuisine : ${details?.cuisine}",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Servings : ${details?.servings}",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Difficulty : ${details?.difficulty}",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Start
                )
            }

        }
        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterVertically),
                text = "Ingredients:",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(5.dp))

            IconButton(onClick = {
                println("Ingredients:, ${details.ingredients?.joinToString(" ")}")
                recipesViewModel.textToSpeech(
                    context = context,
                    "Ingredients:, ${details.ingredients?.joinToString(",")}"
                )
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                    contentDescription = stringResource(R.string.speak)
                )
            }
        }

    }
}

fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}

fun LazyGridScope.footer(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}