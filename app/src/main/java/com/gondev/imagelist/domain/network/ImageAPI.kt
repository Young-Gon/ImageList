package com.gondev.imagelist.domain.network

import com.gondev.imagelist.domain.network.response.ImageData
import retrofit2.http.GET

interface ImageAPI {

    @GET("list")
    suspend fun requestImageList(): List<ImageData>
}