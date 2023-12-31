package com.example.android_tv_temp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.tv.material3.Card
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import com.example.android_tv_temp.model.valueobject.MenuType
import com.example.android_tv_temp.model.valueobject.ScreenType
import com.example.android_tv_temp.ui.screen.Menu1Screen
import com.example.android_tv_temp.ui.screen.Menu1ViewModel

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MyRouter(
    navController: NavHostController,
    startScreenName: String,
    menu1ViewModel: Menu1ViewModel = hiltViewModel()
) {

    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = startScreenName,
    ) {
        composable(MenuType.Menu1.route) {
            Menu1Screen(navController, menu1ViewModel.uiState)
        }
        composable(MenuType.Menu2.route) {
            Menu1Screen(navController, menu1ViewModel.uiState)
        }
        composable(MenuType.Menu3.route) {
            Menu1Screen(navController, menu1ViewModel.uiState)
        }
        composable(
            route = ScreenType.VideoScreen.route,
            arguments = ScreenType.VideoScreen.arguments,
        ) {
            val videoId = it.arguments?.getString(ScreenType.VideoScreen.keyVideoId) ?: return@composable
            Card(
                onClick = { },
            ) {
                Text(
                    text = "videoId : $videoId",
                    modifier = Modifier.padding(20.dp),
                )
            }
        }
    }
}