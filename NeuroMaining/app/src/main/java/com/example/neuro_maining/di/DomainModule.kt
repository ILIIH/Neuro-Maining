package com.example.neuroMaining.di

import android.content.Context
import com.example.neuroMaining.data.AuthManagerIml
import com.example.neuroMaining.domain.AuthManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    fun provideAuthManager(
        @ApplicationContext context: Context
    ): AuthManager {
        return AuthManagerIml(context)
    }
}
