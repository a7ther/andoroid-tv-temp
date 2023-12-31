package com.example.android_tv_temp.model.data

import androidx.navigation.NamedNavArgument

interface NavRouter {

    val route: String
        get() = this::class.java.simpleName

    val arguments: List<NamedNavArgument>
        get() = emptyList()
}