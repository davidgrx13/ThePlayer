package com.example.theplayer.ui.screens.home

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.theplayer.ui.screens.player.MediaViewModel
import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.theplayer.R
import com.example.theplayer.ui.theme.*

/**
 * Pantalla principal de la aplicación donde se muestra la lista de archivos multimedia.
 * Permite la navegación al reproductor multimedia al seleccionar un archivo.
 *
 * @param navController Controlador de navegación para gestionar las transiciones entre pantallas.
 * @param viewModel Instancia de MediaViewModel para obtener la lista de archivos multimedia.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController, viewModel: MediaViewModel = hiltViewModel()) {
    val mediaFiles by viewModel.mediaFiles.collectAsState(initial = emptyList())

    // Verificar y solicitar permisos según la versión de Android
    val requiredPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        listOf(Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.READ_MEDIA_VIDEO)
    } else {
        listOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    val permissionsGranted = remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissionsGranted.value = permissions.all { it.value }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(requiredPermissions.toTypedArray())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.library),
                        color = SecondAccent,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundColor
                )
            )
        },
        containerColor = BackgroundColor
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
        ) {
            items(mediaFiles) { media ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            val encodedUri = Uri.encode(media.uri.toString())
                            navController.navigate("mediaplayer/$encodedUri/${media.title}")
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = BackgroundColor
                    )
                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            // Título del archivo
                            Text(text = media.title, style = MaterialTheme.typography.titleMedium, color = Color.White)
                            // Duración del archivo multimedia
                            Text(
                                text = if (media.duration > 0) {
                                    stringResource(R.string.duration_format, media.duration / 60000, (media.duration % 60000) / 1000)
                                } else {
                                    stringResource(R.string.audio_file)
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}
