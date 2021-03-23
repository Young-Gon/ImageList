package com.gondev.imagelist.domain.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_data")
class ImageDataEntity(
    @PrimaryKey
    val id: Int,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val download_url: String,
)