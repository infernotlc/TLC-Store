package com.tlh.afinal.screens.in_app.favorite_products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tlh.afinal.adapter.FavoriteAdapter
import com.tlh.afinal.databinding.FragmentFavoriteProductsBinding
import com.tlh.afinal.room.RoomViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteProductsFragment : Fragment() {

    private val viewModel: RoomViewModel by viewModels()
    private lateinit var adapter: FavoriteAdapter
    private var _binding: FragmentFavoriteProductsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoriteAdapter(this,viewModel)
        binding.favRecyclerVlew.adapter = adapter
        binding.favRecyclerVlew.layoutManager = LinearLayoutManager(context)

        viewModel.favoriteProducts.observe(viewLifecycleOwner) { products ->
            if (products.isNotEmpty()) {
                adapter.products = products
            } else {
                Toast.makeText(context, "No favorite products found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

