package com.example.emtyapp.data.Repository

import android.util.Log
import com.example.emtyapp.R
import com.example.emtyapp.data.API.ProductApi
import com.example.emtyapp.data.Entities.Product
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.math.log

    class ProductRepository @Inject constructor(
        private val api: ProductApi
    ) {
        suspend fun getProducts(): List<Product> {
            return try {
                api.getProducts().map { product ->
                    product.copy(
                        imageResId = mapProductImage(product.id)
                    )
                }
            } catch (e: Exception) {
                emptyList()
            }
        }

        private fun mapProductImage(productId: String): Int {
            return when (productId) {
                "PR001" -> R.drawable.iphone14
                "PR002" -> R.drawable.samsungtab
                "PR003" -> R.drawable.trotinette
                "PR006" -> R.drawable.tvsamsung
                "PR007" -> R.drawable.upcoming
                "PR008" -> R.drawable.logo
                "PR009" -> R.drawable.montre_fossil
                "PR010" -> R.drawable.collier_or
                "PR011" -> R.drawable.velo_electrique
                "PR012" -> R.drawable.gopro
                "PR013" -> R.drawable.blazer_homme
                "PR014" -> R.drawable.robe_femme
                "PR015" -> R.drawable.casque_moto

                "HM001" -> R.drawable.veste_jean
                "HM002" -> R.drawable.polo_lacoste
                "HM003" -> R.drawable.nike_airmax
                "HM004" -> R.drawable.tshirt_graphique
                "HM005" -> R.drawable.montre_casio
                "HM006" -> R.drawable.blouson_cuir
                "FM001" -> R.drawable.robe_fleurie
                "FM002" -> R.drawable.sac_cuir_camel
                "FM003" -> R.drawable.talons_noirs
                "FM004" -> R.drawable.ensemble_sport
                "FM005" -> R.drawable.montre_doree
                "FM006" -> R.drawable.blazer_femme

                else -> R.drawable.placeholder
            }
        }


        suspend fun getProductById(id: String): Product? {
            return try {
                val product = api.getProducts().find { it.id == id }?.copy(
                    imageResId = mapProductImage(id)
                )
                if (product == null) {
                    Log.w("ProductRepository", "Product with id $id not found")
                }
                product
            } catch (e: Exception) {
                Log.e("ProductRepository", "Error fetching product $id: ${e.message}", e)
                null
            }
        }
    }
