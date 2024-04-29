package com.jp.test.composedemo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jp.test.composedemo.domain.model.ApiRecipesFromTags
import com.jp.test.composedemo.network.ApiService
import com.jp.test.composedemo.network.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _recipesTags = MutableStateFlow<ApiState<List<String>>>(ApiState.Loading)
    val recipesTags: StateFlow<ApiState<List<String>>> = _recipesTags

    private val _recipesByTags = MutableStateFlow<ApiState<ApiRecipesFromTags>>(ApiState.Loading)
    val recipesByTags: StateFlow<ApiState<ApiRecipesFromTags>> = _recipesByTags

    fun fetchRecipeTags() {
        viewModelScope.launch {
            try {
                val response = apiService.getRecipesTags()
                _recipesTags.value = ApiState.Success(response)

            } catch (e: Exception) {
                _recipesTags.value = ApiState.Error(e.message.toString())
            }
        }
    }

    fun fetchRecipeByTags(tag: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getRecipesByTags(tag)
                _recipesByTags.value = ApiState.Success(response)
            } catch (e: Exception) {
                _recipesByTags.value = ApiState.Error(e.message.toString())
            }
        }
    }
}