package com.example.theplayer.ui.utils

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

/**
 * Administra la reproducción de archivos multimedia mediante ExoPlayer.
 * Se encarga de preparar, reproducir y liberar los recursos del reproductor.
 *
 * @param context Contexto de la aplicación necesario para inicializar ExoPlayer.
 */
class PlayerManager(context: Context) {

    /** Instancia de ExoPlayer utilizada para la reproducción de medios. */
    private val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()

    /**
     * Prepara y comienza la reproducción de un archivo multimedia.
     *
     * @param uri URI del archivo multimedia a reproducir.
     */
    fun prepareAndPlay(uri: Uri) {
        val mediaItem = MediaItem.fromUri(uri)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    /**
     * Libera los recursos del reproductor cuando ya no se necesiten.
     */
    fun release() {
        exoPlayer.release()
    }

    /**
     * Retorna la instancia actual de ExoPlayer.
     *
     * @return Instancia de ExoPlayer.
     */
    fun getPlayer(): ExoPlayer = exoPlayer
}
