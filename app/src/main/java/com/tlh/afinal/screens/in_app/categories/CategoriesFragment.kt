package com.tlh.afinal.screens.in_app.categories

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tlh.afinal.adapter.CategoryAdapter
import com.tlh.afinal.databinding.FragmentCategoriesBinding
import com.tlh.afinal.model.in_app_service.Category
import com.tlh.afinal.model.in_app_service.ProductAPI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CategoryAdapter

    @Inject
    lateinit var productService: ProductAPI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CategoryAdapter(this::onCategoryClicked)
        binding.categoriesRecyclerView.adapter = adapter
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.categoriesRecyclerView.visibility = View.VISIBLE

        fetchCategories()
    }

    private fun fetchCategories() {
        lifecycleScope.launch {
            try {
                val categories = productService.getCategories()
                adapter.submitList(categories)
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching categories: ${e.message}", e)
                Toast.makeText(context, "Error fetching categories", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onCategoryClicked(category: Category) {
        val action = CategoriesFragmentDirections.actionCategoriesFragmentToCategorieProductsFragment2(category.slug)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "CategoriesFragment"
    }
}