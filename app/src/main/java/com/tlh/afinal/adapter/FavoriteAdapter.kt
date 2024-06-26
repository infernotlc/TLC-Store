package com.tlh.afinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tlh.afinal.R
import com.tlh.afinal.databinding.ProductRecyclerRowBinding

import com.tlh.afinal.data.local.room.ProductRoom
import com.tlh.afinal.data.local.room.RoomViewModel
import com.tlh.afinal.screens.in_app.favorite_products.FavoriteProductsFragment
import com.tlh.afinal.screens.in_app.favorite_products.FavoriteProductsFragmentDirections
import com.tlh.afinal.screens.in_app.home.HomeScreenFragmentDirections


class FavoriteAdapter(
    private val fragment: Fragment,
    private val viewModel: RoomViewModel
) : RecyclerView.Adapter<FavoriteAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ProductRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<ProductRoom>() {
        override fun areItemsTheSame(oldItem: ProductRoom, newItem: ProductRoom): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductRoom, newItem: ProductRoom): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var products: List<ProductRoom>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ProductRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        holder.binding.apply {
            titleTextView.text = product.title
            priceTextView.text = "Only ${product.price} USD!!"
            discountTextView.text = "${product.discountPercentage} OFF"
            ratingTextView.text = product.rating.toString()
            Glide.with(holder.itemView).load(product.thumbnail).into(thumbnailImageView)

            // Handle the heart icon click
            imageButton2.setOnClickListener {
                val isFavorite = product.isFavorite
                product.isFavorite = !isFavorite
                updateHeartIcon(imageButton2, product.isFavorite)

                if (product.isFavorite) {
                    viewModel.addFavoriteProduct(product)
                } else {
                    viewModel.removeFavoriteProduct(product.id)
                }
            }

            // Set initial heart icon state
            updateHeartIcon(imageButton2, product.isFavorite)

            // Navigate to product detail
            root.setOnClickListener {

                val action =
                    FavoriteProductsFragmentDirections.actionFavoriteProductsFragmentToProductDetailFragment(
                        product.id
                    )
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
