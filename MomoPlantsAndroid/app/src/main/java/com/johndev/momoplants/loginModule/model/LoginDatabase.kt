package com.johndev.momoplants.loginModule.model

import android.database.sqlite.SQLiteConstraintException
import com.johndev.momoplants.PlantsApplication
import com.johndev.momoplants.common.dataAccess.UserDao
import com.johndev.momoplants.common.entities.UserEntity
import com.johndev.momoplants.common.utils.Constants.ERROR_EXIST
import com.johndev.momoplants.common.utils.Constants.ERROR_UNKNOW
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginDatabase {

    private val dao: UserDao by lazy { PlantsApplication.database.userDao() }

    suspend fun getUserByEmailAndPassword(email: String, password: String) = dao.getUserByEmailAndPassword(email, password)

    suspend fun insert(userEntity: UserEntity) = withContext(Dispatchers.IO) {
        try {
            dao.insert(userEntity)
        } catch (e: Exception) {
            (e as? SQLiteConstraintException)?.let { throw Exception(ERROR_EXIST) }
            throw Exception(e.message ?: ERROR_UNKNOW)
        }
    }

}