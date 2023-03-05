package com.mariiakhakimova.kodetestapp.ui.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariiakhakimova.kodetestapp.R
import com.mariiakhakimova.kodetestapp.data.model.Recipe
import com.mariiakhakimova.kodetestapp.databinding.RecipeListFragmentBinding
import com.mariiakhakimova.kodetestapp.presentation.RecipeListViewModel
import com.mariiakhakimova.kodetestapp.ui.recipe.RecipeFragment.Companion.RECIPE
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
    }

    private fun initList() {
        recipeAdapter = RecipeAdapter(object : RecipeInfoListener {
            override fun openRecipeInfo(recipe: Recipe) {
                val bundle = Bundle()
                bundle.putSerializable(RECIPE, recipe)
                findNavController().navigate(R.id.action_RecipeListFragment_to_RecipeFragment, bundle)
            }
        })
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvTitle.adapter = recipeAdapter
        binding.rvTitle.layoutManager = layoutManager
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                recipeListViewModel.recipesState.collectLatest {
                    recipeAdapter.setItems(it)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                recipeListViewModel.progressState.collectLatest { isProgress ->
                    binding.progressLayout.isVisible = isProgress
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                recipeListViewModel.errorEvent.collectLatest {
                    showError(it)
                }
            }
        }
    }

    private fun showError(throwable: Throwable) {
        with(AlertDialog.Builder(requireActivity())) {
            setTitle(R.string.error)
            setMessage(throwable.message)
            setCancelable(false)
            setPositiveButton(R.string.reload) { dialog, which ->
                recipeListViewModel.loadRecipeList()
                dialog.dismiss()
            }
            show()
        }
    }
}