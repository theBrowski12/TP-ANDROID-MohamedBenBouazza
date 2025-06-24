package com.example.emtyapp.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emtyapp.data.Entities.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToCart: () -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("Guest") }
    var expanded by remember { mutableStateOf(false) }
    val roleOptions = listOf("Guest", "Member", "Admin")
    var showSuccessDialog by remember { mutableStateOf(false) }
    val errorMessage by authViewModel.errorMessage.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()

    // Pour indiquer que mot de passe et confirmation sont différents
    val passwordsMatch = password == confirmPassword || confirmPassword.isEmpty()

    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            onRegisterSuccess()
            showSuccessDialog = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inscription", color = Color(0xFF00D4FF)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateToLogin) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour", tint = Color(0xFF00D4FF))
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
                        Icon(Icons.Default.Home, contentDescription = "Accueil", tint = Color(0xFF00D4FF))
                    }
                    IconButton(onClick = onNavigateToLogin) {
                        Icon(Icons.Default.Person, contentDescription = "Login", tint = Color(0xFF00D4FF))
                    }
                    IconButton(onClick = onNavigateToCart) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Panier", tint = Color(0xFF00D4FF))
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
                .background(Color(0xFF0A0A12)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Créer un compte", style = MaterialTheme.typography.headlineLarge, color = Color(0xFF00D4FF))

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it
                    authViewModel.clearError()},
                label = { Text("Nom complet") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    authViewModel.clearError()
                }
                ,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it
                    authViewModel.clearError()
                },
                label = { Text("Mot de passe") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = PasswordVisualTransformation(),
                colors = fieldColors()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it
                    authViewModel.clearError()
                },
                label = { Text("Confirmer mot de passe") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = PasswordVisualTransformation(),
                colors = fieldColors(),
                isError = !passwordsMatch
            )

            if (!passwordsMatch) {
                Text(
                    text = "Les mots de passe ne correspondent pas",
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = role,
                    onValueChange = {},
                    label = { Text("Rôle") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    colors = fieldColors()
                )

                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    roleOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                role = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Affiche erreur si email déjà utilisé (ou autre erreur renvoyée par le ViewModel)
            errorMessage?.let {
                Text(it, color = Color.Red, modifier = Modifier.padding(8.dp))
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    if (validateInputs(name, email, password, confirmPassword)) {
                        authViewModel.register(
                            User(name = name, email = email, password = password, role = role)
                        )
                    }
                },
                enabled = passwordsMatch,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00B8D4)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("S'inscrire", fontSize = 16.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text("Déjà un compte ?", color = Color.LightGray)
                TextButton(onClick = onNavigateToLogin) {
                    Text("Connexion", color = Color(0xFF00D4FF))
                }
            }
        }
    }
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { /* Rien */ },
            confirmButton = {
                TextButton(onClick = {
                    showSuccessDialog = false
                    onRegisterSuccess() // redirection vers Home ou autre
                }) {
                    Text("OK", color = Color(0xFF00D4FF))
                }
            },
            title = { Text("Succès", color = Color.White) },
            text = { Text("Inscription réussie !", color = Color.LightGray) },
            containerColor = Color(0xFF1E1E2C),
            shape = RoundedCornerShape(12.dp)
        )
    }

}

@Composable
private fun fieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Color(0xFF00D4FF),
    unfocusedBorderColor = Color(0xFFA0A0CC),
    focusedLabelColor = Color(0xFF00D4FF),
    unfocusedLabelColor = Color(0xFFA0A0CC),
    cursorColor = Color(0xFF00D4FF)
)

private fun validateInputs(
    name: String,
    email: String,
    password: String,
    confirmPassword: String
): Boolean {
    return name.isNotBlank()
            && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            && password.length >= 6
            && password == confirmPassword
}
