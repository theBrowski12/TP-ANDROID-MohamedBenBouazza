package com.example.emtyapp.data.Repository

import com.example.emtyapp.R
import com.example.emtyapp.data.Entities.Product
import kotlinx.coroutines.delay
import javax.inject.Inject

class ProductRepository @Inject constructor() {
    suspend fun getProducts(): List<Product> {
        // Simulate fetching data from a remote server or database
        delay(2000)
        return listOf(
            Product("PR001", "iPhone 14", 6900.0, 7999.0, imageResId = R.drawable.iphone14, 5, "new iPhone 14"),
            Product("PR002", "Tablette Samsung", 2500.0, 5888.0, R.drawable.samsungtab, 8,"new tablette samsung"),
            Product("PR003", "Trotinette Dualtron2", 4889.0, 6888.0, R.drawable.trotinette, 12,"new trotinette"),
            Product("PR006", "Samsung TV", 3790.0, 4589.0, imageResId = R.drawable.tvsamsung, 19,"new Smasung TV"),
            Product("PR007", "Upcoming product", 0.0, 0.0, R.drawable.placeholder, 10, "new product"),
            Product("PR008", "Digital Logo Product", 50.0, 200.0, R.drawable.logo, 16, "Digital product"),
        )
    }
    suspend fun getProductById(id: String): Product? {
        return getProducts().find { it.id == id }
    }
}