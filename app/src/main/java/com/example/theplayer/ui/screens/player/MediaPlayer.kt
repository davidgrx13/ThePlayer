package com.example.theplayer.ui.screens.player

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import com.example.theplayer.ui.utils.PlayerManager
import com.example.theplayer.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Pantalla del reproductor multimedia que permite la reproducción de audio y video.
 * Dependiendo del tipo de archivo, muestra un reproductor de video o controles personalizados para audio.
 *
 * @param uri URI del archivo multimedia a reproducir.
 * @param title Título del archivo multimedia.
 * @param navController Controlador de navegación para gestionar la vuelta a la pantalla anterior.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaPlayer(
    uri: String,
    title: String,
    navController: NavController,
) {
    val context = LocalContext.current
    val playerManager = remember { PlayerManager(context) }
    val isPlaying = remember { mutableStateOf(false) }
    val progress = remember { mutableStateOf(0L) }
    val duration = remember { mutableStateOf(100000L) } // Valor por defecto
    val coroutineScope = rememberCoroutineScope()

    val isAudio = remember { !uri.contains("video") }

    DisposableEffect(Unit) {
        playerManager.prepareAndPlay(Uri.parse(uri))
        isPlaying.value = true

        val player = playerManager.getPlayer()
        val validDuration = player.duration.takeIf { it > 0 } ?: 100000L
        duration.value = validDuration

        coroutineScope.launch {
            while (true) {
                if (isPlaying.value) {
                    progress.value = player.currentPosition
                }
                delay(10L) // Actualizar cada 10ms
            }
        }

        onDispose { playerManager.release() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "",
                            tint = SecondAccent
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundColor)
            )

        },
        containerColor = BackgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundColor)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título del archivo
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            if (isAudio) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Tiempo reproducido a la izquierda del slider
                        Text(
                            fontSize = 14.sp,
                            text = formatTime(progress.value),
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall
                        )

                        Slider(
                            value = progress.value.toFloat(),
                            onValueChange = { newPosition ->
                                playerManager.getPlayer().seekTo(newPosition.toLong())
                                progress.value = newPosition.toLong()
                            },
                            valueRange = 0f..duration.value.toFloat(),
                            modifier = Modifier.weight(1f),
                            colors = SliderDefaults.colors(
                                thumbColor = SecondAccent,
                                activeTrackColor = SecondAccent,
                                inactiveTrackColor = SecondAccent.copy(alpha = 0.5f)
                            )
                        )

                        // Duración total a la derecha del slider
                        Text(
                            fontSize = 14.sp,
                            text = formatTime(duration.value),
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall

                        )
                    }

                    IconButton(onClick = {
                        if (isPlaying.value) {
                            playerManager.getPlayer().pause()
                        } else {
                            playerManager.getPlayer().play()
                        }
                        isPlaying.value = !isPlaying.value
                    }) {
                        Icon(
                            Icons.Default.PlayArrow,
                            contentDescription = "",
                            modifier = Modifier.size(64.dp),
                            tint = Color.White
                        )
                    }
                }
            } else {
                AndroidView(
                    factory = { ctx ->
                        PlayerView(ctx).apply {
                            player = playerManager.getPlayer()
                            useController = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(300.dp)
                )
            }
        }
    }
}

/**
 * Convierte un tiempo en milisegundos a formato min:seg.
 *
 * @param ms Tiempo en milisegundos.
 * @return Cadena formateada en minutos:segundos.
 */
@Composable
fun formatTime(ms: Long): String {
    val minutes = (ms / 60000).toInt()
    val seconds = ((ms % 60000) / 1000).toInt()
    return String.format("%d:%02d", minutes, seconds)
}
