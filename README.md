==========================================
 ThePlayer - Reproductor Multimedia
==========================================

ThePlayer es una aplicación de reproducción de medios que permite visualizar videos y reproducir audios desde el almacenamiento del dispositivo.
Está desarrollada en Kotlin con Jetpack Compose, siguiendo el patrón MVVM, e integra ExoPlayer para una experiencia de reproducción fluida.

------------------------------------------
 FUNCIONALIDADES
------------------------------------------
- Reproducción de videos y audios desde el almacenamiento del dispositivo  
- Interfaz con Jetpack Compose  
- Controles personalizados para la reproducción de audio  
- Barra de progreso interactiva para avanzar o retroceder en la reproducción  
- Manejo de permisos en Android 13+  
- Diseño adaptable con Material 3  

------------------------------------------
 TECNOLOGÍAS UTILIZADAS
------------------------------------------
- Kotlin
- Jetpack Compose
- ExoPlayer - Reproductor multimedia  
- Model-View-ViewModel (MVVM)
- Dagger Hilt - Inyección de dependencias eficiente  (MVVM)
- 2️⃣ API - Acceso a archivos multimedia del dispositivo  

------------------------------------------
ESTRUCTURA DEL PROYECTO
------------------------------------------
📦 ThePlayer  
 ┣ 📂 data                        -> Modelos de datos y repositorio multimedia  
 ┣ 📂 di                            -> Configuración de inyección de dependencias con Hilt  
 ┣ 📂 ui                            -> Screens, navegación y temas  
 ┃ ┣ 📂 screens              -> Vistas de Home y Reproductor  
 ┃ ┣ 📂 navigation          -> Configuración de rutas y navegación  
 ┃ ┗ 📂 utils             	-> Clases auxiliares como el manager de permisos y ExoPlayer  
 ┣ 📜 README.md          -> Documentación  

------------------------------------------
 CONFIGURACIÓN Y EJECUCIÓN
------------------------------------------
1️⃣ Clonar el repositorio  
   git clone https://github.com/davidgrx13/ThePlayer.git

2️⃣ Abrir el proyecto en Android Studio  
   - Usar Android Studio Flamingo o superior  

3️⃣ Almacenar algún archivo de audio o video en el emulador o dispositivo

4️⃣ Ejecutar la aplicación  

------------------------------------------
 AUTOR
------------------------------------------
Nombre: David García  
GitHub: davidgrx13
