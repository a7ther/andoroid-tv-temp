package com.example.android_tv_temp.ui.screen.menu2

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.android_tv_temp.model.data.Menu1ScreenUiState
import com.example.android_tv_temp.ui.screen.menu1.Menu1Screen

@Composable
fun Menu2Screen(
    navController: NavHostController,
    uiState: Menu1ScreenUiState,
) {
    Menu1Screen(navController, uiState)
}

