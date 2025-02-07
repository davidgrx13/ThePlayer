package com.example.theplayer.ui.utils

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.theplayer.R

/**
 * Composable que solicita permisos de lectura de medios al usuario.
 * Muestra un `Toast` si el permiso es denegado.
 */
@Composable
fun RequestPermissionEffect() {
    val context = LocalContext.current
    val permissionDeniedMessage = stringResource(R.string.permission_required)

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, permissionDeniedMessage, Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(Unit) {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_VIDEO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        permissionLauncher.launch(permission)
    }
}
