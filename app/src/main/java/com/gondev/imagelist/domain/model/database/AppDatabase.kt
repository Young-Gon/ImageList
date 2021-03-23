package com.gondev.imagelist.domain.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gondev.imagelist.domain.model.database.dao.ImageDataDao
import com.gondev.imagelist.domain.model.database.entity.ImageDataEntity


const val DATABASE_NAME = "image_list"

@Database(
    entities = [
        ImageDataEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getImageDataDao(): ImageDataDao
}