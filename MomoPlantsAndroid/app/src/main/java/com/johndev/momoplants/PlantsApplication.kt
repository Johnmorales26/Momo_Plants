package com.johndev.momoplants

import android.app.Application
import androidx.room.Room
import com.johndev.momoplants.common.dataAccess.PlantsDatabase


class PlantsApplication : Application() {

    companion object {
        lateinit var database: PlantsDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, PlantsDatabase::class.java, "PlantsDatabase").build()
    }


}