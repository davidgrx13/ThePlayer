package com.example.theplayer.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.theplayer.ui.screens.player.MediaPlayer
import com.example.theplayer.ui.screens.home.Home
import com.example.theplayer.ui.screens.player.MediaViewModel

/**
 * Configura la navegación de la aplicación utilizando Jetpack Navigation.
 * Permite la transición entre la pantalla de inicio (`Home`) y el reproductor multimedia (`MediaPlayer`).
 *
 * @param viewModel Instancia de `MediaViewModel` compartida entre las pantallas.
 */
@Composable
fun AppNavigation(viewModel: MediaViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        /**
         * Pantalla principal donde se muestra la lista de archivos multimedia.
         */
        composable("home") {
            Home(navController, viewModel)
        }

        /**
         * Pantalla del reproductor multimedia.
         * Recibe la URI y el título del archivo como parámetros de navegación.
         */
        composable("mediaplayer/{uri}/{title}") { backStackEntry ->
            val uri = Uri.decode(backStackEntry.arguments?.getString("uri") ?: "")
            val title = backStackEntry.arguments?.getString("title") ?: "No title"
            MediaPlayer(uri, title, navController)
        }
    }
}
