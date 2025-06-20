package com.example.emtyapp.ui.product.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.emtyapp.R
import com.example.emtyapp.data.Entities.Product

@Composable
fun ProductsList(
    products: List<Product>,
    onNavigateToDetails: (String) -> Unit,
    onAddToCart: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        item {
            // --- Header Banner ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFB6A17A)) // Bamboo-style beige/green tone
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .height(50.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Nos Produits",
                    style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    modifier = Modifier.weight(3f)
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        items(products) { product ->
            ProductItem(
                product = product,
                onNavigateToDetails = onNavigateToDetails,
                onAddToCart = { onAddToCart(product.id) } // Wrap in a lambda
            )
        }
    }
}