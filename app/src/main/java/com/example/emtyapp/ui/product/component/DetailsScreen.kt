package com.example.emtyapp.ui.product.component

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.emtyapp.ui.product.ProductIntent
import com.example.emtyapp.ui.product.ProductViewModel

@Composable
fun DetailsScreen(
    productId: String,
    viewModel: ProductViewModel = hiltViewModel(),
    onBuy: () -> Unit
) {
    // Collect the entire state from ViewModel
    val state by viewModel.state.collectAsState()

    // Load product when screen opens or productId changes
    LaunchedEffect(productId) {
        viewModel.loadProductById(productId)
    }

    // Handle different states
    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        state.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.error ?: "Error loading product",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        state.selectedProduct == null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Product not found")
            }
        }
        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(Color(0xFFE8F5E9))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Product Image Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = state.selectedProduct!!.imageResId),
                            contentDescription = state.selectedProduct!!.name,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Product Name
                Text(
                    text = state.selectedProduct!!.name,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Price
                Text(
                    text = "${state.selectedProduct!!.price} DH",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )
                )

                // Old Price
                Text(
                    text = "${state.selectedProduct!!.oldPrice} DH",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Stock Information
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val quantityColor = if (state.selectedProduct!!.quantity < 10) Color.Red else Color(0xFF2E7D32)
                    Text(
                        text = "Disponible : ${state.selectedProduct!!.quantity} unités",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                        color = quantityColor
                    )

                    if (state.selectedProduct!!.quantity < 10) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "⚠️ Stock faible",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFFFA000),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Description
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1B5E20)
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = state.selectedProduct!!.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Buy Button
                Button(
                    onClick = {
                        viewModel.handleIntent(ProductIntent.AddToCart(productId))
                        onBuy()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81C784)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
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
}