package com.gondev.imagelist.domain.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gondev.imagelist.domain.model.database.dao.ImageDataDao
import com.gondev.imagelist.domain.model.network.response.ImageData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


const val ITEM_ID = "itemId"

interface GalleryRepository {
    fun loadGallery(scope: CoroutineScope): LiveData<PagedList<ImageData>>
}

class GalleryRepositoryImpl @Inject constructor(
    private val dao: ImageDataDao,
    private val handle: SavedStateHandle
) : GalleryRepository {
    override fun loadGallery(scope: CoroutineScope) = LivePagedListBuilder(
        ViewPagerDataSource.Factory(
            dao,
            scope,
        ), 10
    ).setInitialLoadKey(handle[ITEM_ID]).build()
}

class ViewPagerDataSource(
    private val dao: ImageDataDao,
    private val scope: CoroutineScope,
) : ItemKeyedDataSource<Int, ImageData>() {

    class Factory(
        private val dao: ImageDataDao,
        private val viewModelScope: CoroutineScope,
    ) : DataSource.Factory<Int, ImageData>() {
        override fun create(): DataSource<Int, ImageData> {
            return ViewPagerDataSource(dao, viewModelScope)
        }
    }

    override fun getKey(item: ImageData) = item.id

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<ImageData>
    ) {
        scope.launch {
            callback.onResult(listOf(dao.findInitialImage(params.requestedInitialKey ?: 0)))
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<ImageData>) {
        scope.launch {
            callback.onResult(dao.findPrevImages(params.key, params.requestedLoadSize))
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<ImageData>) {
        scope.launch {
            callback.onResult(dao.findNextImages(params.key, params.requestedLoadSize).asReversed())
        }
    }
}
