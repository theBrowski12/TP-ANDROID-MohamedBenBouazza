package com.example.emtyapp.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.emtyapp.data.sampleProducts



@Composable
fun DetailsScreen(productId: String, onBack: () -> Unit, onBuy: () -> Unit) {
    val product = sampleProducts.find { it.id == productId }

    if (product != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Button(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Retour")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Retour")
                }

            }

            Spacer(modifier = Modifier.height(20.dp))


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Image(
                    painter = painterResource(id = product.imageResId),
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))


            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Prix : ${product.price} Dh",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1E88E5)
            )

            Text(
                text = "Ancien prix : ${product.oldPrice} Dh",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray ,
                textDecoration = TextDecoration.LineThrough
            )
            val quantityColor = if (product.quantity < 10) Color.Red else Color(0xFF1E88E5) // Blue

            Text(
                text = "Quantité disponible : ${product.quantity}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = quantityColor
            )

            if (product.quantity < 10) {
                Text(
                    text = "⚠️ Stock faible",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFFF9800), // Orange
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(18.dp))

            Button(onClick = onBuy) {

                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Retour")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Acheter")
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Produit non trouvé")
        }
    }
}