package com.example.emtyapp.ui.product

// Intent
sealed class ProductIntent {
    object LoadProducts : ProductIntent()
}