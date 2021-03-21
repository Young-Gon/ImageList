package com.gondev.imagelist.domain.repository

import com.gondev.imagelist.domain.network.response.ImageData
import com.gondev.imagelist.util.NetworkState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ImageListRepository {
    fun getImageList():Flow<NetworkState<List<ImageData>>>
}

class ImageListRepositoryImpl: ImageListRepository {
    override fun getImageList() = flow<NetworkState<List<ImageData>>> {
        TODO("Not yet implemented")
    }
}