package com.example.android_tv_temp.ui.screen.videoplayer

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.exoplayer.ExoPlayer
import com.example.android_tv_temp.model.data.VideoPlayerScreenUiState
import com.example.android_tv_temp.network.VideoRepository
import com.example.android_tv_temp.network.dto.NetworkProgressState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val player: ExoPlayer,
    private val videoRepository: VideoRepository,
) : ViewModel() {

    @OptIn(SavedStateHandleSaveableApi::class)
    var uiState by savedStateHandle.saveable {
        mutableStateOf(VideoPlayerScreenUiState())
    }
        private set

    fun prepare(videoId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = videoRepository.fetchVideoDetail(videoId)
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(
                        networkProgressState = NetworkProgressState.Success(response)
                    )
                    val item = MediaItem.Builder()
                        .setMediaId(videoId)
                        .setUri(response.videoUrl)
                        .setMediaMetadata(
                            MediaMetadata.Builder()
                                .setTitle(response.title)
                                .build()
                        )
                        .build()
                    player.prepare()
                    player.setMediaItem(item)
                    player.play()
                }
            }
        }
    }
}