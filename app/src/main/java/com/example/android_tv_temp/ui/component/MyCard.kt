package com.example.android_tv_temp.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.example.android_tv_temp.model.data.MyCardData

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MyCard(data: MyCardData) {
    Column(modifier = Modifier) {
        var isFocused by remember { mutableStateOf(false) }
        Card(
            modifier = Modifier
                .width(300.dp)
                .wrapContentSize()
                .onFocusChanged { isFocused = it.isFocused || it.hasFocus },
            scale = CardDefaults.scale(focusedScale = 1.0f),
            border = CardDefaults.border(
                focusedBorder = Border(
                    border = BorderStroke(
                        width = 2.dp, color = Color.White
                    )
                )
            ),
            colors = CardDefaults.colors(
                containerColor = Color.Transparent,
            ),
            onClick = data.onClickContent,
        ) {
            AsyncImage(
                model = data.imageUrl,
                contentDescription = data.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.77f)
                    .clip(RoundedCornerShape(10.dp))
                    .graphicsLayer { alpha = if (isFocused) 1f else 0.5f },
                placeholder = BrushPainter(
                    Brush.linearGradient(
                        listOf(
                            Color.Gray,
                            Color.DarkGray,
                        )
                    )
                ),
            )
        }

        Column(
            modifier = Modifier
                .padding(
                    vertical = 5.dp,
                )
                .width(300.dp)
        ) {
            Text(
                text = data.title,
                modifier = Modifier,
                fontSize = 20.sp,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = data.description,
                fontSize = 14.sp,
                modifier = Modifier
                    .graphicsLayer { alpha = 0.6f }
                    .padding(top = 5.dp),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
fun MyCardPreview() {
    MyCard(
        MyCardData(
            title = "title !!",
            description = "description !!",
            imageUrl = "https://xxx.com.png",
            onClickContent = {},
        )
    )
}