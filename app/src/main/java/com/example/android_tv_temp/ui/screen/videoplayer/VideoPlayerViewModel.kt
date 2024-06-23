package com.example.android_tv_temp.ui.screen.videoplayer

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.android_tv_temp.model.data.PlayerControllerState
import com.example.android_tv_temp.model.data.VideoPlayerScreenUiState
import com.example.android_tv_temp.network.VideoRepository
import com.example.android_tv_temp.network.dto.NetworkProgressState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val player: ExoPlayer,
    private val videoRepository: VideoRepository,
) : ViewModel() {

    private val _isPlaying: MutableSharedFlow<Boolean> =
        MutableSharedFlow(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    private val isPlaying: StateFlow<Boolean> = _isPlaying.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = false
    )
    private val currentPosition: MutableStateFlow<Long> = MutableStateFlow(0L)
    private val duration: MutableStateFlow<Long> = MutableStateFlow(0L)

    private val playerListener = PlayerListener()

    @OptIn(SavedStateHandleSaveableApi::class)
    var uiState by savedStateHandle.saveable {
        mutableStateOf(
            VideoPlayerScreenUiState(
                playerControllerState = PlayerControllerState(
                    isPlaying = isPlaying,
                    currentPosition = currentPosition.asStateFlow(),
                    duration = duration.asStateFlow(),
                    _onClickTogglePlay = ::onClickTogglePlay,
                    _onClickSeek = ::onClickSeek,
                )
            )
        )
    }
        private set

    init {
        player.addListener(playerListener)

        isPlaying.onEach {
            if (it.not()) return@onEach
            do {
                currentPosition.value = player.currentPosition
                delay(1_000L)
            } while (isPlaying.value)
        }.launchIn(viewModelScope)

    }

    override fun onCleared() {
        player.removeListener(playerListener)
        super.onCleared()
    }

    fun prepare(videoId: String) {
        resetFlowToInitialValue()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = videoRepository.fetchVideoDetail(videoId)
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(
                        networkProgressState = NetworkProgressState.Success(response)
                    )
                    val item = MediaItem.Builder().setMediaId(videoId).setUri(response.videoUrl).setMediaMetadata(
                        MediaMetadata.Builder().setTitle(response.title).build()
                    ).build()
                    player.prepare()
                    player.setMediaItem(item)
                    player.play()
                }
            }
        }
    }

    private fun resetFlowToInitialValue() {
        _isPlaying.tryEmit(false)
        currentPosition.value = 0L
        duration.value = 0L
        uiState.playerControllerState.resetFlowToInitialValue()
    }

    private fun onClickTogglePlay() {
        if (player.isPlaying) {
            player.pause()
        } else {
            player.play()
        }
    }

    private fun onClickSeek(relativeMs: Long) {
        val currentPosition = player.currentPosition
        val duration = player.duration
        val position = if (relativeMs > 0) {
            min(currentPosition + relativeMs, duration)
        } else {
            max(currentPosition + relativeMs, 0)
        }
        player.seekTo(position)
    }

    private inner class PlayerListener : Player.Listener {

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _isPlaying.tryEmit(isPlaying)
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            when (playbackState) {
                Player.STATE_READY -> {
                    viewModelScope.launch {
                        duration.value = player.duration
                    }
                }

                else -> {
                }
            }
        }
    }
}
