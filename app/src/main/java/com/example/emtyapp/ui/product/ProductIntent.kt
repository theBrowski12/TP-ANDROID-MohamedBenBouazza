package com.example.emtyapp.ui.product

import com.example.emtyapp.data.Entities.Product

// Intent
sealed class ProductIntent {
    object LoadProducts : ProductIntent()
    data class AddToCart(val productId: String) : ProductIntent()
    data class RemoveFromCart(val productId: String) : ProductIntent()
    data class IncrementQuantity(val productId: String) : ProductIntent()
    data class DecrementQuantity(val productId: String) : ProductIntent()
    data class SearchProducts(val query: String) : ProductIntent()
    data class FilterByCategory(val category: String) : ProductIntent()
    data class DeleteProduct(val product: Product) : ProductIntent()
    object ClearCart : ProductIntent()
}