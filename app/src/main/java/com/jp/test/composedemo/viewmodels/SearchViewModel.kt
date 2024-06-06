package com.jp.test.composedemo.viewmodels

import androidx.lifecycle.ViewModel
import com.jp.test.composedemo.domain.model.ApiRecipesFromTags
import com.jp.test.composedemo.network.ApiService
import com.jp.test.composedemo.network.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _recipeData = MutableStateFlow(listOf<ApiRecipesFromTags.Recipe?>())
    val recipeData = _recipeData.asStateFlow()


    private var _recipesFromSearch =
        MutableStateFlow<ApiState<ApiRecipesFromTags>>(ApiState.Loading)

    fun onSearchTextChanged(text: String) {
        println("Search Text ==>> ${text}")
        _searchText.value = text
    }

    fun setRecipeData(data: List<ApiRecipesFromTags.Recipe?>) {
        println("view model list size ==>> ${data.size}")
        _recipeData.value = data
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    var recipesFromSearch = searchText.debounce(300)
        .filter { query ->
            if (query.isEmpty()) {
                return@filter false
            } else {
                return@filter true
            }

        }
        .onEach { _isSearching.update { true } }
        .distinctUntilChanged()
        .flatMapLatest { query ->
            getRecipesFromSearch(query)
                .catch {
                    emitAll(flowOf())
                }

        }
        .onEach { _isSearching.update { false } }
        .flowOn(Dispatchers.Default)
    /*   .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _recipesFromSearch.value).collect{

       }*/


    suspend fun getRecipesFromSearch(searchText: String): Flow<ApiState<Any>> {
        println("api call for search ==>> $searchText")
        return flow {
            try {
                val response = apiService.getSearchedRecipe(searchText)
                emit(ApiState.Success(response))
            } catch (e: Exception) {
                emit(ApiState.Error(e.message.toString()))
            }
        }
    }
}