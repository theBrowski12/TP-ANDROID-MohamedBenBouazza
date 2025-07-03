package com.example.emtyapp.data.API

import com.example.emtyapp.data.Entities.Order
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderApi {
    @GET("orders/user/{userId}")
    suspend fun getOrdersForUser(@Path("userId") userId: String): List<Order>

    @PUT("orders/{id}")
    suspend fun updateOrderStatus(
        @Path("id") id: String,
        @Body statusUpdate: Map<String, String>  // <-- Ici on attend une map
    )

    @POST("orders")
    suspend fun createOrder(@Body order: Order)

    @DELETE("orders/{id}")
    suspend fun deleteOrder(@Path("id") id: String)

    @GET("orders")
    suspend fun getAllOrders(): List<Order>


}
