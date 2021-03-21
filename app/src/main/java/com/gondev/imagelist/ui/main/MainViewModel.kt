package com.gondev.imagelist.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gondev.imagelist.domain.repository.ImageListRepository
import com.gondev.imagelist.util.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    imageListRepository: ImageListRepository,
):ViewModel() {
    val imageList = imageListRepository.getImageList().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        NetworkState.loading(emptyList())
    )
}