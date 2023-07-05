package com.johndev.momoplantsparent.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Singleton
    @Provides
    fun provideFirebaseAnalytics() = Firebase.analytics

    @Singleton
    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

}

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {
    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

}