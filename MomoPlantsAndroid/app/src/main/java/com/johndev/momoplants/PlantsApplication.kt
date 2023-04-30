package com.johndev.momoplants

import android.app.Application
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.johndev.momoplants.adapter.PlantAdapter
import com.johndev.momoplants.adapter.PlantsViewHolder
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