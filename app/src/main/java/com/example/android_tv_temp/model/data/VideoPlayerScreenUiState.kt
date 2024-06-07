package com.example.android_tv_temp.model.data

import com.example.android_tv_temp.network.dto.NetworkProgressState
import com.example.android_tv_temp.network.dto.VideoDetailResponseDto

data class VideoPlayerScreenUiState(
    val networkProgressState: NetworkProgressState = NetworkProgressState.Loading,
    val playerControllerState: PlayerControllerState,
) {

    val title: String
        get() = response?.title ?: ""

    private val response: VideoDetailResponseDto?
        get() = when (val state = networkProgressState) {
            is NetworkProgressState.Loading -> null
            is NetworkProgressState.Success<*> -> {
                state.response as VideoDetailResponseDto
            }

            is NetworkProgressState.Error<*> -> null
        }
}

