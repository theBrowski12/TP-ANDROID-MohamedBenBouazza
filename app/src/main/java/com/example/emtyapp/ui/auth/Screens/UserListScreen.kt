package com.example.emtyapp.ui.auth.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.emtyapp.ui.auth.AuthViewModel
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.emtyapp.data.Entities.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    authViewModel: AuthViewModel,
    onBack: () -> Unit
) {
    val allUsers by authViewModel.allUsers.collectAsState()
    val error by authViewModel.errorMessage.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()
    var userToDelete by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(Unit) {
        authViewModel.loadAllUsersIfAdmin()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Utilisateurs", color = Color(0xFF00D4FF))
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour", tint = Color(0xFF00D4FF))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2A2A3A))
            )
        },
        containerColor = Color(0xFF0A0A12)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            error?.let {
                Text(it, color = Color.Red)
            }

            if (currentUser?.role?.lowercase() != "admin") {
                Text("Accès refusé : réservé aux administrateurs.", color = Color.Red)
            } else {
                // Statistiques Utilisateurs avec style distinct
                val totalUsers = allUsers.size
                val adminCount = allUsers.count { it.role.lowercase() == "admin" }
                val memberCount = allUsers.count { it.role.lowercase() == "member" || it.role.lowercase() == "user" }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF004A99)), // bleu vif différent
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            "📊 Statistiques Utilisateurs",
                            color = Color(0xFFFFD700), // or brillant
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            "👥 Total : $totalUsers",
                            color = Color(0xFFFFF8E7),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            "👑 Admins : $adminCount",
                            color = Color(0xFFB0E0E6), // bleu clair
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            "🙋 Membres : $memberCount",
                            color = Color(0xFFB0E0E6),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                // Liste des utilisateurs avec style plus sombre
                LazyColumn {
                    items(allUsers) { user ->
                        val expanded = remember { mutableStateOf(false) }
                        val isCurrentUser = user.id == currentUser?.id

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isCurrentUser) Color(0xFF2E7D32) else Color(
                                    0xFF25254D
                                ) // Vert si c’est vous
                            ),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = if (isCurrentUser) 8.dp else 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "👤 ${user.name}",
                                        color = Color(0xFF00D4FF),
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.weight(1f)
                                    )

                                    if (isCurrentUser) {
                                        Text(
                                            text = "👈 C’est vous",
                                            color = Color(0xFFFFF176), // Jaune clair
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }

                                Text(
                                    text = "📧 ${user.email}",
                                    color = Color.LightGray,
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                if (!isCurrentUser) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "🔐 Rôle : ${user.role}",
                                            color = Color(0xFF00B8D4),
                                            style = MaterialTheme.typography.bodySmall,
                                            modifier = Modifier.weight(1f)
                                        )
                                        IconButton(onClick = { expanded.value = true }) {
                                            Icon(
                                                Icons.Default.ArrowDropDown,
                                                contentDescription = "Changer rôle",
                                                tint = Color(0xFF00B8D4)
                                            )
                                        }
                                        DropdownMenu(
                                            expanded = expanded.value,
                                            onDismissRequest = { expanded.value = false }
                                        ) {
                                            val roles = listOf("admin", "member", "user")
                                            roles.forEach { roleOption ->
                                                DropdownMenuItem(
                                                    text = { Text(roleOption) },
                                                    onClick = {
                                                        expanded.value = false
                                                        authViewModel.updateUserRole(user.id!!, roleOption)
                                                    }
                                                )
                                            }
                                        }
                                    }
                                } else {
                                    Text(
                                        text = "🔐 Rôle : ${user.role}",
                                        color = Color(0xFF00B8D4),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }

                                Spacer(modifier = Modifier.height(2.dp))

                                if (currentUser?.role?.lowercase() == "admin" && !isCurrentUser) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Button(
                                        onClick = { userToDelete = user },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB00020))
                                    ) {
                                        Text("Supprimer", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }
    if (userToDelete != null) {
        AlertDialog(
            onDismissRequest = { userToDelete = null },
            confirmButton = {
                Button(onClick = {
                    authViewModel.deleteUser(userToDelete!!.id!!)
                    userToDelete = null
                }) {
                    Text("Confirmer")
                }
            },
            dismissButton = {
                Button(onClick = { userToDelete = null }) {
                    Text("Annuler")
                }
            },
            title = { Text("Confirmation") },
            text = { Text("Voulez-vous vraiment supprimer cet utilisateur ?") }
        )
    }
}


