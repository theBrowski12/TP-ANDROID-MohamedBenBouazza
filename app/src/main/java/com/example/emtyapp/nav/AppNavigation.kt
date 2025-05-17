package com.example.emtyapp.nav

<<<<<<< HEAD
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
=======
import androidx.compose.runtime.Composable
>>>>>>> 49c4b61b1e8a076dbd190c9beaada34bb5a35e1e
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
<<<<<<< HEAD
import com.example.emtyapp.ui.product.component.DetailsScreen
import com.example.emtyapp.ui.product.screens.HomeScreen
import com.example.emtyapp.ui.product.ProductViewModel
=======
import com.example.emtyapp.ui.screens.DetailsScreen
import com.example.emtyapp.ui.screens.HomeScreen
>>>>>>> 49c4b61b1e8a076dbd190c9beaada34bb5a35e1e

object Routes {
    const val Home = "home"
    const val ProductDetails = "productDetails"
}

@Composable
<<<<<<< HEAD
fun AppNavigation(viewModel: ProductViewModel) {
=======
fun AppNavigation() {
>>>>>>> 49c4b61b1e8a076dbd190c9beaada34bb5a35e1e
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Home) {

        composable(Routes.Home) {
            HomeScreen(onNavigateToDetails = { productId ->
                navController.navigate("${Routes.ProductDetails}/$productId")
            })
        }


        composable(
            "${Routes.ProductDetails}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
<<<<<<< HEAD
            DetailsScreen(productId = productId)

        }
    }
}
@Composable
fun HomeScreenDeprecated(onNavigateToDetails: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Bienvenue sur HomeScreen")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { onNavigateToDetails("PR1234") }) {
            Text(text = "Aller aux détails du produit PR1234")
        }
    }
}
=======

            DetailsScreen(
                productId = productId,
                onBack = { navController.popBackStack() },
                onBuy = {}
            )
        }
    }
}
>>>>>>> 49c4b61b1e8a076dbd190c9beaada34bb5a35e1e
