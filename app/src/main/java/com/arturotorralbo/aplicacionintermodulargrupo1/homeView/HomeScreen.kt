package com.arturotorralbo.aplicacionintermodulargrupo1.homeView

import android.content.Context
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.arturotorralbo.aplicacionintermodulargrupo1.core.navigation.Login
import com.arturotorralbo.aplicacionintermodulargrupo1.core.navigation.Search
import com.arturotorralbo.aplicacionintermodulargrupo1.core.utils.TokenManager
import com.arturotorralbo.aplicacionintermodulargrupo1.homeView.components.HeaderBar
import com.arturotorralbo.aplicacionintermodulargrupo1.ui.theme.primaryColorBlue

@OptIn(UnstableApi::class)
@Composable
fun HomeScreen(navController: NavController, tokenManager: TokenManager = TokenManager(LocalContext.current) ) {
    val context = LocalContext.current
    val exoPlayer = remember { createExoPlayer(context) }

    LaunchedEffect(Unit) {
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
        exoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_ALL
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    keepScreenOn = true
                    setUseController(false)
                    setResizeMode (AspectRatioFrameLayout.RESIZE_MODE_ZOOM)
                    post {
                        translationX = -width / 4f
                    }
                }
            }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.TopCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White.copy(alpha = 0.9f), Color.Transparent)
                    )
                )
        )

        Spacer(modifier = Modifier.height(80.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.White.copy(alpha = 0.9f))
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            HeaderBar(navController, context)

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Gran Hotel Pere María",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Benidorm 5 estrellas",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Button(
                onClick = {
                    val token = tokenManager.getToken()
                    if (token != null) {
                        navController.navigate(Search)
                    } else {
                        navController.navigate(Login)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryColorBlue)
            ) {
                Text(text = "Reservar", color = Color.White, style = MaterialTheme.typography.titleMedium)
            }
        }


        DisposableEffect(Unit) {
            onDispose {
                exoPlayer.release()
            }
        }
    }
}

private fun createExoPlayer(context: Context): ExoPlayer {
    return ExoPlayer.Builder(context).build().apply {
        val mediaItem = MediaItem.fromUri("http://10.0.2.2:3000/images/HomeVideo.mp4")
        setMediaItem(mediaItem)
    }
}

