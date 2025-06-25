package com.example.emtyapp.ui.product.component
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

@Composable
fun ProductItem(
    product: Product,
    onNavigateToDetails: (String) -> Unit,
    onAddToCart: (String) -> Unit,  // Changed to accept productId
    modifier: Modifier,
    isAdmin: Boolean = false, // ✅ Ajouté
    onEditClick: (Product) -> Unit = {} // ✅ Ajouté
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onNavigateToDetails(product.id) },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF8FF)) // Soft neon-blue background
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFCCF1FF),
                            Color(0xFFEFF8FF)
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Image(
                painter = painterResource(id = product.imageResId),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF0D47A1),
                        fontSize = 18.sp
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF37474F),
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${product.price} MAD",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = Color(0xFF00897B),
                            fontWeight = FontWeight.Medium
                        )
                    )
                    if (product.oldPrice > product.price) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${product.oldPrice} MAD",
                            style = MaterialTheme.typography.bodySmall.copy(
                                textDecoration = TextDecoration.LineThrough,
                                color = Color.Gray
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = if (product.quantity > 9)
                        "${product.quantity} en stock"
                    else
                        "Stock faible, reste que ${product.quantity} !!",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (product.quantity > 9) Color(0xFF388E3C) else Color.Red
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { onNavigateToDetails(product.id) }) {
                        Text(
                            text = "Voir détails",
                            color = Color(0xFF0D47A1)
                        )
                    }

                    Button(
                        onClick = { onAddToCart(product.id) },  // Pass the product.id here
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00B8D4))
                    ) {
                        Text("Ajouter", color = Color.White)
                    }
                    // ✅ Bouton visible uniquement pour les admins
                    // ✅ Affichage du bouton "Gérer" uniquement pour l'admin
                    if (isAdmin) {
                        Spacer(modifier = Modifier.height(3.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            OutlinedButton(
                                onClick = { onEditClick(product) },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFD32F2F))
                            ) {
                                Text("✏️ Gérer")
                            }
                        }
                    }

                }
            }
        }
    }
}
