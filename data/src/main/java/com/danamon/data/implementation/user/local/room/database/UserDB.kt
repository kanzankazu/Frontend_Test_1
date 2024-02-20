package com.danamon.data.implementation.user.local.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.danamon.data.implementation.user.local.room.dao.UserRequestDao
import com.danamon.data.implementation.user.local.room.model.UserEntity

@Database(
    entities = [
        UserEntity::class,
    ],
    version = 1,
    exportSchema = true,
)

abstract class UserDB : RoomDatabase() {

    abstract fun userRequestDao(): UserRequestDao

    companion object {
        private const val DB_NAME = "user_database"

        @Volatile
        private var instance: UserDB? = null

        fun getInstance(context: Context): UserDB {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            UserDB::class.java, DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
