package com.example.emtyapp.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.emtyapp.data.Repository.ProductRepository
import com.example.emtyapp.ui.product.ProductIntent
import com.example.emtyapp.ui.product.component.DetailsScreen
import com.example.emtyapp.ui.product.screens.HomeScreen
import com.example.emtyapp.ui.product.ProductViewModel
import com.example.emtyapp.ui.product.screens.CartScreen

object Routes {
    const val Home = "home"
    const val ProductDetails = "productDetails"
    const val Cart = "cart"
}

@Composable
fun AppNavigation(viewModel: ProductViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Home) {

        composable(Routes.Home) {
            HomeScreen(viewModel = viewModel, onNavigateToDetails = { productId ->
                navController.navigate("${Routes.ProductDetails}/$productId")
            },
                onNavigateToCart = {
                    navController.navigate(Routes.Cart) // Fix: Navigate to cart
                })
        }

        composable(
            "${Routes.ProductDetails}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            DetailsScreen(
                productId = productId,
                viewModel = ProductViewModel(repository = ProductRepository()),
                onBuy = {
                    viewModel.handleIntent(ProductIntent.AddToCart(productId))
                    println("Product added to cart: $productId")
                }
            )
        }
        composable(Routes.Cart) {
            CartScreen(navController = navController)
        }

    }
}
