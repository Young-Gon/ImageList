package com.gondev.imagelist.domain.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.gondev.imagelist.domain.model.database.dao.ImageDataDao
import com.gondev.imagelist.domain.model.network.ImageAPI
import com.gondev.imagelist.domain.model.network.response.ImageData
import com.gondev.imagelist.util.NetworkState
import javax.inject.Inject

interface ImageListRepository {
    fun loadImageList(): LiveData<NetworkState<List<ImageData>>>
}

class ImageListRepositoryImpl @Inject constructor(
    private val dao: ImageDataDao,
    private val api: ImageAPI,
): ImageListRepository {
    override fun loadImageList() = liveData {
        val disposable= emitSource(dao.findAll().map{
            NetworkState.loading(it)
        })

        try {
            val imageList=api.requestImageList()
            disposable.dispose()

            dao.refreshAll(imageList.map {
                it.toEntity()
            })

            emitSource(dao.findAll().map{
                NetworkState.success(it)
            })
        } catch (e: Exception) {
            Log.e("ERROR",e.stackTraceToString())
            emitSource(dao.findAll().map{
                NetworkState.error(it, e)
            })
        }
    }
}