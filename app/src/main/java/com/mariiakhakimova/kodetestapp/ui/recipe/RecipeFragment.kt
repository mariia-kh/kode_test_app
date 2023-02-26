package com.mariiakhakimova.kodetestapp.ui.recipe

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mariiakhakimova.kodetestapp.R
import com.mariiakhakimova.kodetestapp.data.model.Recipe
import com.mariiakhakimova.kodetestapp.databinding.RecipeFragmentBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

class RecipeFragment : Fragment() {

    private lateinit var binding: RecipeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = RecipeFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        extractData()
        setListener()
    }

    private fun setListener() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun extractData() {
        val recipe: Recipe = requireArguments().getSerializable(RECEPT) as Recipe
        binding.tvName.text = recipe.name
        Picasso.get()
            .load(recipe.images[0])
            .into(binding.ivImage)
        binding.tvDifficulty.text = getString(R.string.difficulty, recipe.difficulty)
        binding.tvDescription.text = recipe.description
        binding.tvInstructions.text = Html.fromHtml(recipe.instructions)
        val lastUpdated = recipe.lastUpdated * 1000L
        binding.tvLastUpdated.text = SimpleDateFormat(dd_MM_yyyy_FORMAT).format(lastUpdated)

    }

    companion object {
        const val RECEPT = "recept"
        const val dd_MM_yyyy_FORMAT = "dd.MM.yyyy"
    }
}