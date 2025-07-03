package com.example.emtyapp.data.Repository

import android.util.Log
import com.example.emtyapp.data.API.OrderApi
import com.example.emtyapp.data.Entities.Order
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val api: OrderApi
) {
    private val httpClient = HttpClient()

    suspend fun getOrdersForUser(userId: String): List<Order> {
        return try {
            val orders = api.getOrdersForUser(userId)
            Log.d("OrderRepository", "Orders received: $orders")
            orders
        } catch (e: Exception) {
            Log.e("OrderRepository", "Error fetching orders", e)
            emptyList()
        }
    }

    suspend fun createOrder(order: Order) {
        api.createOrder(order)
    }

    suspend fun updateOrderStatus(orderId: String, status: String): Boolean {
        return try {
            api.updateOrderStatus(orderId, mapOf("status" to status))
            true
        } catch (e: Exception) {
            Log.e("OrderRepository", "Erreur mise à jour statut: ${e.message}")
            false
        }
    }

    suspend fun deleteOrder(orderId: String): Boolean {
        return try {
            api.deleteOrder(orderId)
            true
        } catch (e: Exception) {
            Log.e("OrderRepository", "Erreur suppression commande: ${e.message}")
            false
        }
    }
    suspend fun getAllOrders(): List<Order> {
        return try {
            api.getAllOrders()
        } catch (e: Exception) {
            Log.e("OrderRepository", "Erreur récupération commandes admin", e)
            emptyList()
        }
    }

}
