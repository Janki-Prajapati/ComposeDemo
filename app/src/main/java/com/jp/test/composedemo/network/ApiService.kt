package com.jp.test.composedemo.network

import com.jp.test.composedemo.domain.model.ApiRecipesFromTags
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @GET("tags")
    suspend fun getRecipesTags() : List<String>
    @GET("tag/{_tag}")
    suspend fun getRecipesByTags(@Path(value = "_tag") tag : String) : ApiRecipesFromTags
}