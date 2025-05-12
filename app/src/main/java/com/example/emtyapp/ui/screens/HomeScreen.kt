package com.example.emtyapp.ui.screens

import android.text.style.BackgroundColorSpan
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.emtyapp.data.sampleProducts
import com.example.emtyapp.ui.components.ProductCard

@Composable
fun HomeScreen(onNavigateToDetails: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = Color(0xFF02284F),
            shape = RoundedCornerShape(5.dp), // Rounded corners
            tonalElevation = 5.dp
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Bambou Commerce",
                    style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
                )
            }
        }
        LazyColumn {
            items(sampleProducts) { product ->
                ProductCard(product = product) {
                    onNavigateToDetails(product.id)
                }
            }
        }
    }
}
