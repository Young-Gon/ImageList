package com.gondev.imagelist.domain.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.gondev.imagelist.domain.model.database.entity.ImageDataEntity
import com.gondev.imagelist.domain.model.network.response.ImageData

@Dao
interface ImageDataDao {
    @Query("SELECT * FROM image_data")
    fun findAll(): LiveData<List<ImageData>>

    @Insert
    suspend fun insertAll(entities: List<ImageDataEntity>)

    @Query("DELETE FROM image_data")
    suspend fun deleteAll()

    @Transaction
    suspend fun refreshAll(entities: List<ImageDataEntity>){
        deleteAll()
        insertAll(entities)
    }

    @Query("SELECT * FROM image_data WHERE id = :id")
    suspend fun findInitialImage(id: Int): ImageData

    @Query("SELECT * FROM image_data WHERE id < :key ORDER BY id DESC LIMIT :requestedLoadSize")
    suspend fun findNextImages(key: Int, requestedLoadSize: Int): MutableList<ImageData>

    @Query("SELECT * FROM image_data WHERE :key < id ORDER BY id ASC LIMIT :requestedLoadSize")
    suspend fun findPrevImages(key: Int, requestedLoadSize: Int): MutableList<ImageData>
}