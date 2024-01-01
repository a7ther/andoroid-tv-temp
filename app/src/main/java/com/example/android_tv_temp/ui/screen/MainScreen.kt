package com.example.android_tv_temp.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun MainScreen(
    navController: NavHostController,
) {
    MyNavigationDrawer(
        navController = navController,
    )
}

