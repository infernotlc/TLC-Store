package com.tlh.afinal.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tlh.afinal.R
import com.tlh.afinal.databinding.ProductRecyclerRowBinding
import com.tlh.afinal.model.in_app_service.Product
import com.tlh.afinal.data.local.room.ProductRoom
import com.tlh.afinal.data.local.room.RoomViewModel
import com.tlh.afinal.screens.in_app.home.HomeScreenFragment
import com.tlh.afinal.screens.in_app.home.HomeScreenFragmentDirections


class ProductAdapter(
    private val fragment: Fragment,
    private val roomViewModel: RoomViewModel,
    private val onAddToCartClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ProductRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var products: List<Product>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            discountTextView.text = "%${product.discountPercentage} OFF"
            ratingTextView.text = product.rating.toString()
            Glide.with(holder.itemView).load(product.thumbnail).into(thumbnailImageView)

            // Handle the heart icon click
            imageButton2.setOnClickListener {
                val isFavorite = product.isFavorite
                product.isFavorite = !isFavorite
                updateHeartIcon(imageButton2, product.isFavorite)

                if (product.isFavorite) {
                    roomViewModel.addFavoriteProduct(product.toRoomProduct())
                } else {
                    roomViewModel.removeFavoriteProduct(product.id)
                }
                notifyItemChanged(position)  // Update the specific item
            }

            // Handle the add to cart button click
            addToCartButton.setOnClickListener {
                val quantity = 1 // or you can make this dynamic
                (fragment as HomeScreenFragment).viewModel.addToCart(product.id, quantity)
                Toast.makeText(holder.itemView.context, "${product.title} added to cart", Toast.LENGTH_SHORT).show()
            }

            // Set initial heart icon state
            updateHeartIcon(imageButton2, product.isFavorite)

            // Navigate to product detail
            root.setOnClickListener {
                val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToProductDetailFragment(product.id)
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

// Extension function to convert Product to ProductRoom
fun Product.toRoomProduct(): ProductRoom {
    return ProductRoom(
        id = this.id,
        title = this.title,
        price = this.price,
        discountPercentage = this.discountPercentage,
        rating = this.rating,
        thumbnail = this.thumbnail,
        isFavorite = this.isFavorite
    )
}
