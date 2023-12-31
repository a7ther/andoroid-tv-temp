package com.example.android_tv_temp.network.dto

data class VideoListResponseDto(
    val carouselList: List<VideoCarouselResponseDto>,
) : NetworkProgressState.ResponseDto.Success

data class VideoCarouselResponseDto(
    val title: String,
    val itemList: List<VideoItemResponseDto>,
)

data class VideoItemResponseDto(
    val videoId: String,
    val title: String,
    val description: String,
    val thumbnailUrl: String,
)

