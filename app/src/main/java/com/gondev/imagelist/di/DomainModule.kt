package com.gondev.imagelist.di

import com.gondev.imagelist.domain.repository.GalleryRepository
import com.gondev.imagelist.domain.repository.GalleryRepositoryImpl
import com.gondev.imagelist.domain.repository.ImageListRepository
import com.gondev.imagelist.domain.repository.ImageListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {
    @Binds
    @ViewModelScoped
    abstract fun bindImageListRepository(
        imageListRepositoryImpl: ImageListRepositoryImpl
    ): ImageListRepository

    @Binds
    @ViewModelScoped
    abstract fun bindGalleryRepository(
        galleryRepositoryImpl: GalleryRepositoryImpl
    ): GalleryRepository
}