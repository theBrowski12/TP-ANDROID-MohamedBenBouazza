package com.example.emtyapp.data.API

import com.example.emtyapp.data.Entities.Product
import retrofit2.http.GET


interface ProductApi {
    @GET("Products.JSON")
    suspend fun getProducts(): List<Product>

}