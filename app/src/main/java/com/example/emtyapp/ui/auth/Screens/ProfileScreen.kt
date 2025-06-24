package com.example.emtyapp.ui.auth.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emtyapp.ui.auth.AuthViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToUserList: () -> Unit,
    authViewModel: AuthViewModel
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val roleOptions = listOf("Guest", "Member", "Admin")
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var showSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(currentUser) {
        name = currentUser?.name ?: ""
        email = currentUser?.email ?: ""
        role = currentUser?.role ?: ""
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },

        topBar = {
            TopAppBar(
                title = {
                    Text("Retour", color = Color(0xFF00D4FF))
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour", tint = Color(0xFF00D4FF))
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
                    IconButton(onClick = onNavigateToHome) {
                        Icon(Icons.Default.Home, contentDescription = "Accueil", tint = Color(0xFF00D4FF))
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Person, contentDescription = "Profil", tint = Color(0xFF00D4FF))
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
                .background(Color(0xFF0A0A12))
                .padding(16.dp)
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(Color(0xFF00D4FF).copy(alpha = 0.4f), Color.Transparent)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "MON PROFIL",
                    fontSize = 26.sp,
                    color = Color(0xFF00D4FF),
                    letterSpacing = 2.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (currentUser != null) {
                // Champs éditables
                ProfileEditableField(label = "Nom", value = name, onValueChange = { name = it })
                ProfileEditableField(label = "Email", value = email, onValueChange = { email = it })
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = role,
                        onValueChange = {},
                        label = { Text("Rôle") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF00D4FF),
                            unfocusedBorderColor = Color.White,
                            cursorColor = Color(0xFF00D4FF)
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        roleOptions.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    role = selectionOption
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        authViewModel.updateProfile(name, email, role)
                        showSuccess = true
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Sauvegardé avec succès")
                            delay(1500)  // Attente avant redirection
                            onNavigateToHome() // Redirection vers Home
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00B8D4)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Sauvegarder", color = Color.White)
                }


                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onLogout,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00B8D4)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Se déconnecter", color = Color.White)
                }
                if (currentUser?.role?.lowercase() == "admin") {
                    Button(
                        onClick = onNavigateToUserList,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0059D4)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text("Voir tous les utilisateurs", color = Color.White)
                    }
                }
            }
                else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Veuillez vous connecter pour voir votre profil", color = Color.White)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onBack) {
                        Text("Aller à la connexion")
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileEditableField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label.uppercase(),
            color = Color(0xFF00D4FF),
            fontSize = 12.sp
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF00D4FF),
                unfocusedBorderColor = Color.White,
                focusedLabelColor = Color(0xFF00D4FF),
                unfocusedLabelColor = Color.LightGray,
                cursorColor = Color(0xFF00D4FF)
            )
        )
    }
}
