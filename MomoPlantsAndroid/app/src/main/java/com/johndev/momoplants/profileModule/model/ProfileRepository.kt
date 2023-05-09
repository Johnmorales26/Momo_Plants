package com.johndev.momoplants.profileModule.model

class ProfileRepository {
    private val profileDatabase = ProfileDatabase()
    suspend fun getUserById(user_id:Long) = profileDatabase.getUserById(user_id)
}