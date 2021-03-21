package com.gondev.imagelist.di

import com.gondev.imagelist.domain.repository.ImageListRepository
import com.gondev.imagelist.domain.repository.ImageListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
abstract class DomainModule {
    @Binds
    abstract fun bindImageListRepository(
        imageListRepositoryImpl: ImageListRepositoryImpl
    ): ImageListRepository
}