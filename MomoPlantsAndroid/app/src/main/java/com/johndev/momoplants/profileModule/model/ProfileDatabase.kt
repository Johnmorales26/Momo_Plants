package com.johndev.momoplants.profileModule.model

import com.johndev.momoplants.PlantsApplication
import com.johndev.momoplants.common.dataAccess.UserDao

class ProfileDatabase {
    private val dao:UserDao by lazy { PlantsApplication.database.userDao() }
    suspend fun getUserById(user_id:Long) = dao.getUserById(user_id)
}