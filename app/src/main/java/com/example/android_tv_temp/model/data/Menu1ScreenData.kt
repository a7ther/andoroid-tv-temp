package com.example.android_tv_temp.model.data

data class Menu1ScreenData(
    val carousels: List<Carousel>
) {

    data class Carousel(
        val carouselTitle: String,
        val cards: List<MyCardData>,
    )
}

