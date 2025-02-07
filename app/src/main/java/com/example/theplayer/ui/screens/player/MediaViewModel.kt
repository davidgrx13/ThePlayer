package com.example.theplayer.ui.screens.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theplayer.data.model.MediaFile
import com.example.theplayer.data.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.theplayer.data.model.MediaSource

/**
 * ViewModel encargado de gestionar la lógica de carga de archivos multimedia.
 * Utiliza MediaRepository para recuperar los archivos y expone los datos mediante StateFlow.
 *
 * @param mediaRepository Repositorio encargado de obtener los archivos multimedia.
 */
@HiltViewModel
class MediaViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
) : ViewModel() {

    /** Lista de archivos multimedia obtenidos del dispositivo. */
    private val _mediaFiles = MutableStateFlow<List<MediaFile>>(emptyList())
    val mediaFiles: StateFlow<List<MediaFile>> = _mediaFiles.asStateFlow()

    /** Fuente de datos utilizada para cargar los archivos multimedia. */
    private val _source = MutableStateFlow(MediaSource.DEVICE)

    init {
        setSource(MediaSource.DEVICE)
    }

    /**
     * Establece la fuente de datos y carga los archivos correspondientes.
     *
     * @param source Fuente de datos (DEVICE para archivos del almacenamiento interno).
     */
    fun setSource(source: MediaSource) {
        _source.value = source
        when (source) {
            MediaSource.DEVICE -> loadMediaFilesFromDevice()
            MediaSource.RAW -> TODO() // No hace falta
        }
    }

    /**
     * Carga los archivos multimedia almacenados en el dispositivo y actualiza el StateFlow.
     */
    fun loadMediaFilesFromDevice() {
        viewModelScope.launch {
            _mediaFiles.value = mediaRepository.getMediaFilesFromMediaStore()
        }
    }
}
