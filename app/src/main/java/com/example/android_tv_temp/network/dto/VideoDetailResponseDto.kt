package com.example.android_tv_temp.network.dto

data class VideoDetailResponseDto(
    val title: String,
    val videoUrl: String,
) : NetworkProgressState.ResponseDto.Success

