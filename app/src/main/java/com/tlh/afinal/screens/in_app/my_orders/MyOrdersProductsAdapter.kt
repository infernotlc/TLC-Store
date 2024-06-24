package com.tlh.afinal.screens.in_app.my_orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tlh.afinal.databinding.OrderProductItemRowBinding
import com.tlh.afinal.model.in_app_service.Product

class MyOrdersProductsAdapter : RecyclerView.Adapter<MyOrdersProductsAdapter.OrderProductViewHolder>() {

    inner class OrderProductViewHolder(val binding: OrderProductItemRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var products: List<Product>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderProductViewHolder {
        val binding = OrderProductItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderProductViewHolder, position: Int) {
        val product = products[position]

        holder.binding.apply {
            productTitleTextView.text = product.title
            productPriceTextView.text = "${product.price} USD"
            productQuantityTextView.text = "Quantity: ${product.quantity}"
            Glide.with(holder.itemView.context).load(product.thumbnail).into(productThumbnailImageView)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}