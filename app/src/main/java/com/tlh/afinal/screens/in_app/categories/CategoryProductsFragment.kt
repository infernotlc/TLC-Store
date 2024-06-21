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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tlh.afinal.adapter.CategoryProductAdapter
import com.tlh.afinal.databinding.FragmentCategoryProductsBinding
import com.tlh.afinal.model.in_app_service.Product
import com.tlh.afinal.model.in_app_service.ProductAPI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CategoryProductsFragment : Fragment() {

    private var _binding: FragmentCategoryProductsBinding? = null
    private val binding get() = _binding!!
    private val args: CategoryProductsFragmentArgs by navArgs()
    private lateinit var adapter: CategoryProductAdapter

    @Inject
    lateinit var productService: ProductAPI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CategoryProductAdapter(this::onProductClicked)
        binding.categoryproductrecyclerView.adapter = adapter
        binding.categoryproductrecyclerView.layoutManager = LinearLayoutManager(context)

        fetchProducts(args.slug)
    }

    private fun fetchProducts(categorySlug: String) {
        lifecycleScope.launch {
            try {
                val productResponse = productService.getProductsByCategory(categorySlug)
                val products = productResponse.products // Assuming products is a List<Product> in ProductResponse

                adapter.submitList(products)
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching products: ${e.message}", e)
                Toast.makeText(context, "Error fetching products", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onProductClicked(product: Product) {
        val action = CategoryProductsFragmentDirections.actionCategorieProductsFragmentToProductDetailFragment(product.id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "CategoryProductsFragment"
    }
}