package com.jp.test.composedemo.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.google.gson.Gson
import com.jp.test.composedemo.components.ImageFromURLWithPlaceHolder
import com.jp.test.composedemo.domain.model.ApiTagRecipesDetail
import java.net.URLDecoder

@Composable
fun RecipeDetails(navBackStackEntry: NavBackStackEntry) {
    val recipeDetailsJson = navBackStackEntry.arguments?.getString("recipeDetails")
    val decodedRecipeDetailsJson = URLDecoder.decode(recipeDetailsJson, "UTF-8")

    val details = Gson().fromJson(decodedRecipeDetailsJson, ApiTagRecipesDetail.Recipe::class.java)

    println("Recipe details are ==>> $details")
    Column(
        modifier = Modifier.padding(top = 70.dp, bottom = 80.dp, start = 10.dp, end = 10.dp),
    ) {
        details.image?.let {
            ImageFromURLWithPlaceHolder(
                imageUrl = it
            )
        }
    }

}