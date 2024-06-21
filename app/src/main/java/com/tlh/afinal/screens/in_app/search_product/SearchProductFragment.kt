package com.tlh.afinal.screens.in_app.search_product


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tlh.afinal.adapter.ProductAdapter
import com.tlh.afinal.adapter.SearchAdapter
import com.tlh.afinal.databinding.FragmentSearchProductBinding
import com.tlh.afinal.room.RoomViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchProductFragment : Fragment() {

    private var _binding: FragmentSearchProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var s_adapter: SearchAdapter
    private val viewModel: SearchProductViewModel by viewModels()
    private val r_viewModel: RoomViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        s_adapter = SearchAdapter(this)
        binding.recyclerView.adapter = s_adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    viewModel.searchProducts(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank()) {
                    viewModel.searchProducts(newText)
                }
                return true
            }
        })

        viewModel.searchResults.observe(viewLifecycleOwner) { products ->
            s_adapter.submitList(products)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}