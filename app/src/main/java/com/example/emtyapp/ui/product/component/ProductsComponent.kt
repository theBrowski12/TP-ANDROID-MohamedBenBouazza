package com.example.emtyapp.ui.product.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.emtyapp.R
import com.example.emtyapp.data.Entities.Product

@Composable
fun ProductsList(products: List<Product>, onNavigateToDetails: (String) -> Unit) {
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ){
        item {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp, max = 150.dp) // vertically stretches in a nice range
                    .padding(bottom = 12.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White), // or any branding background
                contentScale = ContentScale.Fit // or .Crop if you want full cover
            )
        }
        items(products) { product ->
            ProductItem(product, onNavigateToDetails)
        }
    }
}