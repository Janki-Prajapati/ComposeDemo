package com.jp.test.composedemo.network

import retrofit2.http.GET

interface ApiService {

    @GET("tags")
    suspend fun getRecipesTags() : List<String>
}