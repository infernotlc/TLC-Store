package com.tlh.afinal.screens.in_app.my_orders

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tlh.afinal.R
import com.tlh.afinal.databinding.FragmentMyOrdersBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyOrdersFragment : Fragment() {
    private val viewModel: MyOrdersViewModel by viewModels()
    private lateinit var binding: FragmentMyOrdersBinding
    private lateinit var adapter: MyOrdersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MyOrdersAdapter()
        binding.ordersRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.ordersRecyclerView.adapter = adapter
        binding.addOrderButton.setOnClickListener {
            Toast.makeText(context, "You can choose from our various of products", Toast.LENGTH_SHORT).show()
        }
        val userIds = resources.getStringArray(R.array.profile_numbers)

        binding.userSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedUserId = userIds[position].toInt()
                viewModel.fetchOrdersForUser(selectedUserId)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        viewModel.orders.observe(viewLifecycleOwner) { orders ->
            if (orders.isNotEmpty()) {
                adapter.orders = orders
            } else {
                Toast.makeText(context, "No orders found for selected user", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
