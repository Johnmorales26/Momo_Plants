package com.johndev.momoplants.loginModule.model

import com.johndev.momoplants.common.entities.UserEntity

class LoginRepository {

    private val loginDatabase = LoginDatabase()

    suspend fun getUserByEmailAndPassword(email: String, password: String) = loginDatabase.getUserByEmailAndPassword(email, password)

    suspend fun insert(userEntity: UserEntity) = loginDatabase.insert(userEntity)

}