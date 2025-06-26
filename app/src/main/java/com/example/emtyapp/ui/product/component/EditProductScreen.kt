package com.example.emtyapp.ui.product.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.emtyapp.data.Entities.Product
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import com.example.emtyapp.ui.product.ProductViewModel

import androidx.compose.material.icons.filled.ArrowBack


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(
    product: Product,
    viewModel: ProductViewModel,
    onBack: () -> Unit,
) {
    var name by remember { mutableStateOf(product.name) }
    var description by remember { mutableStateOf(product.description ?: "") }
    var price by remember { mutableStateOf(product.price.toString()) }
    var oldPrice by remember { mutableStateOf(product.oldPrice.toString()) }
    var quantity by remember { mutableStateOf(product.quantity.toString()) }
    var category by remember { mutableStateOf(product.category) }

    val context = LocalContext.current
    val isFormValid = name.isNotBlank() && price.toDoubleOrNull() != null && quantity.toIntOrNull() != null

    val categories = listOf(
        "Électroniques", "Véhicules", "Digital", "Montres", "Joaillerie", "Homme", "Femme", "Upcoming"
    )

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Modifier le produit", color = Color(0xFF00D4FF))
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Retour",
                            tint = Color(0xFF00D4FF)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF001F3F)
                )
            )
        },
        containerColor = Color(0xFF1E3D52)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Champ Nom
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nom", color = Color(0xFF00D4FF)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description", color = Color(0xFF00D4FF)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Prix
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Prix", color = Color(0xFF00D4FF)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Ancien prix
            OutlinedTextField(
                value = oldPrice,
                onValueChange = { oldPrice = it },
                label = { Text("Ancien Prix", color = Color(0xFF00D4FF)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Quantité
            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantité", color = Color(0xFF00D4FF)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Catégorie (Dropdown)
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = category,
                    onValueChange = {},
                    label = { Text("Catégorie", color = Color(0xFF00D4FF)) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    singleLine = true
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat) },
                            onClick = {
                                category = cat
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Enregistrer
            Button(
                onClick = {
                    if (isFormValid) {
                        val updatedProduct = product.copy(
                            name = name,
                            description = description,
                            price = price.toDouble(),
                            oldPrice = oldPrice.toDoubleOrNull() ?: price.toDouble(),
                            quantity = quantity.toInt(),
                            category = category
                        )
                        viewModel.updateProduct(updatedProduct) { success ->
                            if (success) {
                                Toast.makeText(context, "Produit mis à jour", Toast.LENGTH_SHORT).show()
                                onBack()
                            } else {
                                Toast.makeText(context, "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "Veuillez remplir correctement le formulaire", Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00D4FF))
            ) {
                Text("Enregistrer", color = Color.Black)
            }


            Spacer(modifier = Modifier.height(8.dp))

            // Annuler
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF00D4FF))
            ) {
                Text("Annuler")
            }
        }
    }
}
