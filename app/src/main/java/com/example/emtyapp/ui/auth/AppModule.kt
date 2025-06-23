package com.example.emtyapp.ui.auth

import android.app.Application
import android.content.Context
import com.example.emtyapp.data.Repository.ProductRepository
import com.example.emtyapp.data.Repository.UserRepository
import com.example.emtyapp.data.Repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideUserRepository(context: Context): UserRepository {
        return UserRepositoryImpl(context)
    }
}