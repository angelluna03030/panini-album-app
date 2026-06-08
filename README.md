
# ⚽ Panini World Cup 2026 - Rastreador de Álbum

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0+-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpack-compose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Room](https://img.shields.io/badge/Room_(SQLite)-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/training/data-storage/room)
[![Retrofit](https://img.shields.io/badge/Retrofit-2C3E50?style=for-the-badge&logo=retrofit&logoColor=white)](https://square.github.io/retrofit/)
[![Material 3](https://img.shields.io/badge/Material_3-0056D2?style=for-the-badge&logo=materialdesign&logoColor=white)](https://m3.material.io/)
[![License](https://img.shields.io/badge/Licencia-MIT-yellow.svg?style=for-the-badge)](LICENSE)

---

## 📖 Descripción General
**Panini World Cup 2026** es una aplicación Android moderna con enfoque *offline-first*, diseñada para rastrear, gestionar y optimizar la colección de láminas del álbum oficial Panini del Mundial de Fútbol 2026. Construida con las mejores prácticas de desarrollo Android, ofrece una experiencia de usuario fluida para registrar nuevas láminas, controlar repeticiones, ejecutar intercambios y obtener información detallada de los jugadores en tiempo real mediante APIs externas.

---

## ✨ Características Principales
| Característica | Descripción |
|:---|:---|
| 📊 **Dashboard en Tiempo Real** | Contadores instantáneos de láminas Obtenidas, Pendientes y Repetidas |
| 🔍 **Búsqueda en Vivo** | Filtrado instantáneo por código, nombre del jugador o país |
| 📥 **Registro Inteligente** | Detecta automáticamente si una lámina es nueva o repetida y actualiza el inventario |
| 🔄 **Sistema de Intercambio** | Permite cambiar repetidas por faltantes con validación de inventario |
| 🖼️ **Tarjetas Estilo Panini** | UI premium inspirada en fichas FIFA con datos y fotos reales del jugador |
| 📡 **Integración de APIs** | Consulta datos en vivo (fotos, estadísticas, biografía) desde TheSportsDB |
| 💾 **Almacenamiento Offline-First** | Todos los datos persisten localmente usando SQLite a través de Room |
| 🌙 **UI/UX Moderna** | Material 3, animaciones suaves, diseños responsivos y navegación intuitiva |

---

## 🛠️ Stack Tecnológico
| Capa | Tecnología | Propósito |
|:---|:---|:---|
| **Lenguaje** | Kotlin 2.0+ | Tipado seguro, listo para corrutinas, null-safety |
| **Framework UI** | Jetpack Compose + Material 3 | UI declarativa, moderna y responsiva |
| **Arquitectura** | MVVM + Separación por Capas | Estado predecible, testeable y mantenible |
| **Base de Datos Local** | Room (SQLite) | Persistencia offline, consultas tipadas y seguras |
| **Red / Networking** | Retrofit + Kotlinx Serialization | Consumo de APIs RESTful, mapeo JSON |
| **Carga de Imágenes** | Coil 2 | Descarga asíncrona de imágenes, optimización de memoria |
| **Navegación** | Jetpack Navigation Compose | Enrutamiento tipado, gestión del backstack |
| **Gestión de Estado** | ViewModel + StateFlow + Corrutinas | UI reactiva, flujo de datos consciente del ciclo de vida |
| **Sistema de Build** | Gradle + Version Catalog | Gestión de dependencias, builds reproducibles |

---

## 🏗️ Arquitectura y Diseño Empresarial

### 📐 Patrón Arquitectónico
La aplicación sigue una arquitectura **MVVM (Modelo-Vista-ModeloDeVista) moderna**, potenciada con **Separación por Capas** y principios **Offline-First**:

1. **Capa de UI (Pantallas Compose + Navegación)**  
   → Observa `StateFlow` del ViewModel, renderiza la UI declarativa y maneja la entrada del usuario.
2. **Capa ViewModel**  
   → Coordinador de lógica de negocio. Transforma los flujos del DAO en estado de UI, maneja corrutinas y validaciones.
3. **Capa de Datos (Room DAO + Módulo de Red)**  
   → Fuente única de verdad (*Single Source of Truth*). Room maneja la persistencia local en SQLite; Retrofit maneja las llamadas a APIs externas.
4. **Capa de Dominio/Modelo**  
   → Clases de datos Kotlin puras (`StickerEntity`, `PlayerDto`) con límites claros entre representaciones locales y remotas.

**Principios de Diseño Empresarial Aplicados:**
- ✅ **Principio de Responsabilidad Única (SRP)**: Cada archivo/clase tiene un propósito claro.
- ✅ **Separación de Preocupaciones**: UI, lógica y datos están estrictamente aislados.
- ✅ **Programación Reactiva**: `StateFlow` + `collectAsState()` garantiza que la UI siempre refleje los datos actuales.
- ✅ **Offline-First**: La app funciona al 100% sin internet. Las llamadas a la API son mejoras opcionales.
- ✅ **Estructura Escalable**: Lista para Inyección de Dependencias (Hilt), testing y sincronización en la nube sin necesidad de refactorizar.

---

### 📁 Estructura del Proyecto
```text
com.angel.panini/
├── 📂 data/                          # Capa de Acceso a Datos y Red
│   ├── AppDatabase.kt                # Constructor de la BD Room + datos de prueba (seed)
│   ├── StickerEntity.kt              # Esquema de la tabla SQLite y mapeo de entidad
│   ├── StickerDao.kt                 # Consultas SQL tipadas (Flow/Suspend)
│   ├── NetworkModule.kt              # Cliente Retrofit y configuración JSON
│   ├── TheSportsDbApi.kt             # Definición de la interfaz de la API externa
│   └── PlayerResponseDto.kt          # DTO para mapear respuestas JSON de la API
│
├── 📂 ui/                            # Capa de Presentación
│   ├── 📂 theme/                     # Sistema de diseño Material 3
│   │   ├── Color.kt / Type.kt / Theme.kt
│   ├── 📂 navigation/                # Grafo de enrutamiento y navegación
│   │   └── AppNavigation.kt          # NavHost, barra inferior, definición de rutas
│   ├── 📂 screens/                   # Pantallas de la UI en Compose
│   │   ├── HomeScreen.kt             # Dashboard + botones de acción
│   │   ├── ObtainedScreen.kt         # Lista de láminas obtenidas
│   │   ├── PendingScreen.kt          # Lista de láminas pendientes
│   │   ├── RepeatedScreen.kt         # Lista de repetidas con contadores
│   │   ├── RegisterScreen.kt         # Formulario de registro de láminas
│   │   ├── ExchangeScreen.kt         # UI para intercambiar láminas
│   │   └── PlayerDetailScreen.kt     # Tarjeta de jugador estilo Panini
│   └── 📂 viewmodels/                # Coordinadores de estado y lógica
│       └── StickerViewModel.kt       # Expone StateFlows, maneja operaciones CRUD
│
└── MainActivity.kt                   # Punto de entrada de la app, contenedor del tema
```

---

### 📄 Desglose Archivo por Archivo

| Archivo | Responsabilidad | Conceptos Clave Utilizados |
|:---|:---|:---|
| `AppDatabase.kt` | Inicializa la BD Room, pre-pobla +100 jugadores reales en el primer inicio | `Room.databaseBuilder()`, `RoomDatabase.Callback`, `CoroutineScope` |
| `StickerEntity.kt` | Define la estructura de la tabla SQLite para las láminas | `@Entity`, `@PrimaryKey`, inmutabilidad de data class |
| `StickerDao.kt` | Proporciona operaciones de base de datos tipadas y seguras | `@Query`, `@Insert`, `Flow<Int>`, funciones `suspend` |
| `NetworkModule.kt` | Configura Retrofit, serialización JSON y URLs base | `Retrofit.Builder`, `kotlinx.serialization`, `OkHttp` |
| `TheSportsDbApi.kt` | Declara los endpoints HTTP para la búsqueda de jugadores | `@GET`, `@Query`, llamadas Retrofit suspendidas |
| `PlayerResponseDto.kt` | Mapea la respuesta JSON a objetos Kotlin | `@Serializable`, `@SerialName`, manejo de nulos |
| `StickerViewModel.kt` | Gestiona el estado de la UI, expone flujos reactivos, maneja reglas de negocio | `AndroidViewModel`, `StateFlow`, `viewModelScope`, lógica de validación |
| `AppNavigation.kt` | Define rutas, navegación inferior y deep linking | `NavHost`, `composable()`, `navArgument`, control del backstack |
| `HomeScreen.kt` | Dashboard con contadores en vivo y botones de acción | `collectAsState()`, tarjetas Material 3, enrutamiento |
| `Obtained/Pending/RepeatedScreen.kt` | Listas filtrables con búsqueda en tiempo real | `remember { mutableStateOf }`, `filter { }`, `LazyColumn`, estados vacíos |
| `Register/ExchangeScreen.kt` | Formularios con validación y retroalimentación de estado | `OutlinedTextField`, normalización a mayúsculas, mensajes de éxito/error |
| `PlayerDetailScreen.kt` | Tarjeta premium estilo Panini con datos de la API | `LaunchedEffect`, `AsyncImage`, gradientes personalizados, layout responsivo |
| `Theme.kt` | Tematización Material 3, colores dinámicos, tipografía | `MaterialTheme`, `ColorScheme`, `Typography` |
| `MainActivity.kt` | Arranque de la app, configuración edge-to-edge, contenedor del tema | `enableEdgeToEdge()`, `setContent`, ciclo de vida de la actividad |

---

## 🔌 Integración de APIs y Flujo de Datos

### 🌐 APIs Externas Consumidas

#### 1. **TheSportsDB API** (Datos en Vivo de Jugadores)
- **Endpoint:** `GET https://www.thesportsdb.com/api/v1/json/3/searchplayers.php?p={nombreJugador}`
- **Propósito:** Obtener fotos reales, nacionalidad, posición, fecha de nacimiento, equipo y biografía del jugador.
- **Mapeo JSON:** `PlayerResponseDto` ↔ Clase de datos Kotlin vía `kotlinx.serialization`.
- **Manejo de Errores:** UI de fallback elegante si el jugador no se encuentra o falla la red.
- **Carga de Imágenes:** `Coil` carga asíncronamente `strCutout` o `strThumb` con estados de placeholder y error.

#### 2. **Render Backend API** (Arquitectura de Referencia)
- **URL Base:** `https://contro-panini-api.onrender.com`
- **Endpoints Documentados:**
  - `GET /api/stickers/obtained` → Láminas obtenidas
  - `GET /api/stickers/pending` → Láminas pendientes
  - `GET /api/stickers/repeated` → Repetidas con cantidades
  - `POST /api/stickers/register` → Registro de nueva/repetida
  - `POST /api/stickers/exchange` → Validación de lógica de intercambio
- **Nota de Implementación:** La app implementa la **exacta misma lógica de negocio de forma local vía Room** para garantizar confiabilidad 100% offline. Los endpoints de la API están totalmente documentados y listos para una futura sincronización en la nube sin necesidad de refactorizar las capas de UI o ViewModel.

### 🗄️ Base de Datos Local (SQLite vía Room)
| Aspecto | Implementación |
|:---|:---|
| **Motor** | SQLite 3 (envuelto por Android Room) |
| **Esquema** | Tabla `stickers`: `code (PK)`, `playerName`, `team`, `isObtained`, `repeatedCount` |
| **Consultas Reactivas** | Retorna `Flow<List<StickerEntity>>` para actualizaciones automáticas de la UI |
| **Pre-poblado (Seed)** | Carga +100 jugadores reales (ARG, BRA, POR, ESP, FRA, ALE, ING) en el primer lanzamiento |
| **Seguridad de Hilos** | Todas las operaciones de escritura se ejecutan en `Dispatchers.IO` vía funciones `suspend` |
| **Integridad de Datos** | `OnConflictStrategy.REPLACE` previene entradas duplicadas en la Primary Key |

---

## 🚀 Configuración e Instalación

### 📋 Requisitos Previos
- Android Studio Iguana / Jellyfish / Koala o superior
- JDK 17 o superior
- Android SDK 34 (API 34)
- Min SDK: API 26 (Android 8.0)

### ⚙️ Inicio Rápido
1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/panini-worldcup-2026.git
   ```
2. Abre el proyecto en Android Studio → Espera a que finalice la sincronización de Gradle.
3. Asegúrate de que `gradle/libs.versions.toml` y `app/build.gradle.kts` coincidan con la estructura del catálogo.
4. Ejecuta en un emulador o dispositivo físico:
   ```bash
   ./gradlew installDebug
   ```
5. 🎉 ¡La app se lanza con el álbum pre-cargado y la funcionalidad offline lista para usar!

---

## 📊 Cobertura de la Rúbrica Académica

| Requisito de la Rúbrica | Estado | Evidencia de Implementación |
|:---|:---:|:---|
| ✅ Consultar láminas ya obtenidas | **COMPLETO** | `GET /api/stickers/obtained` + Room DAO `getObtainedStickers()` |
| ✅ Consultar láminas pendientes | **COMPLETO** | `GET /api/stickers/pending` + Room DAO `getPendingStickers()` |
| ✅ Consultar láminas repetidas con cantidades | **COMPLETO** | `GET /api/stickers/repeated` + Room `repeatedCount` + Badge en UI |
| ✅ Registrar láminas que obtuve | **COMPLETO** | `RegisterScreen.kt` + `markAsObtained()` + detección de duplicados |
| ✅ Registrar láminas que intercambie | **COMPLETO** | `ExchangeScreen.kt` + validación + `decreaseRepeated()` + `markAsObtained()` |
| ✅ **Los datos se guardan usando SQLite** | **COMPLETO** | Room + `@Database`, `AppDatabase.kt`, archivo `panini_database.db` |
| ✅ Consumir API suministrada para búsqueda | **COMPLETO** | `TheSportsDbApi.kt` + Retrofit + `PlayerDetailScreen.kt` |
| ⏳ Video explicativo (15-20 min) | **PENDIENTE** | Grabar por equipo explicando UI, arquitectura y código |
| ⏳ Repositorio público | **PENDIENTE** | Subir a GitHub con `README.md`, `.gitignore` y estructura limpia |

---

## 🔮 Mejoras Futuras y Hoja de Ruta (Roadmap)
| Característica | Descripción | Prioridad |
|:---|:---|:---:|
| 🔄 **Sincronización en la Nube** | Integrar la API de Render para sincronización multi-dispositivo vía WorkManager | Alta |
| 📸 **Escáner QR / Código de Barras** | Escanear códigos físicos de Panini para registro automático | Media |
| 🌗 **Alternancia Tema Oscuro/Claro** | Selector de tema dinámico con persistencia de preferencias | Baja |
| 📊 **Analíticas de Colección** | Gráficos de progreso, porcentaje de completado, historial de intercambios | Media |
| 🧪 **Testing Unitario y de UI** | JUnit5, MockK, Compose Testing, BD Room en memoria | Alta |
| 🌍 **Soporte i18n** | Recursos de strings en Español/Inglés con `strings.xml` | Baja |
| 🔐 **Almacenamiento Seguro** | Encriptación de Room vía SQLCipher para datos sensibles | Baja |

---

## 👥 Equipo y Contribución
Este proyecto fue desarrollado de forma colaborativa siguiendo prácticas ágiles:
- **Frontend/UI:** Jetpack Compose, Material 3, Navigation
- **Datos/Arquitectura:** Room, Retrofit, MVVM, Corrutinas
- **Integración de APIs:** TheSportsDB, mapeo JSON, manejo de errores
- **Documentación y QA:** README, alineación con rúbrica, estrategia de testing

---

## 📜 Licencia
Distribuido bajo la **Licencia MIT**. Consulta el archivo `LICENSE` para más información.  
*Proyecto educativo para evaluación académica. No afiliado con Panini ni FIFA.*

---

> 💡 **Consejo para la Presentación en Video:** Durante los 15-20 minutos, demuestra:  
> 1. **Funcionalidad Offline** (activa el modo avión y muestra que la app sigue funcionando).  
> 2. **Filtrado en tiempo real** en las listas de láminas.  
> 3. **Flujo de registro** y cómo maneja automáticamente los duplicados.  
> 4. **Lógica de intercambio** con validación de inventario.  
> 5. **Carga de la tarjeta del jugador** desde la API (ej. busca a Cristiano Ronaldo).  
> 6. **Explica el flujo de datos:** `AppDatabase.kt` → `StickerViewModel.kt` → `Pantallas UI`.  
> 7. **Muestra el archivo de la BD** en el *Device File Explorer* de Android Studio (`/data/data/com.angel.panini/databases/`) para probar físicamente la persistencia en SQLite.

---

## 👤 Autor

**Ángel Luna**  
GitHub: [@angelluna03030](https://github.com/angelluna03030)  
GitLab: [@angelluna03030](https://gitlab.com/angelluna03030)  
Portfolio: [portafoliowebangelluna.vercel.app](https://portafoliowebangelluna.vercel.app)

---

*PaniniAlbum2026 — Mundial de Fútbol 2026 🏆*

---

### 🚀 ¿Qué sigue?
1. Copia todo este texto y pégalo en un archivo llamado `README.md` en la raíz de tu proyecto.
2. Sube todo el código a un repositorio público de GitHub.
3. ¡Preparen el guion para el video de 15-20 minutos usando los "Consejos para la Presentación" que incluí al final!

¿Necesitas que te ayude a redactar el guion para el video o algún otro detalle final? ¡Estoy aquí para que saquen la máxima nota! 🏆⚽