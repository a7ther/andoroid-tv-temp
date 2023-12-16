package com.example.android_tv_temp.model.data

data class MyCardData(
    val title: String,
    val description: String,
    val imageUrl: String,
    val onClickContent: () -> Unit,
)
