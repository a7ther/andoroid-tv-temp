package com.example.android_tv_temp.network

import com.example.android_tv_temp.network.dto.VideoCarouselResponseDto
import com.example.android_tv_temp.network.dto.VideoDetailResponseDto
import com.example.android_tv_temp.network.dto.VideoItemResponseDto
import com.example.android_tv_temp.network.dto.VideoListResponseDto
import kotlinx.coroutines.delay

class StubVideoRepositoryImpl : VideoRepository {

    companion object {

        private val IMAGE_URLS = listOf(
            "https://1.bp.blogspot.com/-grR0-27HeAk/Xo-8A7L5NXI/AAAAAAABYOc/S25Dd_VUJv4iEQfjem3b-gbvYZZu6tACACNcBGAsYHQ/s1600/bg_sakura_night.jpg",
            "https://1.bp.blogspot.com/-bthkpH7aumM/XwkxYLCim9I/AAAAAAABZ_Y/PAuyA3NYApI6IA6Uqdw2NWyrJV2MUXW-QCNcBGAsYHQ/s1600/bg_school_taiikukan.jpg",
            "https://1.bp.blogspot.com/-Pn5YVYCP5y8/XwkxXzeCAUI/AAAAAAABZ_U/_kp36Irf-mMCObO5wDP7Vwh5b8pvVDH2QCNcBGAsYHQ/s1600/bg_school_koutei.jpg",
        )

        private const val VIDEO_URL =
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    }

    override suspend fun fetchVideoList(): VideoListResponseDto {
        // 通信を想定したdelay
        delay(1_000L)
        return VideoListResponseDto(
            List(size = (1..5).random()) { index ->
                VideoCarouselResponseDto(
                    title = "Carousel $index",
                    itemList = List(size = (3..8).random()) { itemIndex ->
                        VideoItemResponseDto(
                            videoId = "videoId $index - $itemIndex",
                            title = "Title $index - $itemIndex",
                            description = "Description $index - $itemIndex",
                            thumbnailUrl = IMAGE_URLS.random(),
                        )
                    }
                )
            }
        )
    }

    override suspend fun fetchVideoDetail(videoId: String): VideoDetailResponseDto {
        // 通信を想定したdelay
        delay(200L)
        return VideoDetailResponseDto(
            title = "Title $videoId",
            videoUrl = VIDEO_URL,
        )
    }
}