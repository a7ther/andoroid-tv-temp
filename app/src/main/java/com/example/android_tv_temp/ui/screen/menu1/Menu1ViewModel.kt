package com.example.android_tv_temp.ui.screen.menu1

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_tv_temp.model.data.Menu1ScreenUiState
import com.example.android_tv_temp.network.VideoRepository
import com.example.android_tv_temp.network.dto.NetworkProgressState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class Menu1ViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
) : ViewModel() {

    var uiState by mutableStateOf(Menu1ScreenUiState())
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