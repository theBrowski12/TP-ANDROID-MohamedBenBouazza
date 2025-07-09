package com.example.emtyapp.data.Repository

import android.util.Log
//import androidx.compose.ui.autofill.ContentType
import com.example.emtyapp.data.API.ProductApi
import com.example.emtyapp.data.Entities.Order
import com.example.emtyapp.data.Entities.Product
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import javax.inject.Inject
import android.app.Application
import io.ktor.http.contentType

class ProductRepository @Inject constructor(
    private val api: ProductApi

) {
    private val httpClient = HttpClient()


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

    suspend fun createOrder(order: Order) {
        try {
            val response: HttpResponse = httpClient.post("http://192.168.11.198/orders") {
                contentType(ContentType.Application.Json)
                setBody(order)
            }
            if (!response.status.isSuccess()) {
                throw Exception("Erreur lors de la création de la commande : ${response.status}")
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "Erreur createOrder: ${e.message}")
            throw e
        }
    }

}

