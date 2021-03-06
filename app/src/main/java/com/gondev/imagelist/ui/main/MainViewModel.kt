package com.gondev.imagelist.ui.main

import androidx.lifecycle.ViewModel
import com.gondev.imagelist.domain.repository.ImageListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    imageListRepository: ImageListRepository,
):ViewModel() {
    val imageList = imageListRepository.loadImageList()

}