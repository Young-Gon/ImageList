package com.gondev.imagelist.ui.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gondev.imagelist.domain.repository.GalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    galleryRepository: GalleryRepository,
):ViewModel() {
    val images = galleryRepository.loadGallery(viewModelScope)

    val currentPosition = MutableLiveData(0)
    fun onPageSelected(position: Int) {
        currentPosition.value = position
    }
}