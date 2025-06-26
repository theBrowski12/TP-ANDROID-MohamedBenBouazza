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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.emtyapp.data.Entities.Product
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch

@Composable
fun ProductItem(
    product: Product,
    onNavigateToDetails: (String) -> Unit,
    onAddToCart: (String) -> Unit,
    modifier: Modifier,
    isAdmin: Boolean = false,
    onEditClick: (Product) -> Unit = {},
    onDeleteClick: (Product) -> Unit = {}

) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
// Etat local pour afficher la confirmation
    var showDeleteDialog by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onNavigateToDetails(product.id) },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF8FF))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                if (!product.imageResURL.isNullOrEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(model = product.imageResURL),
                        contentDescription = product.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = product.name ?: "Nom inconnu",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF0D47A1),
                            fontSize = 18.sp
                        )
                    )

                    Text(
                        text = product.description ?: "Pas de description",
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
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Partie gauche : bouton gérer (admin seulement)
                    if (isAdmin) {
                        Row {
                            TextButton(onClick = { onEditClick(product) }) {
                                Text("✏️ Gérer", color = Color.Red)
                            }

                            Spacer(modifier = Modifier.width(4.dp))

                            TextButton(onClick = { showDeleteDialog = true }) {
                                Text("🗑️", color = Color(0xFFD32F2F))
                            }

                        }
                    } else {
                        Spacer(modifier = Modifier.width(1.dp))
                    }


                    // Partie droite : voir détails + bouton panier
                    Row {
                        TextButton(onClick = { onNavigateToDetails(product.id) }) {
                            Text("Voir détails", color = Color(0xFF0D47A1))
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                onAddToCart(product.id)
                                scope.launch {
                                    snackbarHostState.showSnackbar("Produit ajouté au panier !")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00B8D4)),
                            modifier = Modifier.height(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Ajouter au panier",
                                tint = Color.White
                            )
                        }
                    }
                }
            }

                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirmation de suppression") },
            text = { Text("Êtes-vous sûr de supprimer ce produit définitivement ?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDeleteClick(product)
                    }
                ) {
                    Text("Confirmer")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("Annuler")
                }
            }
        )
    }
}


