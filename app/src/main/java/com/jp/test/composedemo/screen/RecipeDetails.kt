package com.jp.test.composedemo.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import com.google.gson.Gson
import com.jp.test.composedemo.components.ImageFromURLWithPlaceHolderDetails
import com.jp.test.composedemo.domain.model.ApiTagRecipesDetail
import com.jp.test.composedemo.ui.theme.colorWhite
import java.net.URLDecoder

@Composable
fun RecipeDetails(navBackStackEntry: NavBackStackEntry) {
    val recipeDetailsJson = navBackStackEntry.arguments?.getString("recipeDetails")
    val decodedRecipeDetailsJson = URLDecoder.decode(recipeDetailsJson, "UTF-8")

    val details = Gson().fromJson(decodedRecipeDetailsJson, ApiTagRecipesDetail.Recipe::class.java)

    println("Recipe details are ==>> $details")

    Column(
        modifier = Modifier
            .padding(top = 70.dp, start = 10.dp, end = 10.dp)
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(7.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            header {
                HeaderView(details = details)
            }
            items(details.ingredients?.size ?: 0) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(0.dp),
                    colors = CardDefaults.cardColors(containerColor = colorWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
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

            footer {
                FooterView(details = details)
            }
        }


    }

}


@Composable
fun HeaderView(details: ApiTagRecipesDetail.Recipe) {
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
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Ingredients:",
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun FooterView(details: ApiTagRecipesDetail.Recipe) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Instructions:",
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        fontFamily = FontFamily.Serif,
        textAlign = TextAlign.Start
    )

    Spacer(modifier = Modifier.height(10.dp))

    /*LazyColumn(modifier = Modifier.fillMaxSize()) {
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
    }*/
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