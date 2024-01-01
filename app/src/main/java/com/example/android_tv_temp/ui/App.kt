package com.example.android_tv_temp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.android_tv_temp.model.valueobject.ScreenType

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun App() {
    val navController = rememberNavController()
    val startScreenName = ScreenType.MainScreen.route
    MyRouter(
        navController = navController,
        startScreenName = startScreenName,
    )
}