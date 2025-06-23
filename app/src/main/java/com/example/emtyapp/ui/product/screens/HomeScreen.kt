package com.example.emtyapp.ui.product.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.emtyapp.R
import com.example.emtyapp.ui.product.ProductIntent
import com.example.emtyapp.ui.product.ProductViewModel
import com.example.emtyapp.ui.product.component.ProductsList
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChipDefaults
import com.example.emtyapp.ui.auth.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    authViewModel: AuthViewModel,
    viewModel: ProductViewModel,
    onNavigateToDetails: (String) -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val cartItemCount by remember { derivedStateOf { viewModel.getCartItemCount() } }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    val currentUser by authViewModel.currentUser.collectAsState()
    val categories = listOf(
        "Tous",
        "Électroniques",
        "Véhicules",
        "Digital",
        "Montres",
        "Joaillerie",
        "Homme",
        "Femme",
        "Upcoming"
    )
    var selectedCategory by remember { mutableStateOf("Tous") }

    LaunchedEffect(viewModel) {
        viewModel.handleIntent(ProductIntent.LoadProducts)
    }

    LaunchedEffect(searchQuery) {
        if (searchQuery.length > 2) {
            viewModel.handleIntent(ProductIntent.SearchProducts(searchQuery))
        } else if (searchQuery.isEmpty()) {
            viewModel.handleIntent(ProductIntent.LoadProducts)
        }
    }

    LaunchedEffect(selectedCategory) {
        viewModel.handleIntent(
            if (selectedCategory == "Tous") ProductIntent.LoadProducts
            else ProductIntent.FilterByCategory(selectedCategory)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearchActive) {
                        SearchBarDefaults.inputFieldColors(
                            //textColor = Color.White,
                            //placeholderColor = Color(0xFFA0A0CC)
                        )
                        SearchBar(
                            query = searchQuery,
                            onQueryChange = { newQuery -> searchQuery = newQuery },
                            onSearch = { query ->
                                viewModel.handleIntent(ProductIntent.SearchProducts(query))
                                isSearchActive = false
                            },
                            active = isSearchActive,
                            onActiveChange = { isSearchActive = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Rechercher...") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = Color(0xFF00D4FF)
                                )
                            },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(
                                        onClick = { searchQuery = "" }
                                    ) {
                                        Icon(
                                            Icons.Default.Close,
                                            contentDescription = "Clear",
                                            tint = Color(0xFF00D4FF)
                                        )
                                    }
                                }
                            },
                            colors = SearchBarDefaults.colors(
                                containerColor = Color(0xFF2A2A3A),
                                dividerColor = Color(0x566D6D91)
                            )
                        ) {
                            // Empty content for the dropdown
                        }
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "App Logo",
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(end = 8.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(
                                onClick = { isSearchActive = true },
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = Color(0xFF00D4FF)
                                )
                            }
                        }
                    }
                },
                actions = {
                    if (currentUser != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Welcome ${currentUser?.name?.take(10) ?: currentUser?.email?.take(10)}",
                                color = Color(0xFF00D4FF),
                                maxLines = 1,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            TextButton(onClick = { authViewModel.logout() }) {
                                Text("Logout", color = Color(0xFF00ACC1))
                            }
                        }
                    } else {
                        Row {
                            TextButton(onClick = onNavigateToLogin) {
                                Text("Login", color = Color(0xFF00D4FF))
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            TextButton(onClick = onNavigateToRegister) {
                                Text("Register", color = Color(0xFF00ACC1))
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2A2A3A),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF00135E)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(onClick = { /* Home action */ }) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Home",
                            tint = Color(0xFF00D4FF)
                        )
                    }
                    IconButton(onClick = { isSearchActive = true }) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color(0xFF00D4FF)
                        )
                    }
                    IconButton(onClick = {
                        if (authViewModel.currentUser.value != null) {
                            onNavigateToProfile()
                        } else {
                            onNavigateToLogin()
                        }
                    }) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = Color(0xFF00D4FF)
                        )
                    }
                    IconButton(onClick = onNavigateToCart) {
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
                                tint = Color(0xFF00D4FF)
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color(0xFF1E3D52))
                .fillMaxSize()
        ) {
            // Categories Section with LazyRow
            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(categories) { category ->
                    CategoryChip(
                        name = category,
                        isSelected = category == selectedCategory,
                        onClick = { selectedCategory = category }
                    )
                }
            }

            // Products Section
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                when {
                    state.isLoading -> CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color(0xFF00D4FF)
                    )
                    state.error != null -> Text(
                        "Error: ${state.error}",
                        color = Color(0xFFFF5555),
                        modifier = Modifier.align(Alignment.Center)
                    )
                    else -> Column {
                        if (!isSearchActive) {

                        }
                        ProductsList(
                            products = if (selectedCategory == "Tous") state.products
                            else state.products.filter { it.category == selectedCategory },
                            onNavigateToDetails = onNavigateToDetails,
                            onAddToCart = { productId ->
                                viewModel.handleIntent(ProductIntent.AddToCart(productId))
                                // Optional: Show snackbar or feedback
                            },
                            selectedCategory = selectedCategory
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryChip(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        enabled = true,  // Added enabled parameter
        label = {
            Text(
                text = name,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        },
        leadingIcon = if (isSelected) {
            @Composable {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Color(0xFF00D4FF),
            selectedLabelColor = Color.Black,
            containerColor = Color(0xFF2A2A3A),
            labelColor = Color.White,
            disabledContainerColor = Color.LightGray,
            disabledLabelColor = Color.DarkGray
        ),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = if (isSelected) Color(0xFF00D4FF) else Color.Transparent,
            disabledBorderColor = Color.Gray,
            selectedBorderColor = Color(0xFF00D4FF),
            borderWidth = 1.dp,
            selected = true,
            enabled = true
        ),
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.padding(4.dp)
    )
}