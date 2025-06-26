package com.example.emtyapp.data.Repository

import android.util.Log
import com.example.emtyapp.R
import com.example.emtyapp.data.API.ProductApi
import com.example.emtyapp.data.Entities.Product
import javax.inject.Inject


class ProductRepository @Inject constructor(
    private val api: ProductApi
) {

    suspend fun getProducts(): List<Product> {
        return try {
            api.getProducts()
        } catch (e: Exception) {
            Log.e("ProductRepository", "Erreur récupération produits: ${e.message}")
            emptyList()
        }
    }

    suspend fun getProductById(id: String): Product? {
        return try {
            api.getProducts().find { it.id == id } ?: run {
                Log.w("ProductRepository", "Produit avec ID $id introuvable")
                null
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "Erreur produit ID $id: ${e.message}", e)
            null
        }
    }

    suspend fun updateProduct(product: Product): Product? {
        return try {
            api.updateProduct(product.id, product)
        } catch (e: Exception) {
            Log.e("ProductRepository", "Erreur mise à jour produit ${product.id}: ${e.message}", e)
            null
        }
    }
    suspend fun addProduct(product: Product): Boolean {
        return try {
            api.addProduct(product)
            true
        } catch (e: Exception) {
            Log.e("ProductRepository", "Erreur ajout produit: ${e.message}")
            false
        }
    }
    suspend fun deleteProduct(id: String): Boolean {
        return try {
            api.deleteProduct(id)
            true
        } catch (e: Exception) {
            Log.e("ProductRepository", "Erreur suppression produit $id : ${e.message}")
            false
        }
    }


}
