# AppMovil: Marketplace de Software de IA

## Descripción General

AppMovil es una aplicación móvil para Android que funciona como un marketplace especializado en soluciones de software de inteligencia artificial (IA). Los usuarios pueden registrarse, iniciar sesión, explorar y buscar software, agregar nuevas soluciones, configurar su perfil y la interfaz de usuario, así como interactuar con un chatbot integrado para soporte y consultas.

---

## Funcionalidades Principales

### 1. Registro e Inicio de Sesión
- Permite a los usuarios crear una cuenta nueva o ingresar a su cuenta existente.
- Usa Firebase Authentication para gestión segura de usuarios.
- Mantiene sesión iniciada y preferencias con SharedPreferences.

### 2. Buscar Software
- Busca soluciones de software IA dentro del marketplace.
- Usa RecyclerView para mostrar listas dinámicas.
- Consulta datos en Firestore con ViewModel y LiveData.
- Filtra resultados en tiempo real.

### 3. Agregar Software
- Permite publicar nuevas soluciones de software IA.
- Formularios con validación en XML y Kotlin.
- Guarda datos en Firestore.
- Posible integración con Firebase Storage para archivos asociados.

### 4. Configuración y Cambio de Tema UI
- Cambia el tema visual de la app (modo claro/oscuro).
- Usa SharedPreferences para almacenar preferencias.
- Implementa AppCompatDelegate para aplicar temas dinámicos.

### 5. Editar Perfil y Subir Foto
- Edita datos personales y biografía.
- Selecciona o captura foto de perfil.
- Almacena imágenes en Firebase Storage.
- Actualiza datos en Firestore.

### 6. Detalles del Software
- Visualiza información detallada del software.
- Recibe datos con Navigation Component.
- Muestra enlaces y datos enriquecidos.

### 7. Chatbot Integrado
- Soporte y consultas a través de chatbot.
- Integración con API externa (Dialogflow, OpenAI, etc.).
- UI basada en RecyclerView para mostrar conversación.

---

## Estructura del Proyecto

- `data/`: Acceso a datos, repositorios, Firebase.
- `ui/`: Fragmentos y actividades para UI.
- `viewmodel/`: Lógica y manejo de datos reactiva.
- `utils/`: Utilidades y helpers generales.
- `res/layout/`: Archivos XML para vistas (incluye layouts para tablets y landscape).
- `res/drawable/`: Recursos gráficos.
- `res/values/`: Strings, colores, estilos y temas.

---

## Tecnologías y Herramientas Utilizadas

| Funcionalidad         | Herramienta / Librería           | Descripción                                          |
|----------------------|---------------------------------|------------------------------------------------------|
| Autenticación        | Firebase Authentication          | Gestión segura de usuarios y sesión.                 |
| Base de datos        | Firebase Firestore               | Almacenamiento en la nube para software y perfiles. |
| Almacenamiento fotos | Firebase Storage                 | Guardado de imágenes de perfil y archivos.           |
| UI                   | Jetpack (Fragment, ViewModel, LiveData, Navigation Component) | Arquitectura moderna y manejo de UI reactiva.       |
| Gestión de temas     | AppCompatDelegate, SharedPreferences | Cambio dinámico y persistente del tema UI.           |
| Carga de imágenes    | Intents para galería/cámara     | Selección y captura de fotos para subir perfil.      |
| Chatbot             | API de chatbot externa           | Conversación automatizada dentro de la app.          |
| Listados            | RecyclerView                    | Mostrar listas dinámicas de software y mensajes.     |
| Validaciones        | XML y Kotlin                    | Validación de entradas en formularios.               |

---

## Cómo Ejecutar el Proyecto

1. Clona el repositorio.
2. Abre el proyecto en Android Studio.
3. Configura Firebase:
    - Crea un proyecto en [Firebase Console](https://console.firebase.google.com/).
    - Habilita Authentication y Firestore.
    - Descarga el archivo `google-services.json`.
    - Coloca el archivo en la carpeta `app/`.
4. Sincroniza Gradle para descargar dependencias.
5. Compila y ejecuta la aplicación en un emulador o dispositivo físico.

---

## Consideraciones Adicionales

- La UI está diseñada con layouts adaptativos para distintos tamaños y orientaciones.
- Se usa MVVM para mantener separación clara de responsabilidades.
- Los temas y preferencias se aplican globalmente sin cambiar el fondo base de la aplicación.
- El chatbot requiere configuración adicional para la API seleccionada.

---

*Desarrollado por [Yulder Orozco / Santiago Gonzales / Juan David Franco]*