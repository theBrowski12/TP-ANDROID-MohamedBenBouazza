package com.example.emtyapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.emtyapp.nav.AppNavigation
import com.example.emtyapp.ui.product.ProductViewModel
import com.example.emtyapp.ui.theme.EmtyAppTheme
import androidx.compose.material3.Surface
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ProductViewModel by viewModels<ProductViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EmtyAppTheme {
                Surface{
                    AppNavigation(viewModel)
                }
            }
        }
    }
}
