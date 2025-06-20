package com.example.emtyapp.ui.product

// Intent
sealed class ProductIntent {
    object LoadProducts : ProductIntent()
    data class AddToCart(val productId: String) : ProductIntent()
    data class RemoveFromCart(val productId: String) : ProductIntent()
    data class IncrementQuantity(val productId: String) : ProductIntent()
    data class DecrementQuantity(val productId: String) : ProductIntent()
    object ClearCart : ProductIntent()
}