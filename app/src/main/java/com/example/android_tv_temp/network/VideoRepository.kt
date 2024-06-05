package com.example.android_tv_temp.network

import com.example.android_tv_temp.network.dto.VideoDetailResponseDto
import com.example.android_tv_temp.network.dto.VideoListResponseDto

interface VideoRepository {

    suspend fun fetchVideoList(): VideoListResponseDto
    suspend fun fetchVideoDetail(videoId: String): VideoDetailResponseDto

}