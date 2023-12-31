package com.example.android_tv_temp.di

import com.example.android_tv_temp.network.StubVideoRepositoryImpl
import com.example.android_tv_temp.network.VideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {

    @Singleton
    @Provides
    fun provideVideoRepository(): VideoRepository = StubVideoRepositoryImpl()

}