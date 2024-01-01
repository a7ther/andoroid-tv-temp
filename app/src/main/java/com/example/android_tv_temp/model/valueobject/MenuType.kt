package com.example.android_tv_temp.model.valueobject

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Adb
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.android_tv_temp.model.data.NavRouter

enum class MenuType(val icon: ImageVector, val label: String) : NavRouter {

    MENU1(Icons.Default.Adb, "Menu1"),
    MENU2(Icons.Default.Add, "Menu2"),
    MENU3(Icons.Default.AcUnit, "Menu3"),
    ;

    override val route: String = name
}