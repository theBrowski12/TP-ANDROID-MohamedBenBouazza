package com.example.emtyapp.nav

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.emtyapp.ui.auth.AuthViewModel
import com.example.emtyapp.ui.auth.LoginScreen
import com.example.emtyapp.ui.auth.RegisterScreen
import com.example.emtyapp.ui.auth.Screens.ProfileScreen
import com.example.emtyapp.ui.auth.Screens.UserListScreen
import com.example.emtyapp.ui.order.OrderScreen
import com.example.emtyapp.ui.product.ProductViewModel
import com.example.emtyapp.ui.product.component.AddNewProductScreen
import com.example.emtyapp.ui.product.component.DetailsScreen
import com.example.emtyapp.ui.product.component.EditProductScreen
import com.example.emtyapp.ui.product.screens.CartScreen
import com.example.emtyapp.ui.product.screens.HomeScreen

object Routes {
    const val Home = "home"
    const val ProductDetails = "productDetails"
    const val Cart = "cart"
    const val Login = "login"
    const val Register = "register"
    const val Profile = "profile"
    const val UserList = "userList"
    const val EditProduct = "edit_product"
    const val AddProduct = "add_product"
    const val Orders = "orders"

}

@Composable
fun AppNavigation(
    viewModel: ProductViewModel = hiltViewModel(),
    authViewModel: AuthViewModel
) {
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
                    navController.navigate(Routes.Cart) {
                        launchSingleTop = true
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Routes.Login) {
                        launchSingleTop = true
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Routes.Register) {
                        launchSingleTop = true
                    }
                },
                onNavigateToProfile = {
                    navController.navigate(Routes.Profile) {
                        launchSingleTop = true
                    }
                },
                onNavigateToEditProduct = { productId ->
                    navController.navigate("${Routes.EditProduct}/$productId") {
                        launchSingleTop = true
                    }
                },
                onNavigateToAddProduct = {
                    navController.navigate(Routes.AddProduct) {
                        launchSingleTop = true
                    }
                },
                onNavigateToOrders = {
                    navController.navigate(Routes.Orders) {
                        launchSingleTop = true
                    }
                }

            )
        }
        composable(Routes.AddProduct) {
            AddNewProductScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Routes.Home) { launchSingleTop = true }
                },
                onNavigateToProfile = {
                    navController.navigate(Routes.Profile) { launchSingleTop = true }
                },
                onNavigateToCart = {
                    navController.navigate(Routes.Cart) { launchSingleTop = true }
                }
            )
        }

        composable(
            "${Routes.ProductDetails}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            DetailsScreen(
                productId = productId,
                authViewModel = authViewModel,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onHomeClick = {
                    navController.navigate(Routes.Home) {
                        launchSingleTop = true
                    }
                },
                onLoginClick = {
                    navController.navigate(Routes.Login) {
                        launchSingleTop = true
                    }
                },
                onRegisterClick = {
                    navController.navigate(Routes.Register) {
                        launchSingleTop = true
                    }
                },
                onProfileClick = {
                    navController.navigate(Routes.Profile) {
                        launchSingleTop = true
                    }
                },
                onCartClick = {
                    navController.navigate(Routes.Cart) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.Cart) {
            CartScreen(
                navController = navController,
                viewModel = viewModel,
                authViewModel = authViewModel
            )
        }

        composable(Routes.Login) {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = {
                    navController.popBackStack()
                },
                onNavigateToRegister = {
                    navController.navigate(Routes.Register) {
                        launchSingleTop = true
                    }
                },
                onHomeClick = {
                    navController.navigate(Routes.Home) {
                        launchSingleTop = true
                    }
                },
                onCartClick = {
                    navController.navigate(Routes.Cart) {
                        launchSingleTop = true
                    }
                },
                onProfileClick = {
                    navController.navigate(Routes.Profile) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.Register) {
            RegisterScreen(
                authViewModel = authViewModel,
                onRegisterSuccess = { navController.navigate(Routes.Home) },
                onNavigateToLogin = { navController.popBackStack() },
                onNavigateToHome = { navController.navigate(Routes.Home) },
                onNavigateToCart = { navController.navigate(Routes.Cart) }
            )
        }

        composable(Routes.Profile) {
            val currentUser by authViewModel.currentUser.collectAsState()
            if (currentUser != null) {
                ProfileScreen(
                    onBack = { navController.popBackStack() },
                    onLogout = {
                        authViewModel.logout()
                        navController.navigate(Routes.Home) {
                            popUpTo(Routes.Profile) { inclusive = true }
                        }
                    },
                    onNavigateToHome = {
                        navController.navigate(Routes.Home) {
                            launchSingleTop = true
                        }
                    },
                    onNavigateToCart = {
                        navController.navigate(Routes.Cart) {
                            launchSingleTop = true
                        }
                    },
                    onNavigateToUserList = {
                        navController.navigate(Routes.UserList) {
                            launchSingleTop = true
                        }
                    },
                    authViewModel = authViewModel
                )
            } else {
                LoginScreen(
                    authViewModel = authViewModel,
                    onLoginSuccess = {
                        navController.popBackStack()
                    },
                    onNavigateToRegister = {
                        navController.navigate(Routes.Register) {
                            launchSingleTop = true
                        }
                    },
                    onHomeClick = {
                        navController.navigate(Routes.Home) {
                            launchSingleTop = true
                        }
                    },
                    onCartClick = {
                        navController.navigate(Routes.Cart) {
                            launchSingleTop = true
                        }
                    },
                    onProfileClick = {
                        navController.navigate(Routes.Profile) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
        composable(Routes.UserList) {
            val currentUser by authViewModel.currentUser.collectAsState()

            if (currentUser?.role?.lowercase() == "admin") {
                UserListScreen(
                    authViewModel = authViewModel,
                    onBack = { navController.popBackStack() }
                )
            } else {
                // Redirection si non admin
                LoginScreen(
                    authViewModel = authViewModel,
                    onLoginSuccess = {
                        navController.navigate(Routes.UserList) {
                            popUpTo(Routes.Login) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate(Routes.Register) {
                            launchSingleTop = true
                        }
                    },
                    onHomeClick = {
                        navController.navigate(Routes.Home) {
                            launchSingleTop = true
                        }
                    },
                    onCartClick = {
                        navController.navigate(Routes.Cart) {
                            launchSingleTop = true
                        }
                    },
                    onProfileClick = {
                        navController.navigate(Routes.Profile) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
        composable("${Routes.EditProduct}/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            val product by viewModel.selectedProduct.collectAsState()

            LaunchedEffect(productId) {
                viewModel.loadProductById(productId)
            }

            product?.let {
                EditProductScreen(
                    product = it,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack()
                    }
                )
            } ?: Text("Chargement...")
        }
        composable(Routes.Orders) {
            val currentUser by authViewModel.currentUser.collectAsState()
            currentUser?.let {
                OrderScreen(
                    isAdmin = it.role.lowercase() == "admin",
                    userId = it.id,
                    onNavigateBack = { navController.popBackStack() }, // <- ça doit être bien fourni
                    onNavigateToHome = { navController.navigate(Routes.Home) },
                    onNavigateToProfile = { navController.navigate(Routes.Profile) },
                    onNavigateToCart = { navController.navigate(Routes.Cart) }
                )
            }
        }






    }
}
