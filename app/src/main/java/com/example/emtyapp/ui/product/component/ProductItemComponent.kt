package com.example.emtyapp.ui.product.component
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.emtyapp.data.Entities.Product
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.style.TextDecoration


@Composable
fun ProductItem(
    product: Product,
    onNavigateToDetails: (String) -> Unit,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onNavigateToDetails(product.id) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)) // Ton bambou clair
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = product.imageResId),
                contentDescription = product.name,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF3E2723) // Marron foncé nature
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6D4C41), // Marron clair
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${product.price} MAD", // Current price (green)
                        style = MaterialTheme.typography.titleSmall,
                        color = Color(0xFF2E7D32)
                    )
                    if (product.oldPrice > product.price) { // Only show if discounted
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${product.oldPrice} MAD", // Old price (strikethrough)
                            style = MaterialTheme.typography.bodySmall.copy(
                                textDecoration = TextDecoration.LineThrough
                            ),
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                if (product.quantity > 9) {
                    Text(
                        text = "${product.quantity} en stock",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF2E7D32) // Green
                    )
                } else {
                    Text(
                        text = "Stock Faible ,reste que ${product.quantity} !!",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Red
                    )
                }
                TextButton(
                    onClick = { onNavigateToDetails(product.id) },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Voir détails", color = Color(0xFF2E7D32))
                }
                Button(
                    onClick = { onAddToCart() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81C784))
                ) {
                    Text("Ajouter au panier", color = Color.White)
                }
            }
        }
    }
}
