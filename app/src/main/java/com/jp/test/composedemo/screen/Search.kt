package com.jp.test.composedemo.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jp.test.composedemo.R
import com.jp.test.composedemo.components.CustomTextFieldApp
import com.jp.test.composedemo.components.LoaderComposable
import com.jp.test.composedemo.domain.model.ApiRecipesFromTags
import com.jp.test.composedemo.network.ApiState
import com.jp.test.composedemo.viewmodels.SearchViewModel

@Composable
fun Search(navController: NavHostController) {
    val searchViewModel = hiltViewModel<SearchViewModel>()
    val searchText by searchViewModel.searchText.collectAsState()
    val isSearching by searchViewModel.isSearching.collectAsState()
    val recipeData by searchViewModel.recipeData.collectAsState()

    LaunchedEffect(key1 = Unit) {
        searchViewModel.recipesFromSearch.collect {
            when (it) {
                is ApiState.Loading -> {
                }

                is ApiState.Success -> {
                    (it as ApiState.Success<ApiRecipesFromTags>).data?.recipes?.let { data ->
                        println("Success api call ==>> ${data.size}")
                        searchViewModel.setRecipeData(data)
                    }
                }

                is ApiState.Error -> {
                    val error = (it as ApiState.Error<ApiRecipesFromTags>).message
                    // Show error message
//                    ErrorAlertComposable(error)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 70.dp)
            .fillMaxHeight()
    ) {
        CustomTextFieldApp(
            placeholder = stringResource(id = R.string.strSearchHere),
            text = searchText,
            onValueChange = searchViewModel::onSearchTextChanged,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
            modifier = Modifier
                .fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(20.dp)
                )
            }
        )

        if (!isSearching) {
            RecipeListView(
                recipeData, navController = navController, modifier = Modifier.fillMaxSize().padding(bottom = 80.dp))

        } else {
            LoaderComposable()
        }

    }

}

