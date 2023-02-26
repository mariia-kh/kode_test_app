package com.mariiakhakimova.kodetestapp.ui.recipelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mariiakhakimova.kodetestapp.R
import com.mariiakhakimova.kodetestapp.data.model.Recipe

class RecipeAdapter(
    private val recipeInfoListener: RecipeInfoListener,
): RecyclerView.Adapter<RecipeItemViewHolder>(){

    private var items = listOf<Recipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recipe_item, parent, false)
        return RecipeItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecipeItemViewHolder, position: Int) {
        holder.bind(items[position], recipeInfoListener)
    }

    fun setItems(items: List<Recipe>) {
        this.items = items
        notifyDataSetChanged()
    }
}