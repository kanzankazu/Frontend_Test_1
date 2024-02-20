package com.danamon.data.implementation.user.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.danamon.data.implementation.user.local.room.model.UserEntity

@Dao
interface UserRequestDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertData(userEntity: UserEntity): Long?

    @Update
    fun updateData(data: UserEntity): Int

    @Query(value = "delete from user_table where userId == :userId")
    fun deleteData(userId: Long)

    @Query(value = "select * from user_table where userId == :userId limit 1")
    fun getData(userId: Long): UserEntity?

    @Query(value = "SELECT * FROM user_table WHERE email = :email AND password = :password LIMIT 1")
    fun getData(email: String, password: String): UserEntity?

    @Query("select * from user_table")
    fun getList(): List<UserEntity>
}
