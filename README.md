# MISO-AppMoviles-Vinilos

Esta aplicación consiste en un flujo de vistas que muestran los álbumes y los comentarios que coleccionistas han hecho sobre estos. La aplicación permite a los usuarios ver los álbumes disponibles, ver los detalles de un álbum en particular y agregar comentarios a los álbumes. La aplicación también permite a los usuarios ver los comentarios que otros coleccionistas han hecho sobre los álbumes.

## APK - Iteración 1

[APK-vinilos](https://drive.google.com/file/d/1LQ576A_pfC64xrb9sUgMTngV1a09kQ5N/view?usp=drive_link)

## Instrucciones para la ejecución del proyecto

### Requisitos previos

Para poder ejecutar el proyecto es necesario tener instalado en el equipo las siguientes herramientas:

Android Studio: https://developer.android.com/studio

<p>
Version utilizada durante el desarrollo: Android Studio Iguana | 2023.2.1 Patch 2 <br>
Runtime version: 17.0.9+0--11185874 amd64 <br>
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o. <br>
Windows 11.0 <br>
</p>

Android SDK: https://developer.android.com/studio
minSdk = 21
targetSdk = 34

### Ejecución del proyecto
Clonar el repositorio en su equipo local:
```bash
git clone https://github.com/ramirez-alejo/MISO-AppMoviles-Vinilos.git
```
  1. Abrir el repositorio en Android Studio  
  2. Instalar 1 dispositivos con API 21, 22 o 34.
  3. Seleccione el dispositivo en el que desea ejecutar la aplicación.
  4. Seleccione App en la barra de herramientas de Android Studio.
  5. Click en Run o en Debug.

### Ejecución de pruebas
  1. Abrir el repositorio en Android Studio  
  2. Instalar 3 dispositivos. Uno con API 21, otro con API 22 y uno último con API 34.
  3. Click derecho en la carpeta androidTests en el proyecto. Click izquierdo en donde dicer runAllTests. (segunda imágen)
  4. Hacer click a la izquierda de app, donde salen los dispositivos. Ahí seleccionar el botón para seleccionar múltiples dispositivos y escoger los 3 recién instalados. (Primera imágen adjunta)
  5. Los tests deberían estar corriendo como se ve en la imágen. (tercera imágen)

Nota: Si no se corran en los tres dispositivos hay que parar la ejecución de las pruebas y repetir el punto 4.

![alt text](<gradle/Images/image (8).png>)
![alt text](<gradle/Images/image (9).png>)
![alt text](<gradle/Images/image (10).png>)
