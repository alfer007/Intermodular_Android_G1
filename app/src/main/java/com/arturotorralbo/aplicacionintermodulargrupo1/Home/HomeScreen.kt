package com.arturotorralbo.aplicacionintermodulargrupo1.Home

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController

@OptIn(UnstableApi::class)
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val exoPlayer = ExoPlayer.Builder(context)
        .build().apply {
            val mediaItem = MediaItem.fromUri("https://i.imgur.com/KQCZPAU.mp4")
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            repeatMode = ExoPlayer.REPEAT_MODE_ALL
        }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    keepScreenOn = true
                    setUseController(false)
                    setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM)
                    post {
                        translationX = -width / 4f
                    }
                }
            },
            update = { playerView ->
                playerView.player = exoPlayer
                playerView.post {
                    playerView.translationX = -playerView.width / 4f
                }
            }
        )
        DisposableEffect(Unit) {
            onDispose {
                exoPlayer.release()
            }
        }
    }
}
