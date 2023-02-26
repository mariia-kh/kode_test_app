package com.mariiakhakimova.kodetestapp.ui.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariiakhakimova.kodetestapp.R
import com.mariiakhakimova.kodetestapp.data.model.Recipe
import com.mariiakhakimova.kodetestapp.databinding.RecipeListFragmentBinding
import com.mariiakhakimova.kodetestapp.presentation.RecipeListViewModel
import com.mariiakhakimova.kodetestapp.ui.recipe.RecipeFragment.Companion.RECEPT

class RecipeListFragment : Fragment() {

    private lateinit var binding: RecipeListFragmentBinding
    private lateinit var recipeAdapter: RecipeAdapter
    private val recipeListViewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = RecipeListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        observeData()
        recipeListViewModel.loadRecipeItem()
    }

    private fun initList() {
        recipeAdapter = RecipeAdapter(object : RecipeInfoListener {
            override fun openRecipeInfo(recipe: Recipe) {
                val bundle = Bundle()
                bundle.putSerializable(RECEPT, recipe)
                findNavController().navigate(R.id.action_RecipeListFragment_to_RecipeFragment, bundle)
            }
        })
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvTitle.adapter = recipeAdapter
        binding.rvTitle.layoutManager = layoutManager
    }

    private fun observeData() {
        recipeListViewModel.recipesLiveData.observe(viewLifecycleOwner) {
            recipeAdapter.setItems(it)
        }
    }
}