package com.example.android_tv_temp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.PivotOffsets
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.itemsIndexed
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.android_tv_temp.model.data.Menu1ScreenData
import com.example.android_tv_temp.model.data.MyCardData
import com.example.android_tv_temp.ui.component.MyCard

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun Menu1Screen(data: Menu1ScreenData) {

    TvLazyColumn(
        modifier = Modifier,
        pivotOffsets = PivotOffsets(parentFraction = 0.08f),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        itemsIndexed(data.carousels) { carouselIndex, carousel ->
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
                itemsIndexed(carousel.cards) { cardIndex, card ->
                    MyCard(data = card)
                }
            }
        }
    }

}

@Preview
@Composable
fun Menu1ScreenPreview() {
    val imageUrls = listOf(
        "https://1.bp.blogspot.com/-grR0-27HeAk/Xo-8A7L5NXI/AAAAAAABYOc/S25Dd_VUJv4iEQfjem3b-gbvYZZu6tACACNcBGAsYHQ/s1600/bg_sakura_night.jpg",
        "https://1.bp.blogspot.com/-bthkpH7aumM/XwkxYLCim9I/AAAAAAABZ_Y/PAuyA3NYApI6IA6Uqdw2NWyrJV2MUXW-QCNcBGAsYHQ/s1600/bg_school_taiikukan.jpg",
        "https://1.bp.blogspot.com/-Pn5YVYCP5y8/XwkxXzeCAUI/AAAAAAABZ_U/_kp36Irf-mMCObO5wDP7Vwh5b8pvVDH2QCNcBGAsYHQ/s1600/bg_school_koutei.jpg",
    )

    val data = Menu1ScreenData(
        carousels = List(size = 9) { index ->
            Menu1ScreenData.Carousel(
                carouselTitle = "Carousel $index",
                cards = List(size = 9) { cardIndex ->
                    MyCardData(
                        title = "Card $index - $cardIndex",
                        description = "Description",
                        imageUrl = imageUrls.random(),
                        onClickContent = {},
                    )
                }
            )
        }
    )
    Menu1Screen(data = data)
}