package com.example.emtyapp.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.emtyapp.data.Repository.ProductRepository
import com.example.emtyapp.ui.auth.AuthViewModel
import com.example.emtyapp.ui.auth.LoginScreen
import com.example.emtyapp.ui.auth.RegisterScreen
import com.example.emtyapp.ui.auth.Screens.ProfileScreen
import com.example.emtyapp.ui.product.ProductIntent
import com.example.emtyapp.ui.product.component.DetailsScreen
import com.example.emtyapp.ui.product.screens.HomeScreen
import com.example.emtyapp.ui.product.ProductViewModel
import com.example.emtyapp.ui.product.screens.CartScreen

object Routes {
    const val Home = "home"
    const val ProductDetails = "productDetails"
    const val Cart = "cart"
    const val Login = "login"  // Added
    const val Register = "register"
    const val Profile = "profile"// Added
}

@Composable
fun AppNavigation(viewModel: ProductViewModel= hiltViewModel(), authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Home) {

        composable(Routes.Home) {
            HomeScreen(
                authViewModel = authViewModel,
                viewModel = viewModel,
                onNavigateToDetails = { productId ->
                navController.navigate("${Routes.ProductDetails}/$productId")
            },
                onNavigateToCart = {
                    navController.navigate(Routes.Cart) // Fix: Navigate to cart
                },
                onNavigateToLogin = {
                    navController.navigate(Routes.Login)  // Fixed to navigate to login
                },
                onNavigateToRegister = {
                    navController.navigate(Routes.Register)  // Fixed to navigate to register
                },
                onNavigateToProfile = { navController.navigate(Routes.Profile) }
            )
        }

        composable(
            "${Routes.ProductDetails}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            DetailsScreen(
                productId = productId,
                viewModel = viewModel,
                onBuy = {
                    viewModel.handleIntent(ProductIntent.AddToCart(productId))
                    println("Product added to cart: $productId")
                }
            )
        }
        composable(Routes.Cart) {
            CartScreen(navController = navController,
                viewModel = viewModel) // )
        }
        // In your NavGraph file
        // In AppNavigation.kt
        composable(Routes.Login) {
            LoginScreen(
                authViewModel = authViewModel,

                onLoginSuccess = {
                    try {
                        navController.popBackStack()
                    } catch (e: Exception) {
                        navController.navigate(Routes.Home) {
                            popUpTo(Routes.Home)
                        }
                    }
                },
                onNavigateToRegister = {
                    try {
                        navController.navigate(Routes.Register) {
                            launchSingleTop = true
                        }                    }
                    catch (e: Exception) {
                        // Handle error
                    }
                }
            )
        }
        composable(Routes.Register) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.popBackStack()
                    // Or navigate to home if needed
                },
                onNavigateToLogin = {

                    navController.popBackStack()
                    // Or use navigate with popUpTo if needed
                },
                authViewModel = authViewModel,
                )
        }
        composable(Routes.Profile) {
            val currentUser by authViewModel.currentUser.collectAsState()

            if (currentUser != null) {
                ProfileScreen(
                    authViewModel = authViewModel,
                    onBack = { navController.popBackStack() },
                    onLogout = {
                        authViewModel.logout()
                        navController.popBackStack()
                    }
                )
            } else {
                LoginScreen(
                    authViewModel = authViewModel,
                    onLoginSuccess = { navController.popBackStack() },
                    onNavigateToRegister = { navController.navigate(Routes.Register) }
                )
            }
        }
        composable("${Routes.ProductDetails}/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            DetailsScreen(
                productId = productId,
                viewModel = viewModel,
                onBuy = {
                    navController.navigate(Routes.Cart)
                },
                onLoginClick = { navController.navigate(Routes.Login) },
                onRegisterClick = { navController.navigate(Routes.Register) },
                onHomeClick = {
                    navController.popBackStack(Routes.Home, inclusive = false)
                },
                onProfileClick = {
                    // Navigate to profile screen
                    //navController.navigate(Routes.Profile)
                },
                onCartClick = {
                    navController.navigate(Routes.Cart)
                }
            )
        }
    }
}
