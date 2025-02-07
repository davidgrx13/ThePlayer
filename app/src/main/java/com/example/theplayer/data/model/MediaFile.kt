package com.example.theplayer.data.model

import android.net.Uri

/**
 * Representa un archivo multimedia (audio o video) almacenado en el dispositivo.
 * Contiene la información básica necesaria para mostrarlo y reproducirlo.
 *
 * @property uri URI del archivo multimedia.
 * @property title Nombre o título del archivo.
 * @property duration Duración del archivo en milisegundos.
 */
data class MediaFile(
    val uri: Uri,
    val title: String,
    val duration: Long,
)
