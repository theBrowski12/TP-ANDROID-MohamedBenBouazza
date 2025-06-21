package com.example.emtyapp.data.Repository

import android.util.Log
import com.example.emtyapp.R
import com.example.emtyapp.data.API.ProductApi
import com.example.emtyapp.data.Entities.Product
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.math.log

    class ProductRepository @Inject constructor(
        private val api: ProductApi
    ) {
        suspend fun getProducts(): List<Product> {
            return try {
                api.getProducts().map { product ->
                    product.copy(
                        imageResId = mapProductImage(product.id)
                    )
                }
            } catch (e: Exception) {
                emptyList()
            }
        }

        private fun mapProductImage(productId: String): Int {
            return when (productId) {
                "PR001" -> R.drawable.iphone14
                "PR002" -> R.drawable.samsungtab
                "PR003" -> R.drawable.trotinette
                "PR006" -> R.drawable.tvsamsung
                "PR008" -> R.drawable.logo
                else -> R.drawable.placeholder
            }
        }

        suspend fun getProductById(id: String): Product? {
            return try {
                val product = api.getProducts().find { it.id == id }?.copy(
                    imageResId = mapProductImage(id)
                )
                if (product == null) {
                    Log.w("ProductRepository", "Product with id $id not found")
                }
                product
            } catch (e: Exception) {
                Log.e("ProductRepository", "Error fetching product $id: ${e.message}", e)
                null
            }
        }
    }
