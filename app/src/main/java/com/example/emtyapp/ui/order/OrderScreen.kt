package com.example.emtyapp.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.emtyapp.data.Entities.Order
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.emtyapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    isAdmin: Boolean,
    userId: String,
    viewModel: OrderViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToCart: () -> Unit = {}
) {
    val orders: List<Order> by viewModel.orders.collectAsState()
    var expandedOrderId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(userId) {
        viewModel.loadUserOrders(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(36.dp)
                                .padding(end = 8.dp)
                        )
                        Text("Mes Commandes", color = Color.White)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Retour",
                            tint = Color(0xFF00D4FF)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2A2A3A))
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = Color(0xFF00135E)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(onClick = onNavigateToHome) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Accueil",
                            tint = Color(0xFF00D4FF)
                        )
                    }
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profil",
                            tint = Color(0xFF00D4FF)
                        )
                    }
                    IconButton(onClick = onNavigateToCart) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Panier",
                            tint = Color(0xFF00D4FF)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFF1E3D52))
                .padding(16.dp)
        ) {
            items(
                items = orders,
                key = { it.id ?: "" }
            ) { order ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF001F2D))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Commande N°: ${order.id}", color = Color.White)
                        Text("Date: ${order.date}", color = Color.White)
                        Text("Total: ${order.total} DH", color = Color.White)

                        if (isAdmin) {
                            var expanded by remember { mutableStateOf(false) }
                            var selectedStatus by remember { mutableStateOf(order.status) }
                            val options = listOf("En cours", "En attente", "Livrée", "Annulée")

                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = { expanded = !expanded }
                            ) {
                                OutlinedTextField(
                                    value = selectedStatus,
                                    onValueChange = {},
                                    label = { Text("Statut", color = Color.White) },
                                    readOnly = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                                    },
                                    modifier = Modifier.menuAnchor()
                                )

                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    options.forEach { status ->
                                        DropdownMenuItem(
                                            text = { Text(status) },
                                            onClick = {
                                                selectedStatus = status
                                                expanded = false
                                                order.id?.let { id ->
                                                    viewModel.updateOrderStatus(id, status)
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        } else {
                            Text("Statut: ${order.status}", color = Color.White)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                expandedOrderId =
                                    if (expandedOrderId == order.id) null else order.id
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00D4FF))
                        ) {
                            Text(
                                if (expandedOrderId == order.id) "Masquer les détails" else "Voir détail",
                                color = Color.Black
                            )
                        }

                        if (expandedOrderId == order.id) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Détails de la commande :", color = Color.White)
                            order.items.forEach { item ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(item.title, color = Color.LightGray)
                                        Text("x${item.quantity}", color = Color.Gray)
                                    }
                                    Text("${item.price * item.quantity} DH", color = Color.White)
                                }
                            }
                        }
                        Button(
                            onClick = { viewModel.deleteOrder(order.id ?: "") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text("Supprimer", color = Color.White)
                        }

                    }
                }
            }
        }
    }

}
