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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        item {
            // --- Header Banner ---
            androidx.compose.foundation.layout.Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFB6A17A)) // Bamboo-style beige/green tone
                    .padding(horizontal = 16.dp),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .height(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))

                androidx.compose.material3.Text(
                    text = "Nos Produits",
                    style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    modifier = Modifier.weight(3f)

                )

                androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))
            }

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(12.dp))
        }

        items(products) { product ->
            ProductItem(product, onNavigateToDetails)
        }
    }
}
