package com.example.emtyapp.data.Entities

import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("_id") val id: String? = null, // nullable
    val userId: String = "",
    val date: String = "",
    val total: Double = 0.0,
    val status: String = "En attente",
    @SerializedName("items") val items: List<OrderItem> = emptyList()
)
data class OrderItem(
    val productId: String,
    val title: String,
    val quantity: Int,
    val price: Double
)