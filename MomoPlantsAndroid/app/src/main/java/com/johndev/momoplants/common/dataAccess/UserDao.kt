package com.johndev.momoplants.common.dataAccess

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.johndev.momoplants.common.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM UserEntity WHERE email = :email AND password = :password")
    suspend fun getUserByEmailAndPassword(email: String, password: String): UserEntity?

    @Query("SELECT * FROM UserEntity WHERE user_id=:user_id")
    suspend fun getUserById(user_id:Long): UserEntity?

    @Insert
    fun insert(userEntity: UserEntity): Long

    @Update
    fun update(userEntity: UserEntity)

}