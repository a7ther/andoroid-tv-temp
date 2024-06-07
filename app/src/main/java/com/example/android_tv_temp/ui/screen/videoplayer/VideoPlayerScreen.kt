package com.example.android_tv_temp.ui.screen.videoplayer

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.tv.material3.Icon
import androidx.tv.material3.Text
import com.example.android_tv_temp.model.data.PlayerControllerState
import com.example.android_tv_temp.model.data.VideoPlayerScreenUiState
import com.example.android_tv_temp.network.dto.NetworkProgressState
import com.example.android_tv_temp.network.dto.VideoDetailResponseDto
import com.example.android_tv_temp.ui.extension.onCustomKeyEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun VideoPlayerScreen(
    player: ExoPlayer,
    uiState: VideoPlayerScreenUiState,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .focusable()
            .onCustomKeyEvent(
                onLeft = { uiState.playerControllerState.onClickSeek(-10.seconds.inWholeMilliseconds) },
                onRight = { uiState.playerControllerState.onClickSeek(10.seconds.inWholeMilliseconds) },
                onEnter = {
                    coroutineScope.launch {
                        uiState.playerControllerState.onClickTogglePlay()
                    }
                },
                onDown = {
                    uiState.playerControllerState.showCustomPlayerController()
                }
            )
    ) {
        AndroidView(factory = {
            PlayerView(context).apply {
                hideController()
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                this.player = player
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }, onRelease = {
            it.player = null
            player.stop()
            player.clearMediaItems()
        })
        CustomPlayerController(
            uiState = uiState,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomPlayerController(
    uiState: VideoPlayerScreenUiState,
) {
    val currentPosition by uiState.playerControllerState.currentPosition.collectAsState()
    val duration by uiState.playerControllerState.duration.collectAsState()
    val isVisible by uiState.playerControllerState.isVisibleCustomPlayerController.collectAsState()
    val currentDisplayTime by uiState.playerControllerState.currentDisplayTime.collectAsState("")
    val durationDisplayTime by uiState.playerControllerState.durationDisplayTime.collectAsState("")
    val isVisiblePlayIcon by uiState.playerControllerState.isVisiblePlayIcon.collectAsState()
    val isVisiblePauseIcon by uiState.playerControllerState.isVisiblePauseIcon.collectAsState()
    val initialFocus = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        uiState.playerControllerState.observeAutoHideController()
    }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            initialFocus.requestFocus()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isVisiblePlayIcon,
            enter = EnterTransition.None,
            exit = fadeOut() + scaleOut(targetScale = 2f),
        ) {
            Icon(
                Icons.Default.PlayCircleFilled,
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight(0.3f)
                    .align(Alignment.Center),
                contentDescription = null,
                tint = Color.White
            )
        }

        AnimatedVisibility(
            visible = isVisiblePauseIcon,
            enter = EnterTransition.None,
            exit = fadeOut() + scaleOut(targetScale = 2f),
        ) {
            Icon(
                Icons.Default.PauseCircle,
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight(0.3f)
                    .align(Alignment.Center),
                contentDescription = null,
                tint = Color.White
            )
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black.copy(alpha = 0.2f), Color.Black.copy(alpha = 0.9f)
                        )
                    )
                ), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    text = uiState.title,
                )

                Spacer(modifier = Modifier.padding(8.dp))

                val interactionSource = remember { MutableInteractionSource() }
                val sliderColor = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Color.Gray,
                )
                CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                    Slider(
                        modifier = Modifier
                            .focusRequester(initialFocus)
                            .onCustomKeyEvent(
                                onLeft = { uiState.playerControllerState.onClickSeek(-10.seconds.inWholeMilliseconds) },
                                onRight = { uiState.playerControllerState.onClickSeek(10.seconds.inWholeMilliseconds) },
                                onEnter = {
                                    coroutineScope.launch {
                                        uiState.playerControllerState.onClickTogglePlay()
                                    }
                                },
                                onUp = { uiState.playerControllerState.hideCustomPlayerController() }),
                        value = currentPosition.toFloat(),
                        onValueChange = {},
                        valueRange = 0f..duration.toFloat(),
                        interactionSource = interactionSource,
                        colors = sliderColor,
                        thumb = {
                            SliderDefaults.Thumb(
                                interactionSource = interactionSource,
                                thumbSize = DpSize(12.dp, 12.dp),
                                colors = sliderColor,
                                modifier = Modifier.padding(4.dp),
                            )
                        },
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        fontSize = 16.sp,
                        color = Color.Gray,
                        text = currentDisplayTime,
                    )
                    Text(
                        fontSize = 16.sp,
                        color = Color.Gray,
                        text = durationDisplayTime,
                    )
                }

            }
        }
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
            ), playerControllerState = PlayerControllerState(
                isPlaying = MutableStateFlow(false),
                currentPosition = MutableStateFlow(10.seconds.inWholeMilliseconds),
                duration = MutableStateFlow(60.seconds.inWholeMilliseconds),
                _onClickTogglePlay = {},
                _onClickSeek = {},
            ).apply {
                showCustomPlayerController()
            }
        ),
    )
}