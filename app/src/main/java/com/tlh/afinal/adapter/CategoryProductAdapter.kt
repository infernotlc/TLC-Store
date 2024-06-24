package com.tlh.afinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tlh.afinal.databinding.ProductRecyclerRowBinding
import com.tlh.afinal.model.in_app_service.Product

class CategoryProductAdapter(private val onItemClick: (Product) -> Unit) :
    ListAdapter<Product, CategoryProductAdapter.ProductViewHolder>(diffCallback) {

    inner class ProductViewHolder(val binding: ProductRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.titleTextView.text = product.title
            binding.priceTextView.text = "${product.price} USD"
            binding.ratingTextView.text = product.rating.toString()
            binding.discountTextView.text = "%${product.discountPercentage} OFF"
            binding.root.setOnClickListener { onItemClick(product) }
            Glide.with(binding.ratingImageView.context)//
                .load(product.thumbnail)
                .into(binding.thumbnailImageView)
        }
    }

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
        holder.bind(product)
    }
}
