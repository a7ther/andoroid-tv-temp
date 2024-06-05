package com.example.android_tv_temp.ui.screen

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.tv.material3.DrawerValue
import androidx.tv.material3.Icon
import androidx.tv.material3.NavigationDrawer
import androidx.tv.material3.NavigationDrawerItem
import androidx.tv.material3.NavigationDrawerItemDefaults
import androidx.tv.material3.NavigationDrawerScope
import androidx.tv.material3.Text
import com.example.android_tv_temp.model.valueobject.MenuType
import com.example.android_tv_temp.ui.screen.menu1.Menu1Screen
import com.example.android_tv_temp.ui.screen.menu1.Menu1ViewModel
import com.example.android_tv_temp.ui.screen.menu2.Menu2Screen
import com.example.android_tv_temp.ui.screen.menu2.Menu2ViewModel
import com.example.android_tv_temp.ui.screen.menu3.Menu3Screen
import com.example.android_tv_temp.ui.screen.menu3.Menu3ViewModel

@Composable
fun MyNavigationDrawer(
    navController: NavHostController,
    menu1ViewModel: Menu1ViewModel = hiltViewModel(),
    menu2ViewModel: Menu2ViewModel = hiltViewModel(),
    menu3ViewModel: Menu3ViewModel = hiltViewModel(),
) {
    val selectedMenuType: MutableState<MenuType> = rememberSaveable { mutableStateOf(MenuType.MENU1) }
    val menuTypeList = MenuType.entries

    Row(Modifier.fillMaxSize()) {
        Box(modifier = Modifier) {
            NavigationDrawer(
                drawerContent = { drawerValue ->
                    NavigationDrawerItems(
                        drawerValue = drawerValue,
                        selectedMenuType = selectedMenuType,
                        menuTypeList = menuTypeList,
                    )
                }
            ) {
                when (selectedMenuType.value) {
                    MenuType.MENU1 -> Menu1Screen(navController, menu1ViewModel.uiState)
                    MenuType.MENU2 -> Menu2Screen(navController, menu2ViewModel.uiState)
                    MenuType.MENU3 -> Menu3Screen(navController, menu3ViewModel.uiState)
                }
            }
        }
    }
}

@Composable
private fun NavigationDrawerScope.NavigationDrawerItems(
    drawerValue: DrawerValue,
    selectedMenuType: MutableState<MenuType>,
    menuTypeList: List<MenuType>,
) {
    val selectedIndex = rememberSaveable { mutableIntStateOf(0) }
    val focusRequesters = remember {
        List(size = menuTypeList.size) {
            FocusRequester()
        }
    }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(selectedIndex.intValue) {
        selectedMenuType.value = menuTypeList[selectedIndex.intValue]
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .onFocusChanged {
                if (it.isFocused.not()) return@onFocusChanged
                if (drawerValue != DrawerValue.Closed) return@onFocusChanged
                focusRequesters[selectedIndex.intValue].requestFocus()
            }
            .focusable(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        menuTypeList.forEachIndexed { index, menuType ->
            NavigationDrawerItem(
                modifier = Modifier
                    .focusRequester(focusRequesters[index])
                    .onFocusChanged {
                        if (it.isFocused.not()) return@onFocusChanged
                        if (drawerValue != DrawerValue.Open) return@onFocusChanged
                        selectedIndex.intValue = index
                    },
                colors = NavigationDrawerItemDefaults.colors(
                    contentColor = Color.LightGray,
                    inactiveContentColor = Color.LightGray,
                    focusedContainerColor = Color.White,
                    selectedContainerColor = Color.White,
                ),
                selected = selectedIndex.intValue == index,
                onClick = {
                    selectedIndex.intValue = index
                    focusManager.moveFocus(FocusDirection.Right)
                },
                leadingContent = {
                    Icon(
                        imageVector = menuType.icon,
                        contentDescription = null,
                    )
                },
            ) {
                Text(menuType.label)
            }
        }

    }
}
