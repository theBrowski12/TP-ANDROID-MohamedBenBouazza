package com.example.emtyapp.ui.product.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import coil.compose.rememberAsyncImagePainter
import com.example.emtyapp.ui.auth.AuthViewModel
import com.example.emtyapp.ui.product.ProductIntent
import com.example.emtyapp.ui.product.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    productId: String,
    authViewModel: AuthViewModel,
    viewModel: ProductViewModel = hiltViewModel(),
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    onBackClick: () -> Unit = {}  // Retour button callback
) {
    val state by viewModel.state.collectAsState()
    val cartItemCount by remember { derivedStateOf { viewModel.getCartItemCount() } }
    val currentUser by authViewModel.currentUser.collectAsState()

    LaunchedEffect(productId) {
        viewModel.loadProductById(productId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Retour",
                            tint = Color(0xFF0288D1)
                        )
                    }
                },
                title = {
                    Text(
                        text = "Détails",
                        color = Color(0xFF0288D1),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                actions = {
                    if (currentUser != null) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(
                                text = "Welcome, ${currentUser!!.name ?: currentUser!!.email.take(10)}",
                                color = Color(0xFF0288D1),
                                maxLines = 1,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            TextButton(onClick = { authViewModel.logout() }) {
                                Text("Logout", color = Color(0xFF00ACC1))
                            }
                        }
                    } else {
                        TextButton(onClick = onLoginClick) {
                            Text("Login", color = Color(0xFF0288D1))
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        TextButton(onClick = onRegisterClick) {
                            Text("Register", color = Color(0xFF00ACC1))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.background(Color.White.copy(alpha = 0.95f))
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFFF0FBFF)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(onClick = onHomeClick) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Home",
                            tint = Color(0xFF0288D1)
                        )
                    }

                    IconButton(onClick = {
                        if (currentUser != null) {
                            onProfileClick()
                        } else {
                            onLoginClick()
                        }
                    }) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = Color(0xFF0288D1)
                        )
                    }

                    IconButton(onClick = onCartClick) {
                        BadgedBox(
                            badge = {
                                if (cartItemCount > 0) {
                                    Badge {
                                        Text(
                                            cartItemCount.toString(),
                                            color = Color.Black
                                        )
                                    }
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = "Cart",
                                tint = Color(0xFF0288D1)
                            )
                        }
                    }
                }
            }
        },
        containerColor = Color(0xFFF0FBFF)
    ) { innerPadding ->

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF00BCD4))
                }
            }

            state.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.error ?: "Erreur de chargement",
                        color = Color(0xFFE53935),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            state.selectedProduct == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Produit introuvable", color = Color.Gray)
                }
            }

            else -> {
                val product = state.selectedProduct!!

                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                painter = rememberAsyncImagePainter(product.imageResURL),
                                contentDescription = product.name,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF01579B)
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Catégorie : ${product.category}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF5D4037)
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${product.price} MAD",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00C853)
                        )
                    )

                    if (product.oldPrice > product.price) {
                        Text(
                            text = "${product.oldPrice} MAD",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val quantityColor = if (product.quantity < 10) Color.Red else Color(0xFF00C853)
                        Text(
                            text = "Disponible : ${product.quantity} unités",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            color = quantityColor
                        )
                        if (product.quantity < 10) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "⚠️ Stock faible",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFFFA000),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0288D1)
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = product.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF455A64)
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = {
                            viewModel.handleIntent(ProductIntent.AddToCart(productId))
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(14.dp)
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
}
