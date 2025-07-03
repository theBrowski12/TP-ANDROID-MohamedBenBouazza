package com.example.emtyapp.ui.order

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emtyapp.data.Entities.Order
import com.example.emtyapp.data.Repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepository
) : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    fun loadUserOrders(userId: String) {
        viewModelScope.launch {
            val orders = repository.getOrdersForUser(userId)
            Log.d("OrderViewModel", "Loaded orders: $orders")
            _orders.value = orders
        }
    }

    fun createOrder(order: Order, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.createOrder(order)
                onSuccess()
            } catch (e: Exception) {
                Log.e("Order", "Erreur lors de la création de la commande", e)
            }
        }
    }

    fun updateOrderStatus(orderId: String, status: String) {
        viewModelScope.launch {
            if (repository.updateOrderStatus(orderId, status)) {
                _orders.update { orders ->
                    orders.map {
                        if (it.id == orderId) it.copy(status = status) else it
                    }
                }
            }
        }
    }
    fun deleteOrder(orderId: String) {
        viewModelScope.launch {
            val success = repository.deleteOrder(orderId)
            if (success) {
                _orders.update { orders -> orders.filterNot { it.id == orderId } }
            } else {
                Log.e("OrderViewModel", "Échec suppression commande $orderId")
            }
        }
    }



}
