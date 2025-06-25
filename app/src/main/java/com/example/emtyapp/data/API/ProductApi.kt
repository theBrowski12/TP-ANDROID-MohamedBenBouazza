package com.example.emtyapp.data.API

import com.example.emtyapp.data.Entities.Product
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path


interface ProductApi {
    @GET("Products.JSON")
    suspend fun getProducts(): List<Product>
    @PUT("products/{id}")
    suspend fun updateProduct(@Path("id") id: String, @Body product: Product): Product

}