package com.example.android_tv_temp.network.dto

sealed interface NetworkProgressState {

    data object Loading : NetworkProgressState
    data class Success<out T : ResponseDto.Success>(val response: T) : NetworkProgressState
    data class Error<out T : ResponseDto.Error>(val error: T) : NetworkProgressState

    sealed interface ResponseDto {
        interface Success : ResponseDto
        interface Error : ResponseDto
    }
}