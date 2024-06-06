package com.jp.test.composedemo.network

import com.jp.test.composedemo.domain.model.ApiRecipesFromTags
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("tags")
    suspend fun getRecipesTags() : List<String>
    @GET("tag/{tagName}")
    suspend fun getRecipesByTags(@Path("tagName") tagName : String) : ApiRecipesFromTags
    @GET("search")
    suspend fun getSearchedRecipe(@Query("q") searchText : String) : ApiRecipesFromTags
}