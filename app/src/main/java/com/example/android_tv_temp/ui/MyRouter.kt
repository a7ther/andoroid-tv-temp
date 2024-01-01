package com.example.android_tv_temp.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.android_tv_temp.model.valueobject.MenuType
import com.example.android_tv_temp.model.valueobject.ScreenType
import com.example.android_tv_temp.ui.screen.MainScreen
import com.example.android_tv_temp.ui.screen.menu1.Menu1Screen
import com.example.android_tv_temp.ui.screen.menu1.Menu1ViewModel

@Composable
fun MyRouter(
    navController: NavHostController,
    startScreenName: String,
    menu1ViewModel: Menu1ViewModel = hiltViewModel()
) {

    NavHost(
        navController = navController,
        startDestination = startScreenName,
    ) {
        composable(ScreenType.MainScreen.route) {
            MainScreen(navController)
        }
        composable(MenuType.MENU1.route) {
            Menu1Screen(navController, menu1ViewModel.uiState)
        }
        composable(MenuType.MENU2.route) {
            Menu1Screen(navController, menu1ViewModel.uiState)
        }
        composable(MenuType.MENU3.route) {
            Menu1Screen(navController, menu1ViewModel.uiState)
        }
        composable(
            route = ScreenType.VideoPlayerScreen.route,
            arguments = ScreenType.VideoPlayerScreen.arguments,
        ) {
            val videoId = it.arguments?.getString(ScreenType.VideoPlayerScreen.keyVideoId) ?: return@composable
//            Card(
//                onClick = { },
//                modifier = Modifier.fillMaxSize(),
//            ) {
//                Text(
//                    text = "videoId : $videoId",
//                    modifier = Modifier.padding(20.dp),
//                )
//            }
//            VideoPlayerScreen()
        }
    }
}