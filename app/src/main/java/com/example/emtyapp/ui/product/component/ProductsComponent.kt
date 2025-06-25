package com.example.emtyapp.ui.product.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.emtyapp.R
import com.example.emtyapp.data.Entities.Product

@Composable
fun ProductsList(
    products: List<Product>,
    onNavigateToDetails: (String) -> Unit,
    onAddToCart: (String) -> Unit,
    modifier: Modifier = Modifier,
    selectedCategory: String = "Tous",
    currentUserRole: String = "", // Ajouté
    onEditClick: (Product) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A12))
    ) {
        item {
            // Futuristic header with holographic effect
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF00D4FF).copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        )
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                // Dynamic title based on selected category
                Text(
                    text = if (selectedCategory == "Tous") "NOS PRODUITS"
                    else selectedCategory.uppercase(),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color(0xFF00D4FF),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp,
                        letterSpacing = 2.sp
                    ),
                    modifier = Modifier
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(8.dp),
                            spotColor = Color.Cyan
                        )
                )

                // Digital pulse effect at bottom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0xFF00D4FF),
                                    Color.Transparent
                                )
                            )
                        )
                )
            }

            // Grid-like divider
            Divider(
                color = Color(0xFF22223A),
                thickness = 1.dp,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .shadow(2.dp, shape = RoundedCornerShape(50))
            )
        }

        items(products) { product ->
            ProductItem(
                product = product,
                onNavigateToDetails = { onNavigateToDetails(product.id) },
                onAddToCart = { onAddToCart(product.id) },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                isAdmin = currentUserRole.lowercase() == "admin", // ✅ admin seulement
                onEditClick = { selectedProduct ->
                    onEditClick(selectedProduct) // ✅ déclenche le callback
                }
            )
        }

    }
}