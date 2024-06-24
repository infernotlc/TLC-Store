package com.tlh.afinal.screens.in_app.my_orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tlh.afinal.databinding.OrderItemRowBinding
import com.tlh.afinal.model.in_app_service.Order

class MyOrdersAdapter : RecyclerView.Adapter<MyOrdersAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(val binding: OrderItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val productsAdapter = MyOrdersProductsAdapter()

        init {
            binding.orderProductsRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = productsAdapter
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var orders: List<Order>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]

        holder.binding.apply {
            orderTotalTextView.text = "Total: ${order.total} USD"
            orderDiscountedTotalTextView.text = "Discounted Total: ${order.discountedTotal} USD"
            orderTotalProductsTextView.text = "Total Products: ${order.totalProducts}"
            orderTotalQuantityTextView.text = "Total Quantity: ${order.totalQuantity}"

            holder.productsAdapter.products = order.products
        }
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}
