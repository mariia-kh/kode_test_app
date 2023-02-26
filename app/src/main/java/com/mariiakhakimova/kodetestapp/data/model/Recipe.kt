package com.mariiakhakimova.kodetestapp.data.model

import java.io.Serializable

data class Recipe(
    val uuid: String?,  //уникальный id
    val name: String?,  //название рецепта
    val images: List<String>,  //фотографии к рецепту
    val lastUpdated: Int,   //последнее обновление рецепта
    val description: String?,   //описание рецепта
    val instructions: String?,  //инструкция по приготовлению рецепта
    val difficulty: Int?,   //уровень сложности
) : Serializable