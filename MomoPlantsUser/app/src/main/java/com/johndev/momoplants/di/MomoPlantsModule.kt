package com.johndev.momoplants.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.johndev.momoplants.common.dataAccess.MomoPlantsDataSource
import com.johndev.momoplants.common.dataAccess.MomoPlantsDataSourceRoom
import com.johndev.momoplants.common.dataAccess.PlantDao
import com.johndev.momoplants.common.utils.Constants.NAME_DATABASE
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindDataSource(impl: MomoPlantsDataSourceRoom): MomoPlantsDataSource

}

@InstallIn(SingletonComponent::class)
@Module
object FirebaseModule {

    @Singleton
    @Provides
    fun provideFirestore() = Firebase.firestore

    @Singleton
    @Provides
    fun provideFirebaseAnalytics() = Firebase.analytics

}

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Provides
    fun provideDao(database: MomoPlantsDatabase): PlantDao = database.plantDao()

    @Singleton
    @Provides
    fun provideDatabaseRoom(@ApplicationContext context: Context): MomoPlantsDatabase =
        Room.databaseBuilder(context, MomoPlantsDatabase::class.java, NAME_DATABASE).build()

}

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {
    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

}


