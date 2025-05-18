package com.example.emtyapp.ui.product.component

import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.emtyapp.data.Entities.Product
import com.example.emtyapp.data.Repository.ProductRepository


@Composable
fun DetailsScreen(productId: String, onBuy: () -> Unit) {
    val repository = remember { ProductRepository() }

    val productState by produceState<Product?>(initialValue = null) {
        value = repository.getProductById(productId)
    }

    if (productState == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        val product = productState!!

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Image with background & shape
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF5F5F5)), // Light gray background
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = product.imageResId),
                    contentDescription = product.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Product Name
            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Price
            Text(
                text = "${product.price} DH",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF388E3C) // Green
                )
            )

            // Old Price
            Text(
                text = "${product.oldPrice} DH",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
            val quantityColor = if (product.quantity < 10) Color.Red else Color(0xFF1E88E5) // Blue

            // Quantity
            Text(
                text = "Disponible : ${product.quantity} unités",
                style = MaterialTheme.typography.bodyMedium,
                color = quantityColor
            )

            Spacer(modifier = Modifier.height(16.dp))
            if (product.quantity < 10) {
                Text(
                    text = "⚠️ Stock faible",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFFF9800), // Orange
                    fontWeight = FontWeight.Medium
                )
            }
            // Description title
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.align(Alignment.Start)
            )

            // Description content
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.Start),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(18.dp))


            Button(onClick = onBuy) {

                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Retour")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Acheter")
            }
        }
    }
}
