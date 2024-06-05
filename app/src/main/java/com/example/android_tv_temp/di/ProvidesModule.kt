package com.example.android_tv_temp.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.example.android_tv_temp.network.StubVideoRepositoryImpl
import com.example.android_tv_temp.network.VideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {

    @Singleton
    @Provides
    fun provideVideoRepository(): VideoRepository = StubVideoRepositoryImpl()

    @Singleton
    @Provides
    fun provideExoPlayer(@ApplicationContext context: Context): ExoPlayer {
        return ExoPlayer.Builder(context)
            .build()
    }
}