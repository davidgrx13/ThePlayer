package com.example.theplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.theplayer.ui.navigation.AppNavigation
import com.example.theplayer.ui.screens.player.MediaViewModel
import com.example.theplayer.ui.utils.RequestPermissionEffect
import dagger.hilt.android.AndroidEntryPoint

/**
 * Actividad principal de la aplicación. Gestiona la inicialización de la interfaz y la navegación.
 * Utiliza Hilt para la inyección de dependencias.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /** ViewModel compartido en toda la aplicación para gestionar los archivos multimedia. */
    private val viewModel: MediaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Solicita los permisos de lectura de medios
            RequestPermissionEffect()

            // Configura e inicialiaza la navegación de la aplicación
            AppNavigation(viewModel)
        }
    }
}
