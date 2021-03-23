package com.gondev.imagelist.domain.model.network

import com.gondev.imagelist.domain.model.network.response.ImageData
import retrofit2.http.GET

interface ImageAPI {

    @GET("list")
    suspend fun requestImageList(): List<ImageData>
}