package com.tlh.afinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tlh.afinal.R
import com.tlh.afinal.databinding.ProductRecyclerRowBinding
import com.tlh.afinal.model.in_app_service.Product
import com.tlh.afinal.room.ProductRoom
import com.tlh.afinal.room.RoomViewModel
import com.tlh.afinal.screens.in_app.search_product.SearchProductFragmentDirections
import com.tlh.afinal.screens.in_app.search_product.SearchProductViewModel
import dagger.hilt.android.AndroidEntryPoint



class SearchAdapter(private val fragment: Fragment) : ListAdapter<Product, SearchAdapter.ProductViewHolder>(diffCallback) {

    inner class ProductViewHolder(val binding: ProductRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)

        holder.binding.apply {
            titleTextView.text = product.title
            priceTextView.text = "${product.price} USD"
            discountTextView.text = "${product.discountPercentage}% OFF"
            ratingTextView.text = "${product.rating}"
            Glide.with(holder.itemView).load(product.thumbnail).into(thumbnailImageView)

            // Handle the heart icon click
            imageButton2.setOnClickListener {
                product.isFavorite = !product.isFavorite
                updateHeartIcon(imageButton2, product.isFavorite)
                // Update favorite status in database
                val roomViewModel: RoomViewModel by fragment.viewModels()
                if (product.isFavorite) {
                    val roomProduct = ProductRoom(
                        id = product.id,
                        title = product.title,
                        price = product.price,
                        discountPercentage = product.discountPercentage,
                        rating = product.rating,
                        thumbnail = product.thumbnail,
                        isFavorite = product.isFavorite
                    )
                    roomViewModel.addFavoriteProduct(roomProduct)
                } else {
                    roomViewModel.removeFavoriteProduct(product.id)
                }
            }

            // Set initial heart icon state
            updateHeartIcon(imageButton2, product.isFavorite)

            // Navigate to product detail
            root.setOnClickListener {
                val action = SearchProductFragmentDirections.actionSearchProductFragmentToProductDetailFragment(product.id)
                fragment.findNavController().navigate(action)
            }
        }
    }

    private fun updateHeartIcon(button: ImageButton, isFavorite: Boolean) {
        if (isFavorite) {
            button.setImageResource(R.drawable.love)
        } else {
            button.setImageResource(R.drawable.heart)
        }
    }
}
