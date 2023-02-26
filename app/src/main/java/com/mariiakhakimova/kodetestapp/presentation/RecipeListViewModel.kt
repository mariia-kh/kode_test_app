package com.mariiakhakimova.kodetestapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariiakhakimova.kodetestapp.SingleLiveEvent
import com.mariiakhakimova.kodetestapp.data.Api
import com.mariiakhakimova.kodetestapp.data.RecipeService
import com.mariiakhakimova.kodetestapp.data.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeListViewModel : ViewModel() {

    private val listOfRecipe: RecipeService by lazy { Api.retrofit.create(RecipeService::class.java) }

    private val _recipesLiveData = MutableLiveData<List<Recipe>>()
    val recipesLiveData: LiveData<List<Recipe>> = _recipesLiveData
    val errorEvent = SingleLiveEvent<Throwable>()

    fun loadRecipeItem() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                listOfRecipe.getRecipes()
            }.onSuccess { recipesResponse ->
                _recipesLiveData.postValue(recipesResponse.recipes)
            }.onFailure { e ->
                e.printStackTrace()
                errorEvent.postValue(e)
            }
        }
    }
}