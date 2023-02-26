package com.mariiakhakimova.kodetestapp.data

import com.mariiakhakimova.kodetestapp.data.model.RecipesResponse
import retrofit2.http.GET

interface RecipeService {

    @GET("recipes.json")
    suspend fun getRecipes(): RecipesResponse
}