package com.example.emtyapp.ui.product
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri

fun openWhatsAppWithMessage(context: Context, phoneNumber: String, message: String) {
    try {
        val url = "https://wa.me/$phoneNumber?text=${Uri.encode(message)}"
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        context.startActivity(intent)
    } catch (e: Exception) {
        // Fallback if WhatsApp is not installed
        val alternateUrl = "https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(message)}"
        val intent = Intent(Intent.ACTION_VIEW, alternateUrl.toUri())
        context.startActivity(intent)
    }
}