package com.example.android_tv_temp.model.data

import com.example.android_tv_temp.network.dto.NetworkProgressState
import com.example.android_tv_temp.network.dto.VideoDetailResponseDto

data class VideoPlayerScreenUiState(
    val networkProgressState: NetworkProgressState = NetworkProgressState.Loading,
) {

    val title: String
        get() = when (networkProgressState) {
            is NetworkProgressState.Loading -> ""
            is NetworkProgressState.Success<*> -> {
                when (val response = networkProgressState.response) {
                    is VideoDetailResponseDto -> {
                        response.title
                    }

                    else -> ""
                }
            }

            is NetworkProgressState.Error<*> -> ""
        }
}

