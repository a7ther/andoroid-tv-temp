package com.example.android_tv_temp.ui.screen

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.tv.material3.DrawerValue
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.NavigationDrawer
import androidx.tv.material3.Text
import com.example.android_tv_temp.model.valueobject.MenuType
import com.example.android_tv_temp.ui.screen.menu1.Menu1Screen
import com.example.android_tv_temp.ui.screen.menu1.Menu1ViewModel

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MyNavigationDrawer(
    navController: NavHostController,
    menu1ViewModel: Menu1ViewModel = hiltViewModel(),
) {
    val selectedMenuType: MutableState<MenuType> = remember { mutableStateOf(MenuType.MENU1) }
    val menuTypeList = MenuType.entries

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
                when (selectedMenuType.value) {
                    MenuType.MENU1 -> Menu1Screen(navController, menu1ViewModel.uiState)
                    MenuType.MENU2 -> Menu1Screen(navController, menu1ViewModel.uiState)
                    MenuType.MENU3 -> Menu1Screen(navController, menu1ViewModel.uiState)
                }
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
    val focusRequesters = remember { List(size = menuTypeList.size) { FocusRequester() } }

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
                selectedIndex = selectedIndex,
                focusRequesters = focusRequesters,
                index = index,
                modifier = Modifier.focusRequester(focusRequesters[index]),
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
    selectedIndex: MutableState<Int>,
    focusRequesters: List<FocusRequester>,
    index: Int,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .onFocusChanged {
                if (it.isFocused.not()) return@onFocusChanged
                when (drawerValue) {
                    DrawerValue.Open -> selectedIndex.value = index
                    DrawerValue.Closed -> focusRequesters[selectedIndex.value].requestFocus()
                }
            }
            .background(if (selectedIndex.value == index) Color.Black else Color.Transparent)
            .clickable {
                selectedIndex.value = index
                focusManager.moveFocus(FocusDirection.Right)
            }
    ) {
        Box(modifier = Modifier.padding(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                Icon(
                    imageVector = iconImageVector,
                    tint = Color.White,
                    contentDescription = null,
                )
                AnimatedVisibility(visible = drawerValue == DrawerValue.Open) {
                    Text(
                        text = text,
                        modifier = Modifier,
                        softWrap = false,
                        color = Color.White,
                    )
                }
            }
        }
    }
}