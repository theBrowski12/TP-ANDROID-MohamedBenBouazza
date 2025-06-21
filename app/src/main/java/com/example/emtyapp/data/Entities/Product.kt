package com.example.emtyapp.data.Entities

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("productID")
    val id: String,
    @SerializedName("productTitle")
    val name: String,
    @SerializedName("productPrice")
    val price: Double,
    @SerializedName("productOldPrice")
    val oldPrice: Double,
    @SerializedName("productImageResId")
    val imageResId: Int,
    @SerializedName("productQuantity")
    val quantity: Int,
    @SerializedName("productDescription")
    val description: String,
    @SerializedName("productCategory")
    val category: String

)