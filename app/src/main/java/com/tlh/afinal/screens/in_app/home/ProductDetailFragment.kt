package com.tlh.afinal.screens.in_app.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tlh.afinal.R
import com.tlh.afinal.adapter.ReviewsAdapter
import com.tlh.afinal.adapter.ViewPagerAdapter
import com.tlh.afinal.databinding.FragmentProductDetailBinding
import com.tlh.afinal.model.in_app_service.Product
import com.tlh.afinal.model.in_app_service.ProductViewModel
import com.tlh.afinal.data.local.room.ProductRoom
import com.tlh.afinal.data.local.room.RoomViewModel
import com.tlh.afinal.screens.in_app.search_product.SearchProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductViewModel by viewModels()
    private val r_viewModel: RoomViewModel by viewModels()
    private lateinit var currentProduct: Product
    private lateinit var roomProduct: ProductRoom

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productId = arguments?.getInt("id") ?: throw IllegalArgumentException("Product ID must be provided")

        lifecycleScope.launch {
            viewModel.products.observe(viewLifecycleOwner) { products ->
                val product = products.find { it.id == productId }
                product?.let {
                    currentProduct = it
                    roomProduct = ProductRoom(
                        id = it.id,
                        title = it.title,
                        price = it.price,
                        discountPercentage = it.discountPercentage,
                        rating = it.rating,
                        thumbnail = it.thumbnail,
                        isFavorite = it.isFavorite
                    )
                    bindProductDetails(it)
                }
            }
        }

        binding.imageButton.setOnClickListener {
            roomProduct.isFavorite = !roomProduct.isFavorite
            if (roomProduct.isFavorite) {
                r_viewModel.addFavoriteProduct(roomProduct)
            } else {
                r_viewModel.removeFavoriteProduct(roomProduct.id)
            }
            updateHeartIcon(binding.imageButton, roomProduct.isFavorite)
        }

        // Observe changes in the Room database
        r_viewModel.favoriteProducts.observe(viewLifecycleOwner) { favoriteProducts ->
            val isFavorite = favoriteProducts.any { it.id == productId }
            updateHeartIcon(binding.imageButton, isFavorite)
        }
    }

    private fun bindProductDetails(product: Product) {
        binding.apply {
            title.text = product.title
            price.text = "ONLY ${product.price} USD!!"
            discount.text = "${product.discountPercentage}% OFF"
            rating.text = "Rating: ${product.rating}"
            descriptionTextView.text = product.description
            categoryTextView.text = "Category: ${product.category}"
            stockTextView.text = "Stock: ${product.stock}"
            brandTextView.text = "Brand: ${product.brand}"
            skuTextView.text = "SKU: ${product.sku}"
            weightTextView.text = "Weight: ${product.weight}g"
            dimensionsTextView.text = "Dimensions: ${product.dimensions.width} x ${product.dimensions.height} x ${product.dimensions.depth} cm"
            warrantyTextView.text = "Warranty: ${product.warrantyInformation}"
            shippingTextView.text = "Shipping: ${product.shippingInformation}"
            availabilityTextView.text = "Availability: ${product.availabilityStatus}"
            returnPolicyTextView.text = "Return Policy: ${product.returnPolicy}"
            minimumOrderTextView.text = "Minimum Order: ${product.minimumOrderQuantity}"
            metaTextView.text = "Created at: ${product.meta.createdAt}, Updated at: ${product.meta.updatedAt}, Barcode: ${product.meta.barcode}"

            // Setup ViewPager for product images
            val viewPagerAdapter = ViewPagerAdapter(product.images)
            viewPager.adapter = viewPagerAdapter

            // Setup RecyclerView for reviews
            reviewRecyclerView.layoutManager = LinearLayoutManager(context)
            val reviewsAdapter = ReviewsAdapter(product.reviews)
            reviewRecyclerView.adapter = reviewsAdapter
        }
    }

    private fun updateHeartIcon(button: ImageButton, isFavorite: Boolean) {
        if (isFavorite) {
            button.setImageResource(R.drawable.love)
        } else {
            button.setImageResource(R.drawable.heart)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
