package com.example.android_tv_temp.ui.screen.menu1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.tv.foundation.PivotOffsets
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.itemsIndexed
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.android_tv_temp.model.data.Menu1ScreenUiState
import com.example.android_tv_temp.model.valueobject.ScreenType
import com.example.android_tv_temp.network.dto.NetworkProgressState
import com.example.android_tv_temp.network.dto.VideoCarouselResponseDto
import com.example.android_tv_temp.network.dto.VideoItemResponseDto
import com.example.android_tv_temp.network.dto.VideoListResponseDto
import com.example.android_tv_temp.ui.component.MyCard

@Composable
fun Menu1Screen(
    navController: NavHostController,
    uiState: Menu1ScreenUiState,
) {

    val focusedVideoId = rememberSaveable { mutableStateOf<String?>(null) }
    val focusRequesterMap = remember { mutableMapOf<String, FocusRequester>() }

    TvLazyColumn(
        modifier = Modifier,
        pivotOffsets = PivotOffsets(parentFraction = 0.08f),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        itemsIndexed(uiState.carousels) { _, carousel ->
            Text(
                text = carousel.carouselTitle,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier,
                color = Color.White,
            )

            TvLazyRow(
                modifier = Modifier,
                pivotOffsets = PivotOffsets(parentFraction = 0.0f),
                contentPadding = PaddingValues(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                itemsIndexed(carousel.cards) { _, card ->
                    val focusRequester = remember { FocusRequester() }
                    focusRequesterMap[card.videoId] = focusRequester
                    MyCard(
                        data = card,
                        onClick = {
                            navController.navigate(ScreenType.VideoPlayerScreen.createTransitionRoute(card.videoId))
                        },
                        focusedVideoId = focusedVideoId,
                        focusRequester = focusRequester,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    LaunchedEffect(Unit) {
        focusRequesterMap[focusedVideoId.value]?.requestFocus()
    }

}

@Preview(device = Devices.TV_720p)
@Composable
fun Preview_Menu1Screen() {
    val imageUrls = listOf(
        "https://1.bp.blogspot.com/-grR0-27HeAk/Xo-8A7L5NXI/AAAAAAABYOc/S25Dd_VUJv4iEQfjem3b-gbvYZZu6tACACNcBGAsYHQ/s1600/bg_sakura_night.jpg",
        "https://1.bp.blogspot.com/-bthkpH7aumM/XwkxYLCim9I/AAAAAAABZ_Y/PAuyA3NYApI6IA6Uqdw2NWyrJV2MUXW-QCNcBGAsYHQ/s1600/bg_school_taiikukan.jpg",
        "https://1.bp.blogspot.com/-Pn5YVYCP5y8/XwkxXzeCAUI/AAAAAAABZ_U/_kp36Irf-mMCObO5wDP7Vwh5b8pvVDH2QCNcBGAsYHQ/s1600/bg_school_koutei.jpg",
    )

    val videoListResponseDto = VideoListResponseDto(
        List(size = 9) { index ->
            VideoCarouselResponseDto(
                title = "Carousel $index",
                itemList = List(size = 6) { itemIndex ->
                    VideoItemResponseDto(
                        videoId = "videoId $index - $itemIndex",
                        title = "Title $index - $itemIndex",
                        description = "Description $index - $itemIndex",
                        thumbnailUrl = imageUrls.random(),
                    )
                }
            )
        }
    )

    val uiState = Menu1ScreenUiState(
        networkProgressState = NetworkProgressState.Success(videoListResponseDto),
    )

    Menu1Screen(
        navController = rememberNavController(),
        uiState = uiState,
    )
}