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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onHomeClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onCartClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val errorMessage by authViewModel.errorMessage.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()

    LaunchedEffect(currentUser) {
        currentUser?.let {
            delay(100) // small delay to allow state propagation
            onLoginSuccess()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Welcome Back",
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
            BottomAppBar(
                containerColor = Color(0xFF001F2D)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(onClick = onHomeClick) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            tint = Color(0xFF00D4FF)
                        )
                    }
                    IconButton(onClick = onProfileClick) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = Color(0xFF00D4FF)
                        )
                    }
                    IconButton(onClick = onCartClick) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            tint = Color(0xFF00D4FF)
                        )
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
                text = "Login",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00D4FF)
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color(0xFF00D4FF)) },
                singleLine = true,
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
                    // container colors are **not** supported by OutlinedTextFieldDefaults.colors, so omit containerColor here.
                )
                ,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color(0xFF00D4FF)) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
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
                    // container colors are **not** supported by OutlinedTextFieldDefaults.colors, so omit containerColor here.
                )
                ,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            errorMessage?.let { message ->
                Text(
                    text = message,
                    color = Color(0xFFFF5252),
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotBlank()) {
                        authViewModel.login(email, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00D4FF))
            ) {
                Text("Login", color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onNavigateToRegister) {
                Text("Don't have an account? Register", color = Color(0xFF00D4FF))
            }
        }
    }
}
