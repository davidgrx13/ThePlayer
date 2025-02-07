package com.example.theplayer.data.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.example.theplayer.data.model.MediaFile

/**
 * Repositorio encargado de obtener archivos multimedia (videos y audios) desde el almacenamiento del dispositivo.
 *
 * @param context Contexto de la aplicación, utilizado para acceder a ContentResolver.
 */

class MediaRepository(private val context: Context) {

    /**
     * Recupera la lista de archivos multimedia disponibles en el dispositivo (videos y audios).
     *
     * @return Lista de objetos `MediaFile` con la información de los archivos encontrados.
     */
    fun getMediaFilesFromMediaStore(): List<MediaFile> {
        val mediaFiles = mutableListOf<MediaFile>()

        // Archivos de VIDEO
        mediaFiles.addAll(queryMediaFiles(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DURATION
            )
        ))

        // Archivos de AUDIO
        mediaFiles.addAll(queryMediaFiles(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION
            )
        ))

        return mediaFiles
    }

    /**
     * Consulta archivos multimedia en MediaStore y los devuelve en una lista.
     *
     * @param uri URI del contenido a consultar (puede ser `MediaStore.Video.Media.EXTERNAL_CONTENT_URI`
     * o `MediaStore.Audio.Media.EXTERNAL_CONTENT_URI`).
     * @param projection Array con los nombres de las columnas a recuperar de MediaStore.
     * @return Lista de objetos `MediaFile` con los datos obtenidos.
     */
    private fun queryMediaFiles(uri: Uri, projection: Array<String>): List<MediaFile> {
        val mediaFiles = mutableListOf<MediaFile>()
        val contentResolver: ContentResolver = context.contentResolver

        contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            val idColumn = cursor.getColumnIndex(projection[0])
            val titleColumn = cursor.getColumnIndex(projection[1])
            val durationColumn = cursor.getColumnIndex(projection[2])

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val duration = cursor.getLong(durationColumn)

                val fileUri = Uri.withAppendedPath(uri, id.toString())
                mediaFiles.add(MediaFile(fileUri, title, duration))
            }
        }

        return mediaFiles
    }
}
