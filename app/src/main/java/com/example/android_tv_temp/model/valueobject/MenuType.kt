package com.example.android_tv_temp.model.valueobject

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Adb
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.android_tv_temp.model.data.NavRouter

sealed interface MenuType : NavRouter {

    val icon: ImageVector
    val label: String

    data object Menu1 : MenuType {

        override val icon: ImageVector = Icons.Default.Adb
        override val label: String = route
    }

    data object Menu2 : MenuType {

        override val icon: ImageVector = Icons.Default.Add
        override val label: String = route
    }

    data object Menu3 : MenuType {

        override val icon: ImageVector = Icons.Default.AcUnit
        override val label: String = route
    }

}
