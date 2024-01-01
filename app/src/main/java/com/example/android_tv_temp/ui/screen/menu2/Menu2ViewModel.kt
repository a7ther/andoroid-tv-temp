package com.example.android_tv_temp.ui.screen.menu2

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.example.android_tv_temp.model.data.Menu1ScreenUiState
import com.example.android_tv_temp.network.VideoRepository
import com.example.android_tv_temp.network.dto.NetworkProgressState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class Menu2ViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val videoRepository: VideoRepository,
) : ViewModel() {

    @OptIn(SavedStateHandleSaveableApi::class)
    var uiState by savedStateHandle.saveable {
        mutableStateOf(Menu1ScreenUiState())
    }
        private set

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = videoRepository.fetchVideoList()
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(
                        networkProgressState = NetworkProgressState.Success(response)
                    )
                }
            }
        }
    }
}