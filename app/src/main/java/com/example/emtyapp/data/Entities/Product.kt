package com.example.emtyapp.data.Entities

import com.google.gson.annotations.SerializedName
import java.net.URL

data class Product(
    @SerializedName("_id")
    val id: String,
    @SerializedName("productTitle")
    val name: String,
    @SerializedName("productPrice")
    val price: Double,
    @SerializedName("productOldPrice")
    val oldPrice: Double,
    @SerializedName("productImageResURL")
    val imageResURL: String,
    @SerializedName("productQuantity")
    val quantity: Int,
    @SerializedName("productDescription")
    val description: String,
    @SerializedName("productCategory")
    val category: String

)