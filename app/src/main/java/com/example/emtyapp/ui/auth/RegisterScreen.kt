package com.example.emtyapp.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
    val passwordsMatch = password == confirmPassword || confirmPassword.isEmpty()

    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            onRegisterSuccess()
            showSuccessDialog = true
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Register",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF00D4FF)
                        )
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF001F2D)
                )
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = Color(0xFF001F2D)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(onClick = onNavigateToHome) {
                        Icon(Icons.Default.Home, contentDescription = "Home", tint = Color(0xFF00D4FF))
                    }
                    IconButton(onClick = onNavigateToLogin) {
                        Icon(Icons.Default.Person, contentDescription = "Login", tint = Color(0xFF00D4FF))
                    }
                    IconButton(onClick = onNavigateToCart) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = Color(0xFF00D4FF))
                    }
                }
            }
        },
        containerColor = Color(0xFF001F2D)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF001F2D), Color(0xFF004B75))
                    )
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Créer un compte",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00D4FF)
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    authViewModel.clearError()
                },
                label = { Text("Nom complet", color = Color(0xFF00D4FF)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    authViewModel.clearError()
                },
                label = { Text("Email", color = Color(0xFF00D4FF)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    authViewModel.clearError()
                },
                label = { Text("Mot de passe", color = Color(0xFF00D4FF)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    authViewModel.clearError()
                },
                label = { Text("Confirmer mot de passe", color = Color(0xFF00D4FF)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(12.dp),
                isError = !passwordsMatch,
                colors = fieldColors()
            )

            if (!passwordsMatch) {
                Text(
                    text = "Les mots de passe ne correspondent pas",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 4.dp)
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
                    label = { Text("Rôle", color = Color(0xFF00D4FF)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
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

            Spacer(modifier = Modifier.height(20.dp))

            errorMessage?.let {
                Text(it, color = Color.Red, modifier = Modifier.padding(bottom = 8.dp))
            }

            Button(
                onClick = {
                    if (validateInputs(name, email, password, confirmPassword)) {
                        authViewModel.register(User(name, email, password, role))
                    }
                },
                enabled = passwordsMatch,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00D4FF))
            ) {
                Text("S'inscrire", color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onNavigateToLogin) {
                Text("Déjà un compte ? Connexion", color = Color(0xFF00D4FF))
            }
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = {
                    showSuccessDialog = false
                    onRegisterSuccess()
                }) {
                    Text("OK", color = Color(0xFF00D4FF))
                }
            },
            title = { Text("Succès", color = Color.White) },
            text = { Text("Inscription réussie !", color = Color(0xFFDDDDDD)) },
            containerColor = Color(0xFF2A2A3A),
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
private fun fieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White.copy(alpha = 0.7f),
    cursorColor = Color(0xFF00D4FF),
    focusedBorderColor = Color(0xFF00D4FF),
    unfocusedBorderColor = Color(0xFF006680),
    focusedLabelColor = Color(0xFF00D4FF),
    unfocusedLabelColor = Color(0xFF00D4FF).copy(alpha = 0.7f),
    errorBorderColor = Color.Red,
    errorLabelColor = Color.Red
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
