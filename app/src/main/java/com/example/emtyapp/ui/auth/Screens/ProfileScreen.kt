package com.example.emtyapp.ui.auth.Screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.emtyapp.ui.auth.AuthViewModel

@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit,
    authViewModel: AuthViewModel,

    ) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        try {
            authViewModel.currentUser.collect { user ->
                if (user == null) {
                    // Optional: Show message or redirect
                }
            }
        } catch (e: Exception) {
            Log.e("ProfileScreen", "Error observing user", e)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (currentUser != null) {
            Text("Name: ${currentUser?.name ?: "Not provided"}", style = MaterialTheme.typography.titleLarge)
            Text("Email: ${currentUser?.email}", style = MaterialTheme.typography.bodyLarge)
            Text("Role: ${currentUser?.role}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onLogout) {
                Text("Logout")
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Please login to view profile")
                Button(onClick = onBack) {
                    Text("Go to Login")
                }
            }
        }
    }
}