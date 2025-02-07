package com.example.theplayer.data.model

/**
 * Define las posibles fuentes desde donde se pueden cargar los archivos multimedia.
 *
 * @property DEVICE Indica que los archivos provienen del almacenamiento del dispositivo.
 * @property RAW Indica que los archivos provienen de la carpeta `res/raw` de la aplicación.
 */
enum class MediaSource {
    DEVICE, RAW
}
