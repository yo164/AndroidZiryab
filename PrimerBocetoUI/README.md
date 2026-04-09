# Android Ziryab - Primer Boceto UI

Este es el proyecto cliente de la aplicación móvil para Android, desarrollado de forma nativa utilizando Kotlin.

## 🛠️ Requisitos previos

- [Android Studio](https://developer.android.com/studio) (Versión Ladybug o superior recomendada)
- Java JDK 17 o superior

## 🚀 Instalación

1. Clona el repositorio en tu máquina local.
2. Abre **Android Studio**.
3. Selecciona **Open** (o *Open an existing Android Studio project*).
4. Navega hasta el directorio `AndroidZiryab/PrimerBocetoUI` y haz clic en **OK**.
5. Espera a que Gradle sincronice y descargue las dependencias necesarias de forma automática.

## 📱 Uso (Lanzar la app)

1. Conecta un dispositivo físico Android mediante USB (con la depuración USB habilitada) o inicia un Emulador (AVD) desde el *Device Manager* de Android Studio.
2. En la barra de herramientas superior de Android Studio, asegúrate de que el módulo `app` esté seleccionado y que tu dispositivo aparezca en el menú desplegable.
3. Haz clic en el botón verde **Run (Shift + F10)** o selecciona `Run -> Run 'app'` en el menú principal.
4. La aplicación se instalará y se abrirá automáticamente en tu dispositivo/emulador.

## 📦 Construir el APK

Si necesitas compilar el ejecutable APK para distribuirlo o instalarlo manualmente:

1. Ve a `Build -> Build Bundle(s) / APK(s) -> Build APK(s)`.
2. Una vez finalizado el proceso de construcción, Android Studio mostrará un aviso en la esquina inferior derecha.
3. Haz clic en el enlace **locate** dentro de ese aviso para abrir la carpeta donde se encuentra generado tu archivo `.apk`.
