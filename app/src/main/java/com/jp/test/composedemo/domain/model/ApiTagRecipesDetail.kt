package com.jp.test.composedemo.domain.model

data class ApiTagRecipesDetail(
    var limit: Int? = null,
    var recipes: List<Recipe?>? = null,
    var skip: Int? = null,
    var total: Int? = null
) {
    data class Recipe(
        var caloriesPerServing: Int? = null,
        var cookTimeMinutes: Int? = null,
        var cuisine: String? = null,
        var difficulty: String? = null,
        var id: Int? = null,
        var image: String? = null,
        var ingredients: List<String?>? = null,
        var instructions: List<String?>? = null,
        var mealType: List<String?>? = null,
        var name: String? = null,
        var prepTimeMinutes: Int? = null,
        var rating: Double? = null,
        var reviewCount: Int? = null,
        var servings: Int? = null,
        var tags: List<String?>? = null,
        var userId: Int? = null
    )
}