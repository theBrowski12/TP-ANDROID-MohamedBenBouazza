package com.example.emtyapp.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emtyapp.data.Entities.Product
import com.example.emtyapp.data.Repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel()  {
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct



    private val _state = MutableStateFlow(ProductViewState())
    val state: StateFlow<ProductViewState> = _state

    fun handleIntent(intent: ProductIntent) {
        when (intent) {
            is ProductIntent.LoadProducts -> {
                viewModelScope.launch {
                    loadProducts()
                }
            }
        }
    }

    private suspend fun loadProducts() {
        _state.value = _state.value.copy(isLoading = true, error = null)
        try {
            val products = repository.getProducts()
            _state.value = ProductViewState(isLoading = false, products = products)
        } catch (e: Exception) {
            _state.value =
                ProductViewState(isLoading = false, error = e.message ?: "Error fetching products")
        }
    }
    fun loadProductById(id: String) {
        viewModelScope.launch {
            val product = repository.getProductById(id)
            _selectedProduct.value = product
        }
    }
}