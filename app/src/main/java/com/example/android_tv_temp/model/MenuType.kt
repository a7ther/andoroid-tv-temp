package com.example.android_tv_temp.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Adb
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface MenuType {

    val icon: ImageVector
    val label: String

    data object Menu1 : MenuType {

        override val icon: ImageVector = Icons.Default.Adb
        override val label: String = this::class.java.simpleName
    }

    data object Menu2 : MenuType {

        override val icon: ImageVector = Icons.Default.Add
        override val label: String = this::class.java.simpleName

    }

    data object Menu3 : MenuType {

        override val icon: ImageVector = Icons.Default.AcUnit
        override val label: String = this::class.java.simpleName
        
    }

}
