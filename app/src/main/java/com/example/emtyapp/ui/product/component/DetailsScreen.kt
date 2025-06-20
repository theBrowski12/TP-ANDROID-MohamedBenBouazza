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
import com.example.emtyapp.ui.product.ProductIntent
import com.example.emtyapp.ui.product.ProductViewModel


@Composable
fun DetailsScreen(productId: String, viewModel: ProductViewModel,onBuy: () -> Unit) {
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
                .background(Color(0xFFE8F5E9)) // Bamboo green background
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Product Image Card
            // Product Image Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp), // Reduced height
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp), // Padding inside the card
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = product.imageResId),
                        contentDescription = product.name,
                        contentScale = ContentScale.Fit, // Keep original size ratio
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }


            Spacer(modifier = Modifier.height(20.dp))

            // Product Name
            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32) // Bamboo green dark
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Price
            Text(
                text = "${product.price} DH",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50) // Green
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

            Spacer(modifier = Modifier.height(12.dp))

            val quantityColor = if (product.quantity < 10) Color.Red else Color(0xFF2E7D32)

            Text(
                text = "Disponible : ${product.quantity} unités",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                color = quantityColor
            )

            if (product.quantity < 10) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "⚠️ Stock faible",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFFFA000), // Warning orange
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Description
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1B5E20)
                ),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.Start),
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Buy Button
            Button(
                onClick = onBuy,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81C784)), // Bamboo-style green
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Acheter",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Acheter", color = Color.White)
            }
        }
    }
}

