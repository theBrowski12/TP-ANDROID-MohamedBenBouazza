package com.example.emtyapp.ui.product.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.emtyapp.ui.product.component.ProductsList
import com.example.emtyapp.ui.product.ProductIntent
import com.example.emtyapp.ui.product.ProductViewModel
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center

@Composable
fun HomeScreen(
    viewModel: ProductViewModel = viewModel(),
    onNavigateToDetails: (String) -> Unit,
    onNavigateToCart: () -> Unit  // Added cart navigation callback
) {
    val state by viewModel.state.collectAsState()
    val cartItemCount by remember { derivedStateOf { viewModel.getCartItemCount() } }

    LaunchedEffect(viewModel) {
        viewModel.handleIntent(ProductIntent.LoadProducts)
    }

    Scaffold(
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(onClick = { /* Handle home */ }) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    }
                    IconButton(onClick = { /* Handle search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { /* Handle profile */ }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                    IconButton(onClick = onNavigateToCart) {
                        BadgedBox(
                            badge = {
                                if (cartItemCount > 0) {
                                    Badge { Text(cartItemCount.toString()) }
                                }
                            }
                        ) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when {
                state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Center))
                state.error != null -> Text("Error: ${state.error}", color = Color.Red)
                else -> ProductsList(
                    products = state.products,
                    onNavigateToDetails = onNavigateToDetails,
                    onAddToCart = { productId ->
                        viewModel.handleIntent(ProductIntent.AddToCart(productId))
                    }
                )
            }
        }
    }
}