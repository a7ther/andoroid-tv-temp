package com.example.android_tv_temp.model.data

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.util.Locale
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration

data class PlayerControllerState(
    private val isPlaying: StateFlow<Boolean>,
    val currentPosition: StateFlow<Long>,
    val duration: StateFlow<Long>,
    private val _onClickTogglePlay: () -> Unit,
    private val _onClickSeek: (relativeMs: Long) -> Unit,
) {

    val currentDisplayTime: Flow<String> = currentPosition.map { formatDisplayTime(it) }
    val durationDisplayTime: Flow<String> = duration.map { formatDisplayTime(it) }

    private val _isVisibleCustomPlayerController: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isVisibleCustomPlayerController: StateFlow<Boolean> = _isVisibleCustomPlayerController.asStateFlow()

    private val _isVisiblePlayIcon: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isVisiblePlayIcon: StateFlow<Boolean> = _isVisiblePlayIcon.asStateFlow()

    private val _isVisiblePauseIcon: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isVisiblePauseIcon: StateFlow<Boolean> = _isVisiblePauseIcon.asStateFlow()

    private val autoHideControllerSeconds: MutableSharedFlow<Duration> =
        MutableSharedFlow(extraBufferCapacity = 3, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    @OptIn(FlowPreview::class)
    suspend fun observeAutoHideController() =
        autoHideControllerSeconds.debounce { it }.collect { _isVisibleCustomPlayerController.update { false } }

    fun resetFlowToInitialValue() {
        _isVisibleCustomPlayerController.update { false }
        _isVisiblePlayIcon.update { false }
        _isVisiblePauseIcon.update { false }
    }

    suspend fun onClickTogglePlay() {
        showCustomPlayerController()
        if (isPlaying.value) {
            _isVisiblePauseIcon.update { true }
        } else {
            _isVisiblePlayIcon.update { true }
        }
        _onClickTogglePlay()
        delay(100L)
        _isVisiblePauseIcon.update { false }
        _isVisiblePlayIcon.update { false }
    }

    fun onClickSeek(relativeMs: Long) {
        showCustomPlayerController()
        _onClickSeek(relativeMs)
    }

    fun hideCustomPlayerController() {
        _isVisibleCustomPlayerController.update { false }
    }

    fun showCustomPlayerController() {
        _isVisibleCustomPlayerController.update { true }
        autoHideControllerSeconds.tryEmit(4.seconds)
    }

    private fun formatDisplayTime(durationMs: Long): String {
        val duration = durationMs.toDuration(DurationUnit.MILLISECONDS)
        val minutes = duration.inWholeMinutes
        val seconds = duration.minus(minutes.toDuration(DurationUnit.MINUTES)).inWholeSeconds
        return String.format(Locale.getDefault(), FORMAT_DISPLAY_TIME, minutes, seconds)
    }

    companion object {

        private const val FORMAT_DISPLAY_TIME = "%02d:%02d"
    }

}