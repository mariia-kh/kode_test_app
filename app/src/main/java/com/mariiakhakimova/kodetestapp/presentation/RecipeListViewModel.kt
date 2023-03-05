package com.mariiakhakimova.kodetestapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariiakhakimova.kodetestapp.data.Api
import com.mariiakhakimova.kodetestapp.data.RecipeService
import com.mariiakhakimova.kodetestapp.data.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RecipeListViewModel : ViewModel() {

    private val listOfRecipe: RecipeService by lazy { Api.retrofit.create(RecipeService::class.java) }

    private val _recipesState = MutableStateFlow<List<Recipe>>(listOf())
    val recipesState: StateFlow<List<Recipe>> = _recipesState.asStateFlow()

    private val _progressState = MutableStateFlow<Boolean>(true)
    val progressState: StateFlow<Boolean> = _progressState.asStateFlow()

    private val _errorEvent = Channel<Throwable>()
    val errorEvent = _errorEvent.receiveAsFlow()

    init {
        loadRecipeList()
    }

    fun loadRecipeList() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _progressState.emit(true)
                listOfRecipe.getRecipes()
            }.onSuccess { recipesResponse ->
                _progressState.emit(false)
                _recipesState.emit(recipesResponse.recipes)
            }.onFailure { e ->
                e.printStackTrace()
                _progressState.emit(false)
                _errorEvent.send(e)
            }
        }
    }
}