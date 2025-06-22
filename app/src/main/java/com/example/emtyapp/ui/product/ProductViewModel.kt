package com.example.emtyapp.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emtyapp.data.Entities.CartItem
import com.example.emtyapp.data.Entities.Product
import com.example.emtyapp.data.Repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    // Store all products for search functionality
    private var _allProducts: List<Product> = emptyList()

    // Selected product for details view
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct

    // Main state containing products, cart, etc.
    private val _state = MutableStateFlow(ProductViewState())
    val state: StateFlow<ProductViewState> = _state

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    // Cart total
    private val _cartTotal = MutableStateFlow(0.0)
    val cartTotal: StateFlow<Double> = _cartTotal

    fun handleIntent(intent: ProductIntent) {
        when (intent) {
            is ProductIntent.LoadProducts -> {
                viewModelScope.launch {
                    loadProducts()
                }
            }
            is ProductIntent.AddToCart -> addToCart(intent.productId)
            is ProductIntent.RemoveFromCart -> removeFromCart(intent.productId)
            is ProductIntent.ClearCart -> clearCart()
            is ProductIntent.IncrementQuantity -> updateQuantity(intent.productId, 1)
            is ProductIntent.DecrementQuantity -> updateQuantity(intent.productId, -1)
            is ProductIntent.SearchProducts -> searchProducts(intent.query)
            is ProductIntent.FilterByCategory -> {
                _state.value = _state.value.copy(
                    products = _allProducts.filter {
                        it.category.equals(intent.category, ignoreCase = true)
                    }
                )
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val products = repository.getProducts()
                _allProducts = products // Store all products for searching
                _state.update {
                    it.copy(
                        isLoading = false,
                        products = products,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Error fetching products"
                    )
                }
            }
        }
    }

    private fun searchProducts(query: String) {
        if (query.isEmpty()) {
            // If search is empty, show all products
            _state.update { it.copy(products = _allProducts) }
        } else {
            // Filter products based on search query
            _state.update {
                it.copy(
                    products = _allProducts.filter { product ->
                        product.name.contains(query, ignoreCase = true) ||
                                (product.description?.contains(query, ignoreCase = true) ?: false)
                    }
                )
            }
        }
    }

    fun loadProductById(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val product = repository.getProductById(id)
                _state.update {
                    it.copy(
                        isLoading = false,
                        selectedProduct = product,
                        error = if (product == null) "Product not found" else null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load product"
                    )
                }
            }
        }
    }

    private fun addToCart(productId: String) {
        viewModelScope.launch {
            repository.getProductById(productId)?.let { product ->
                _cartItems.update { currentItems ->
                    val existingItem = currentItems.find { it.product.id == productId }
                    if (existingItem != null) {
                        currentItems.map {
                            if (it.product.id == productId) it.copy(quantity = it.quantity + 1)
                            else it
                        }
                    } else {
                        currentItems + CartItem(product, 1)
                    }
                }
                calculateTotal()
            }
        }
    }

    private fun removeFromCart(productId: String) {
        _cartItems.update { currentItems ->
            currentItems.filterNot { it.product.id == productId }
        }
        calculateTotal()
    }

    private fun clearCart() {
        _cartItems.update { emptyList() }
        _cartTotal.value = 0.0
    }

    private fun updateQuantity(productId: String, change: Int) {
        _cartItems.update { currentItems ->
            currentItems.map { item ->
                if (item.product.id == productId) {
                    val newQuantity = (item.quantity + change).coerceAtLeast(1)
                    item.copy(quantity = newQuantity)
                } else {
                    item
                }
            }
        }
        calculateTotal()
    }

    private fun calculateTotal() {
        _cartTotal.value = _cartItems.value.sumOf { it.product.price * it.quantity }
    }

    fun getCartItemCount(): Int {
        return _cartItems.value.sumOf { it.quantity }
    }
}