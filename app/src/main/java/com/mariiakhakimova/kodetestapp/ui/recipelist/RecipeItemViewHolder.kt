package com.mariiakhakimova.kodetestapp.ui.recipelist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mariiakhakimova.kodetestapp.R
import com.mariiakhakimova.kodetestapp.data.model.Recipe
import com.squareup.picasso.Picasso

class RecipeItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val tvName: TextView = itemView.findViewById(R.id.tvName)
    private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
    private val tvDifficulty: TextView = itemView.findViewById(R.id.tvDifficulty)
    private val ivImage: ImageView = itemView.findViewById(R.id.ivImage)
    private val clRoot: ConstraintLayout = itemView.findViewById(R.id.clRoot)

    fun bind(recipe: Recipe, recipeInfoListener: RecipeInfoListener) {
        tvName.setText(recipe.name)
        tvDescription.setText(recipe.description)
        tvDifficulty.setText(recipe.difficulty.toString())
        Picasso.get()
            .load(recipe.images[0])
            .into(ivImage)
        clRoot.setOnClickListener{
            recipeInfoListener.openRecipeInfo(recipe)
        }
    }
}