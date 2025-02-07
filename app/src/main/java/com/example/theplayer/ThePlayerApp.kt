package com.example.theplayer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Clase de aplicación base que inicializa Hilt para la inyección de dependencias.
 * Debe estar declarada en el AndroidManifest en el atributo "android:name".
 */
@HiltAndroidApp
class ThePlayerApp : Application()
