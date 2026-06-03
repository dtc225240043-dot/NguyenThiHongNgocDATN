package com.example.hskmaster.components

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.platform.LocalContext
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.common.MediaItem
import androidx.media3.ui.PlayerView
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VideoPlayer(url: String) {
    val context = LocalContext.current

    if (url.isBlank()) {
        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
            Text("(Không có video)")
        }
        return
    }

    // Create player and ensure it's released when the composable is disposed
    val player = remember {
        try {
            ExoPlayer.Builder(context).build().apply {
                Log.i("VideoPlayer", "Preparing media: $url")
                val mediaItem = MediaItem.fromUri(url)
                setMediaItem(mediaItem)
                prepare()
                playWhenReady = true
            }
        } catch (e: Exception) {
            Log.e("VideoPlayer", "Failed to create player for url=$url", e)
            null
        }
    }

    DisposableEffect(key1 = player) {
        onDispose {
            try {
                player?.release()
            } catch (_: Exception) {
            }
        }
    }

    if (player == null) {
        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
            Text("Không thể phát video")
        }
        return
    }

    AndroidView(
        factory = {
            PlayerView(it).apply {
                this.player = player
                layoutParams = android.view.ViewGroup.LayoutParams(
                    MATCH_PARENT,
                    MATCH_PARENT
                )
            }
        }
    )
}