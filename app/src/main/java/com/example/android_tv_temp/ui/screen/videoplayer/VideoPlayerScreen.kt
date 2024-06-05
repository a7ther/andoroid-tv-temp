package com.example.android_tv_temp.ui.screen.videoplayer

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.tv.material3.Text
import com.example.android_tv_temp.model.data.VideoPlayerScreenUiState
import com.example.android_tv_temp.network.dto.NetworkProgressState
import com.example.android_tv_temp.network.dto.VideoDetailResponseDto
import kotlinx.coroutines.delay

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun VideoPlayerScreen(
    player: ExoPlayer,
    uiState: VideoPlayerScreenUiState,
) {
    val context = LocalContext.current
    var contentCurrentPosition: Long by remember { mutableLongStateOf(0L) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(500L)
            contentCurrentPosition = player.currentPosition
        }
    }

    Box {
        AndroidView(
            factory = {
                PlayerView(context).apply {
                    hideController()
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                    this.player = player
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            onRelease = {
                it.player = null
                player.stop()
                player.clearMediaItems()
            }
        )
        CustomPlayerController(uiState = uiState)
    }
}

@Composable
fun CustomPlayerController(
    uiState: VideoPlayerScreenUiState,
) {
    //TODO: Implement custom player controller
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Black.copy(alpha = 0.2f),
                        Color.Black.copy(alpha = 0.9f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            fontSize = 20.sp,
            color = Color.White,
            text = uiState.title,
        )
    }
}

@Preview(device = Devices.TV_720p)
@Composable
fun Preview_CustomPlayerController() {
    CustomPlayerController(
        uiState = VideoPlayerScreenUiState(
            networkProgressState = NetworkProgressState.Success(
                response = VideoDetailResponseDto(
                    title = "Title",
                    videoUrl = "https://www.example.com/video.m3u8"
                )
            )
        )
    )
}