package com.example.android_tv_temp.model.data

import com.example.android_tv_temp.network.dto.NetworkProgressState
import com.example.android_tv_temp.network.dto.VideoListResponseDto

data class Menu1ScreenUiState(
    val networkProgressState: NetworkProgressState = NetworkProgressState.Loading,
) {

    val carousels: List<Carousel>
        get() = when (networkProgressState) {
            is NetworkProgressState.Loading -> emptyList()
            is NetworkProgressState.Success<*> -> {
                when (val response = networkProgressState.response) {
                    is VideoListResponseDto -> {
                        response.carouselList.map { carousel ->
                            Carousel(
                                carouselTitle = carousel.title,
                                cards = carousel.itemList.map {
                                    MyCardData(
                                        videoId = it.videoId,
                                        title = it.title,
                                        description = it.description,
                                        imageUrl = it.thumbnailUrl,
                                    )
                                }
                            )
                        }
                    }

                    else -> emptyList()
                }
            }

            is NetworkProgressState.Error<*> -> emptyList()
        }

    data class Carousel(
        val carouselTitle: String,
        val cards: List<MyCardData>,
    )
}

