package com.example.android_tv_temp.model.valueobject

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.android_tv_temp.model.data.NavRouter

sealed interface ScreenType : NavRouter {

    data object MainScreen : ScreenType
    data object VideoPlayerScreen : ScreenType {

        const val keyVideoId = "videoId"
        override val route: String = "${super.route}/{$keyVideoId}"
        override val arguments = listOf(navArgument(keyVideoId) { type = NavType.StringType })
        fun createTransitionRoute(videoId: String): String = "${super.route}/$videoId"
    }
}