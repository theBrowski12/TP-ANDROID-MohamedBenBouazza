package com.example.emtyapp.ui.product.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.emtyapp.data.Entities.CartItem
import com.example.emtyapp.ui.product.ProductIntent
import com.example.emtyapp.ui.product.ProductViewModel
import com.example.emtyapp.ui.product.openWhatsAppWithMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import com.example.emtyapp.data.Entities.Order
import com.example.emtyapp.ui.auth.AuthViewModel
import com.example.emtyapp.ui.order.OrderViewModel
import java.text.SimpleDateFormat
import java.util.*
import com.example.emtyapp.data.Entities.OrderItem

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(Date())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: ProductViewModel,
    authViewModel: AuthViewModel
) {
    val orderViewModel: OrderViewModel = hiltViewModel()
    val context = LocalContext.current
    val cartItems by viewModel.cartItems.collectAsState()
    val totalPrice by viewModel.cartTotal.collectAsState()
    var address by remember { mutableStateOf("") }
    val whatsappMessage = buildString {
        append("Bonjour, je suis intéressé par les produits :\n")
        cartItems.forEach { item ->
            append("- ${item.product.name} (Quantité: ${item.quantity})\n")
        }
        append("Adresse de livraison: $address\n")
    }
    val currentUser by authViewModel.currentUser.collectAsState()
    val userId = currentUser?.id ?: ""

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mon Panier") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total: ${String.format("%.2f", totalPrice)} MAD",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Button(
                        onClick = {
                            if (userId.isNotEmpty()) {
                                val orderItems = cartItems.map {
                                    OrderItem(
                                        productId = it.product.id,
                                        title = it.product.name,
                                        quantity = it.quantity,
                                        price = it.product.price
                                    )
                                }

                                val order = Order(
                                    userId = userId,
                                    date = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date()),
                                    total = totalPrice,
                                    status = "En attente",
                                    items = orderItems
                                )
                                orderViewModel.createOrder(order) {
                                    // Après création, vider le panier
                                    viewModel.clearCart()
                                    address = "" // Optionnel : vider l'adresse aussi
                                }
                            }

                            openWhatsAppWithMessage(
                                context = context,
                                phoneNumber = "0646564984", // Remplace par ton numéro
                                message = whatsappMessage
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                    ) {
                        Text("Valider la commande")
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Votre panier est vide")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f), // occupe tout l’espace vertical sauf le TextField
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(cartItems, key = { it.product.id }) { cartItem ->
                        CartItemCard(
                            cartItem = cartItem,
                            onIncreaseQuantity = {
                                viewModel.handleIntent(ProductIntent.IncrementQuantity(cartItem.product.id))
                            },
                            onDecreaseQuantity = {
                                viewModel.handleIntent(ProductIntent.DecrementQuantity(cartItem.product.id))
                            },
                            onRemoveItem = {
                                viewModel.handleIntent(ProductIntent.RemoveFromCart(cartItem.product.id))
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Champ texte Adresse
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Adresse de livraison") },
                    placeholder = { Text("Entrez votre adresse ici") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                    maxLines = 3
                )
            }
        }
    }
}

@Composable
fun CartItemCard(
    cartItem: CartItem,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onRemoveItem: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            Image(
                painter = rememberAsyncImagePainter(cartItem.product.imageResURL),
                contentDescription = cartItem.product.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cartItem.product.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${cartItem.product.price} MAD",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF2E7D32)
                )
                Text(
                    text = "Total: ${String.format("%.2f", cartItem.product.price * cartItem.quantity)} MAD",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Quantity Controls
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onDecreaseQuantity,
                    modifier = Modifier.size(24.dp)
                ) {
                    Text("-", style = MaterialTheme.typography.titleLarge)
                }
                Text(
                    text = cartItem.quantity.toString(),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(
                    onClick = onIncreaseQuantity,
                    modifier = Modifier.size(24.dp)
                ) {
                    Text("+", style = MaterialTheme.typography.titleLarge)
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Remove button
            IconButton(onClick = onRemoveItem) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Remove",
                    tint = Color.Red
                )
            }
        }
    }
}
