package com.example.emtyapp.ui.product.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.emtyapp.data.Entities.Product
import com.example.emtyapp.ui.product.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewProductScreen(
    viewModel: ProductViewModel,
    onBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToCart: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val categories = listOf(
        "Électroniques", "Véhicules", "Digital", "Montres",
        "Joaillerie", "Homme", "Femme", "Upcoming"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajouter un produit", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.Close, contentDescription = "Retour", tint = Color(0xFF00D4FF))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2A2A3A)
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
                    IconButton(onClick = onNavigateToHome) {
                        Icon(Icons.Default.Home, contentDescription = "Accueil", tint = Color(0xFF00D4FF))
                    }
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.Person, contentDescription = "Profil", tint = Color(0xFF00D4FF))
                    }
                    IconButton(onClick = onNavigateToCart) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Panier", tint = Color(0xFF00D4FF))
                    }
                }
            }
        },
        containerColor = Color(0xFF1E3D52)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .background(Color(0xFF1E3D52)),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Nom du produit") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White.copy(alpha = 0.7f),
                    cursorColor = Color(0xFF00D4FF),
                    focusedBorderColor = Color(0xFF00D4FF),
                    unfocusedBorderColor = Color(0xFF006680),
                    focusedLabelColor = Color(0xFF00D4FF),
                    unfocusedLabelColor = Color(0xFF00D4FF).copy(alpha = 0.7f),
                    errorBorderColor = Color.Red,
                    errorLabelColor = Color.Red,
                    focusedLeadingIconColor = Color(0xFF00D4FF),
                    unfocusedLeadingIconColor = Color(0xFF00D4FF).copy(alpha = 0.7f),
                    focusedTrailingIconColor = Color(0xFF00D4FF),
                    unfocusedTrailingIconColor = Color(0xFF00D4FF).copy(alpha = 0.7f),
                )
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White.copy(alpha = 0.7f),
                    cursorColor = Color(0xFF00D4FF),
                    focusedBorderColor = Color(0xFF00D4FF),
                    unfocusedBorderColor = Color(0xFF006680),
                    focusedLabelColor = Color(0xFF00D4FF),
                    unfocusedLabelColor = Color(0xFF00D4FF).copy(alpha = 0.7f),
                    errorBorderColor = Color.Red,
                    errorLabelColor = Color.Red,
                    focusedLeadingIconColor = Color(0xFF00D4FF),
                    unfocusedLeadingIconColor = Color(0xFF00D4FF).copy(alpha = 0.7f),
                    focusedTrailingIconColor = Color(0xFF00D4FF),
                    unfocusedTrailingIconColor = Color(0xFF00D4FF).copy(alpha = 0.7f),
                )
            )

            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Prix") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White.copy(alpha = 0.7f),
                    cursorColor = Color(0xFF00D4FF),
                    focusedBorderColor = Color(0xFF00D4FF),
                    unfocusedBorderColor = Color(0xFF006680),
                    focusedLabelColor = Color(0xFF00D4FF),
                    unfocusedLabelColor = Color(0xFF00D4FF).copy(alpha = 0.7f),
                    errorBorderColor = Color.Red,
                    errorLabelColor = Color.Red,
                    focusedLeadingIconColor = Color(0xFF00D4FF),
                    unfocusedLeadingIconColor = Color(0xFF00D4FF).copy(alpha = 0.7f),
                    focusedTrailingIconColor = Color(0xFF00D4FF),
                    unfocusedTrailingIconColor = Color(0xFF00D4FF).copy(alpha = 0.7f),
                )
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Catégorie") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White.copy(alpha = 0.7f),
                        cursorColor = Color(0xFF00D4FF),
                        focusedBorderColor = Color(0xFF00D4FF),
                        unfocusedBorderColor = Color(0xFF006680),
                        focusedLabelColor = Color(0xFF00D4FF),
                        unfocusedLabelColor = Color(0xFF00D4FF).copy(alpha = 0.7f),
                        errorBorderColor = Color.Red,
                        errorLabelColor = Color.Red,
                        focusedLeadingIconColor = Color(0xFF00D4FF),
                        unfocusedLeadingIconColor = Color(0xFF00D4FF).copy(alpha = 0.7f),
                        focusedTrailingIconColor = Color(0xFF00D4FF),
                        unfocusedTrailingIconColor = Color(0xFF00D4FF).copy(alpha = 0.7f),
                    ),
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color(0xFF2A2A3A))
                ) {
                    categories.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option, color = Color.White) },
                            onClick = {
                                category = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantité") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White.copy(alpha = 0.7f),
                    cursorColor = Color(0xFF00D4FF),
                    focusedBorderColor = Color(0xFF00D4FF),
                    unfocusedBorderColor = Color(0xFF006680),
                    focusedLabelColor = Color(0xFF00D4FF),
                    unfocusedLabelColor = Color(0xFF00D4FF).copy(alpha = 0.7f),
                    errorBorderColor = Color.Red,
                    errorLabelColor = Color.Red,
                    focusedLeadingIconColor = Color(0xFF00D4FF),
                    unfocusedLeadingIconColor = Color(0xFF00D4FF).copy(alpha = 0.7f),
                    focusedTrailingIconColor = Color(0xFF00D4FF),
                    unfocusedTrailingIconColor = Color(0xFF00D4FF).copy(alpha = 0.7f),
                )
            )

            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("Image URL") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White.copy(alpha = 0.7f),
                    cursorColor = Color(0xFF00D4FF),
                    focusedBorderColor = Color(0xFF00D4FF),
                    unfocusedBorderColor = Color(0xFF006680),
                    focusedLabelColor = Color(0xFF00D4FF),
                    unfocusedLabelColor = Color(0xFF00D4FF).copy(alpha = 0.7f),
                    errorBorderColor = Color.Red,
                    errorLabelColor = Color.Red,
                    focusedLeadingIconColor = Color(0xFF00D4FF),
                    unfocusedLeadingIconColor = Color(0xFF00D4FF).copy(alpha = 0.7f),
                    focusedTrailingIconColor = Color(0xFF00D4FF),
                    unfocusedTrailingIconColor = Color(0xFF00D4FF).copy(alpha = 0.7f),
                )
            )

            Button(
                onClick = {
                    val product = Product(
                        id = "PR" + System.currentTimeMillis(),
                        name = title,
                        description = description,
                        price = price.toDoubleOrNull() ?: 0.0,
                        oldPrice = 0.0,
                        quantity = quantity.toIntOrNull() ?: 0,
                        imageResURL = imageUrl,
                        category = category
                    )
                    viewModel.addNewProduct(product) {
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00D4FF))
            ) {
                Text("Ajouter", color = Color.Black)
            }
        }
    }
}
