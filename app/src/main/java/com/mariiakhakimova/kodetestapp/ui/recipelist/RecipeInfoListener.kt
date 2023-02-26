package com.mariiakhakimova.kodetestapp.ui.recipelist

import com.mariiakhakimova.kodetestapp.data.model.Recipe

interface RecipeInfoListener {
    fun openRecipeInfo(recipe: Recipe)
}