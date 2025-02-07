package com.example.theplayer.di

import android.content.Context
import com.example.theplayer.data.repository.MediaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo de dependencias de Dagger Hilt para la inyección de dependencias en la aplicación.
 * Proporciona instancias de clases que necesitan ser inyectadas en otros componentes.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Proporciona una instancia de `MediaRepository` como una dependencia inyectable en toda la aplicación.
     *
     * @param context Contexto de la aplicación obtenido mediante @ApplicationContext.
     * @return Una instancia de MediaRepository.
     */
    @Provides
    @Singleton
    fun provideMediaRepository(@ApplicationContext context: Context): MediaRepository {
        return MediaRepository(context)
    }
}
