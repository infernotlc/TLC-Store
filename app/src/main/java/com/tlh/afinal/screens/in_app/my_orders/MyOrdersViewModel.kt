package com.tlh.afinal.screens.in_app.my_orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlh.afinal.model.in_app_service.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyOrdersViewModel @Inject constructor(private val repository: MyOrdersRepository) : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> get() = _orders

    fun fetchOrdersForUser(userId: Int) {
        viewModelScope.launch {
            val orders = repository.fetchOrdersForUser(userId)
            _orders.value = orders
        }
    }
}
