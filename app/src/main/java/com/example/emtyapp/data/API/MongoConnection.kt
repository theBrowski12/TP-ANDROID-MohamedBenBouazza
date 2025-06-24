//package com.example.emtyapp.data.API
//
//import org.bson.Document
//import com.mongodb.ConnectionString
//import com.mongodb.MongoClientSettings
//import com.mongodb.ServerApi
//import com.mongodb.ServerApiVersion
//import com.mongodb.kotlin.client.coroutine.MongoClient
//import com.mongodb.kotlin.client.coroutine.MongoDatabase
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//
//object MongoConnection {
//
//    private val connectionString = "mongodb+srv://simobambou:45545247@ecommerce.0am7o58.mongodb.net/?retryWrites=true&w=majority&appName=eCommerce"
//
//    private val settings = MongoClientSettings.builder()
//        .applyConnectionString(ConnectionString(connectionString))
//        .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
//        .build()
//
//    val client: MongoClient = MongoClient.create(settings)
//
//    suspend fun ping(): Boolean = withContext(Dispatchers.IO) {
//        return@withContext try {
//            val database: MongoDatabase = client.getDatabase("ecommerce")
//            database.runCommand(Document("ping", 1))
//            println("✅ Pinged your deployment.")
//            true
//        } catch (e: Exception) {
//            e.printStackTrace()
//            false
//        }
//    }
//
//    fun getDatabase(): MongoDatabase = client.getDatabase("ecommerce")
//}
