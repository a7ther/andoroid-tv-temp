package com.example.android_tv_temp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.Card
import androidx.tv.material3.DrawerValue
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.NavigationDrawer
import androidx.tv.material3.Text
import com.example.android_tv_temp.model.MenuType

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MyNavigationDrawer() {
    val selectedMenuType: MutableState<MenuType> = remember { mutableStateOf(MenuType.Menu1) }
    val menuTypeList = listOf(
        MenuType.Menu1,
        MenuType.Menu2,
        MenuType.Menu3,
    )
    val navController = rememberNavController()

    LaunchedEffect(selectedMenuType.value) {
        navController.navigate(selectedMenuType.value.label)
    }

    Row(Modifier.fillMaxSize()) {
        Box(modifier = Modifier) {
            NavigationDrawer(
                drawerContent = { drawerValue ->
                    Sidebar(
                        drawerValue = drawerValue,
                        selectedMenuType = selectedMenuType,
                        menuTypeList = menuTypeList,
                    )
                }
            ) {
                MyContent(
                    navController = navController,
                    startScreenName = menuTypeList.first().label,
                )
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun MyContent(
    navController: NavHostController,
    startScreenName: String,
) {
    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = startScreenName,
    ) {
        composable(MenuType.Menu1.label) {
            Card(
                onClick = { },
            ) {
                Text(
                    text = MenuType.Menu1.label,
                    modifier = Modifier.padding(10.dp),
                )
            }
        }
        composable(MenuType.Menu2.label) {
            Card(
                onClick = { },
            ) {
                Text(
                    text = MenuType.Menu2.label,
                    modifier = Modifier.padding(20.dp),
                )
            }
        }
        composable(MenuType.Menu3.label) {
            Card(
                onClick = { },
            ) {
                Text(
                    text = MenuType.Menu3.label,
                    modifier = Modifier.padding(30.dp),
                )
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun Sidebar(
    drawerValue: DrawerValue,
    selectedMenuType: MutableState<MenuType>,
    menuTypeList: List<MenuType>,
) {
    val selectedIndex = remember { mutableIntStateOf(0) }

    LaunchedEffect(selectedIndex.intValue) {
        selectedMenuType.value = menuTypeList[selectedIndex.intValue]
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.Transparent)
            .selectableGroup(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        menuTypeList.forEachIndexed { index, menuType ->
            NavigationItem(
                iconImageVector = menuType.icon,
                text = menuType.label,
                drawerValue = drawerValue,
                focusedIndex = selectedIndex,
                index = index
            )
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun NavigationItem(
    iconImageVector: ImageVector,
    text: String,
    drawerValue: DrawerValue,
    focusedIndex: MutableState<Int>,
    index: Int,
    modifier: Modifier = Modifier,
) {
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .onFocusChanged {
                isFocused = it.isFocused
                if (it.isFocused) {
                    focusedIndex.value = index
                }
            }
            .background(if (isFocused) Color.White else Color.Transparent)
            .semantics(mergeDescendants = true) {
                selected = focusedIndex.value == index
            }
            .clickable {
                focusedIndex.value = index
            }
    ) {
        Box(modifier = Modifier.padding(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                Icon(
                    imageVector = iconImageVector,
                    tint = if (isFocused) Color.Gray else Color.White,
                    contentDescription = null,
                )
                AnimatedVisibility(visible = drawerValue == DrawerValue.Open) {
                    Text(
                        text = text,
                        modifier = Modifier,
                        softWrap = false,
                        color = if (isFocused) Color.Gray else Color.White,
                    )
                }
            }
        }
    }
}