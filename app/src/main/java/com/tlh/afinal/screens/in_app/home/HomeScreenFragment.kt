package com.tlh.afinal.screens.in_app.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tlh.afinal.adapter.ProductAdapter
import com.tlh.afinal.databinding.FragmentHomeScreenBinding
import com.tlh.afinal.model.in_app_service.CartItemRequest
import com.tlh.afinal.model.in_app_service.Product
import com.tlh.afinal.model.in_app_service.ProductViewModel
import com.tlh.afinal.data.local.room.RoomViewModel
import com.tlh.afinal.screens.in_app.cart.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {
    val viewModel: ProductViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private val roomViewModel: RoomViewModel by viewModels()
    private lateinit var adapter: ProductAdapter
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter
        adapter = ProductAdapter(this, roomViewModel, this::onAddToCartClicked)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.products.observe(viewLifecycleOwner) { products ->
            if (products.isNotEmpty()) {
                adapter.products = products
            } else {
                // Handle empty state
                Toast.makeText(context, "No products found", Toast.LENGTH_SHORT).show()
            }
        }

        // Listen to changes in favorite products
        roomViewModel.favoriteProducts.observe(viewLifecycleOwner) { favoriteProducts ->
            val favoriteProductIds = favoriteProducts.map { it.id }.toSet()
            adapter.products.forEach { product ->
                product.isFavorite = favoriteProductIds.contains(product.id)
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun onAddToCartClicked(product: Product) {
        val cartItem = CartItemRequest(product.id, 1)  // Varsayılan olarak 1 adet eklenir
        lifecycleScope.launch {
            try {
                val response = cartViewModel.addToCart(cartItem)
                // when cart added completed after processes
                Snackbar.make(binding.root, "${product.title} added to cart", Snackbar.LENGTH_LONG).show()
            } catch (e: Exception) {
                Snackbar.make(binding.root, "Failed to add product to cart", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
