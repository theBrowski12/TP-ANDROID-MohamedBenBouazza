package com.example.emtyapp.data

import com.example.emtyapp.R
import com.example.emtyapp.model.Product

val sampleProducts = listOf(
    Product("PR001", "iPhone 14", 6900.0, 7999.0, imageResId = R.drawable.iphone14, 5),
    Product("PR002", "Tablette Samsung", 2500.0, 5888.0, R.drawable.samsungtab, 8),
    Product("PR003", "Trotinette Dualtron2", 4889.0, 6888.0, R.drawable.trotinette, 12),
    Product("PR006", "Samsung TV", 3790.0, 4589.0, imageResId = R.drawable.tvsamsung, 19),
    Product("PR007", "Upcoming product", 0.0, 0.0, R.drawable.placeholder, 10),
    Product("PR008", "Digital Logo Product", 50.0, 200.0, R.drawable.logo, 16),
        )