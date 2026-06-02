# 📒 PaniniAlbum2026

Aplicación móvil Android para llevar el control de las láminas del **Álbum Panini Mundial de Fútbol 2026**.  
Construida con **Kotlin + Jetpack Compose**, arquitectura **MVVM + Clean Architecture**, **Room (SQLite)** para persistencia local y consumo de dos APIs REST externas.

---

## 📋 Tabla de Contenidos

1. [Funcionalidades](#-funcionalidades)
2. [Stack Tecnológico](#-stack-tecnológico)
3. [Arquitectura](#-arquitectura)
4. [Estructura de Archivos](#-estructura-de-archivos)
5. [Capas de la Aplicación](#-capas-de-la-aplicación)
   - [Capa de Datos (data)](#capa-de-datos-data)
   - [Capa de Dominio (domain)](#capa-de-dominio-domain)
   - [Capa de UI (ui)](#capa-de-ui-ui)
6. [Pantallas](#-pantallas)
7. [Componentes Reutilizables](#-componentes-reutilizables)
8. [Navegación](#-navegación)
9. [Tema Visual](#-tema-visual)
10. [APIs Externas](#-apis-externas)
11. [Base de Datos Local (Room)](#-base-de-datos-local-room)
12. [Dependencias](#-dependencias)
13. [Cómo correr el proyecto](#-cómo-correr-el-proyecto)

---

## ✅ Funcionalidades

| # | Funcionalidad | Pantalla |
|---|---|---|
| 1 | Consultar láminas **obtenidas** | `ObtainedScreen` |
| 2 | Consultar láminas **pendientes** (faltan) | `PendingScreen` |
| 3 | Consultar láminas **repetidas** con cantidades | `RepeatedScreen` |
| 4 | **Registrar** una lámina nueva | `PendingScreen` (botón por ítem) |
| 5 | **Intercambiar** una repetida por una que falta | `ExchangeScreen` |
| 6 | **Buscar jugador** por nombre con foto e info | `SearchScreen` |
| 7 | Persistencia local con **SQLite** vía Room | Todas las pantallas |
| 8 | Consumo de **TheSportsDB API** para jugadores | `SearchScreen` |
| 9 | Consumo de **Panini Backend API** (estado del álbum) | Todas las pantallas |

---

## 🛠 Stack Tecnológico

| Categoría | Tecnología | Versión | Propósito |
|---|---|---|---|
| Lenguaje | Kotlin | 2.0+ | Lenguaje principal |
| UI | Jetpack Compose | BOM 2024.12.01 | Interfaz declarativa (como Flutter) |
| UI Components | Material3 | incluida en BOM | Cards, FAB, NavigationBar, etc. |
| Navegación | Navigation Compose | 2.8.4 | Rutas entre pantallas |
| Estado/Lógica | ViewModel + StateFlow | Lifecycle 2.8.7 | Manejo de estado por pantalla |
| Base de datos | Room (SQLite) | 2.6.1 | Persistencia local |
| Procesador Room | KSP | 2.0.21-1.0.28 | Genera código DAO en compilación |
| Red | Retrofit2 | 2.11.0 | Llamadas HTTP a las APIs |
| JSON | Gson Converter | 2.11.0 | Deserialización automática |
| HTTP Logger | OkHttp Logging | 4.12.0 | Debug de requests en Logcat |
| Imágenes | Coil Compose | 2.7.0 | Carga imágenes de la API (fotos jugadores) |
| Mínimo SDK | Android 8.0 | API 26 | Compatible con ~97% de dispositivos |

---

## 🏛 Arquitectura

La app sigue **MVVM (Model-View-ViewModel)** con principios de **Clean Architecture**, dividida en 3 capas:

```
┌─────────────────────────────────────────┐
│              UI Layer                   │  ← Compose Screens + ViewModels
├─────────────────────────────────────────┤
│            Domain Layer                 │  ← Modelos de negocio (data classes)
├─────────────────────────────────────────┤
│             Data Layer                  │  ← Room (SQLite) + Retrofit (APIs)
└─────────────────────────────────────────┘
```

**Flujo de datos:**

```
Compose Screen
     │  observa StateFlow
     ▼
  ViewModel
     │  llama funciones
     ▼
  Repository
     ├──► Room DAO        (datos locales SQLite)
     └──► Retrofit API    (datos remotos)
```

> **Analogía con Flutter:** En Flutter usarías `StatefulWidget` + `setState` o `Provider/Bloc`. En Compose, el equivalente es `ViewModel` + `StateFlow` + `collectAsState()`. La pantalla se re-renderiza automáticamente cuando el estado cambia — exactamente igual que en Flutter.

---

## 📁 Estructura de Archivos

```
app/
└── src/main/
    ├── java/com/panini/album2026/
    │   │
    │   ├── data/                          ← CAPA DE DATOS
    │   │   ├── local/
    │   │   │   ├── dao/
    │   │   │   │   └── StickerDao.kt      ← Interfaz SQL para Room
    │   │   │   ├── entity/
    │   │   │   │   └── StickerEntity.kt   ← Tabla SQLite (modelo de BD)
    │   │   │   └── AppDatabase.kt         ← Instancia singleton de Room
    │   │   ├── remote/
    │   │   │   ├── PaniniApiService.kt    ← Endpoints de la Panini API
    │   │   │   ├── SportsDbApiService.kt  ← Endpoint de TheSportsDB
    │   │   │   └── RetrofitClient.kt      ← Configuración HTTP (2 clientes)
    │   │   └── repository/
    │   │       ├── StickerRepository.kt   ← Orquesta Room + Panini API
    │   │       └── PlayerRepository.kt    ← Orquesta TheSportsDB API
    │   │
    │   ├── domain/                        ← CAPA DE DOMINIO
    │   │   └── model/
    │   │       ├── Sticker.kt             ← Modelo de lámina (UI)
    │   │       └── Player.kt              ← Modelo de jugador (UI)
    │   │
    │   ├── ui/                            ← CAPA DE UI
    │   │   ├── theme/
    │   │   │   ├── Color.kt               ← Paleta de colores
    │   │   │   ├── Theme.kt               ← MaterialTheme oscuro
    │   │   │   └── Type.kt                ← Estilos tipográficos
    │   │   ├── components/
    │   │   │   ├── StickerCard.kt         ← Card reutilizable de lámina
    │   │   │   ├── BottomNavBar.kt        ← Barra de navegación inferior
    │   │   │   └── SearchBar.kt           ← Campo de búsqueda
    │   │   ├── screens/
    │   │   │   ├── obtained/
    │   │   │   │   ├── ObtainedScreen.kt  ← Pantalla láminas obtenidas
    │   │   │   │   └── ObtainedViewModel.kt
    │   │   │   ├── pending/
    │   │   │   │   ├── PendingScreen.kt   ← Pantalla láminas pendientes
    │   │   │   │   └── PendingViewModel.kt
    │   │   │   ├── repeated/
    │   │   │   │   ├── RepeatedScreen.kt  ← Pantalla láminas repetidas
    │   │   │   │   └── RepeatedViewModel.kt
    │   │   │   ├── exchange/
    │   │   │   │   ├── ExchangeScreen.kt  ← Pantalla de intercambio
    │   │   │   │   └── ExchangeViewModel.kt
    │   │   │   └── search/
    │   │   │       ├── SearchScreen.kt    ← Pantalla búsqueda jugadores
    │   │   │       └── SearchViewModel.kt
    │   │   └── navigation/
    │   │       └── AppNavigation.kt       ← Rutas y definición de tabs
    │   │
    │   └── MainActivity.kt                ← Entry point, Scaffold + NavHost
    │
    ├── res/
    │   └── values/
    │       └── strings.xml                ← Textos de la app
    └── AndroidManifest.xml                ← Permisos (INTERNET)
```

---

## 🧱 Capas de la Aplicación

### Capa de Datos (`data/`)

Es la capa más baja. Se encarga de **obtener y guardar datos**, sin importar si vienen de la red o del dispositivo.

---

#### `data/local/entity/StickerEntity.kt`

**¿Qué es?** La representación de una fila en la tabla SQLite de Room.

```
Tabla: stickers
┌──────────┬──────────┬──────────┬──────────┬──────────┐
│   code   │   name   │  team    │ obtained │ repeated │
│  TEXT PK │  TEXT    │  TEXT    │ BOOLEAN  │ INTEGER  │
│ "ARG-1"  │"Messi"   │"Argentina"│  true   │    2     │
└──────────┴──────────┴──────────┴──────────┴──────────┘
```

| Campo | Tipo | Descripción |
|---|---|---|
| `code` | `String` (PK) | Código único — ej: `"ARG-3"`, `"ESP-7"` |
| `name` | `String` | Nombre del jugador en la lámina |
| `team` | `String` | Selección nacional — ej: `"Argentina"` |
| `obtained` | `Boolean` | `true` si ya la tengo |
| `repeated` | `Int` | Cantidad de copias extra que tengo |

---

#### `data/local/dao/StickerDao.kt`

**¿Qué es?** La interfaz que Room usa para generar las consultas SQL automáticamente. Tú escribes funciones Kotlin, Room genera el SQL.

| Función | SQL equivalente | Uso |
|---|---|---|
| `getAllObtained()` | `SELECT * WHERE obtained = 1` | Pantalla Obtenidas |
| `getAllPending()` | `SELECT * WHERE obtained = 0` | Pantalla Pendientes |
| `getAllRepeated()` | `SELECT * WHERE repeated > 0` | Pantalla Repetidas |
| `getStickerByCode()` | `SELECT * WHERE code = ?` | Validar antes de registrar |
| `upsert()` | `INSERT OR REPLACE` | Guardar/actualizar lámina |

---

#### `data/local/AppDatabase.kt`

**¿Qué es?** El singleton que crea y mantiene la conexión a la base de datos SQLite.

```
AppDatabase (Room)
      │
      └── StickerDao  ← acceso a la tabla stickers
```

> Solo existe **una instancia** en toda la app (patrón Singleton). Vive mientras la app está abierta.

---

#### `data/remote/PaniniApiService.kt`

**¿Qué es?** La interfaz Retrofit que define los endpoints de la API del álbum Panini.

| Función | Método HTTP | Endpoint | Pantalla que lo usa |
|---|---|---|---|
| `getObtained()` | `GET` | `/api/stickers/obtained` | ObtainedScreen |
| `getPending()` | `GET` | `/api/stickers/pending` | PendingScreen |
| `getRepeated()` | `GET` | `/api/stickers/repeated` | RepeatedScreen |
| `registerSticker()` | `POST` | `/api/stickers/register` | PendingScreen |
| `exchangeSticker()` | `POST` | `/api/stickers/exchange` | ExchangeScreen |

**URL base:** `https://contro-panini-api.onrender.com`

---

#### `data/remote/SportsDbApiService.kt`

**¿Qué es?** La interfaz Retrofit para buscar jugadores en TheSportsDB.

| Función | Método | Endpoint |
|---|---|---|
| `searchPlayer(name)` | `GET` | `/api/v1/json/3/searchplayers.php?p={name}` |

**URL base:** `https://www.thesportsdb.com`

La respuesta incluye: foto, nombre, nacionalidad, equipo, posición, fecha de nacimiento, altura y peso.

---

#### `data/remote/RetrofitClient.kt`

**¿Qué es?** La fábrica que construye las instancias de Retrofit. Hay **dos clientes** separados porque apuntan a URLs base distintas.

```
RetrofitClient
    ├── paniniApi      → https://contro-panini-api.onrender.com
    └── sportsDbApi    → https://www.thesportsdb.com
```

---

#### `data/repository/StickerRepository.kt`

**¿Qué es?** El orquestador entre Room y la Panini API. El ViewModel nunca habla directo con Room ni con Retrofit — siempre pasa por el Repository.

```
StickerRepository
    ├── Guarda datos de la API → Room (cache local)
    ├── Lee de Room si no hay internet
    └── Expone funciones suspend (coroutines) al ViewModel
```

---

#### `data/repository/PlayerRepository.kt`

**¿Qué es?** Maneja las búsquedas de jugadores contra TheSportsDB. Gestiona el caso donde la API no devuelve resultados (jugador desconocido).

---

### Capa de Dominio (`domain/`)

Modelos puros de Kotlin, sin dependencias de Android ni de APIs. Son los objetos que viajan entre el Repository y la UI.

---

#### `domain/model/Sticker.kt`

```kotlin
data class Sticker(
    val code: String,      // "ARG-3"
    val name: String,      // "Lionel Messi"
    val team: String,      // "Argentina"
    val obtained: Boolean, // true / false
    val repeated: Int      // 0, 1, 2...
)
```

---

#### `domain/model/Player.kt`

```kotlin
data class Player(
    val id: String,
    val name: String,
    val nationality: String,
    val team: String,
    val position: String,
    val birthDate: String,
    val photo: String,     // URL de la imagen
    val height: String,
    val weight: String
)
```

---

### Capa de UI (`ui/`)

Todo lo que el usuario ve e interactúa. Compuesta por **Theme**, **Components** reutilizables, **Screens** individuales y **Navigation**.

---

## 📱 Pantallas

### 1. `ObtainedScreen` — Láminas Obtenidas ✅

**Ruta de navegación:** `obtained`  
**Color de acento:** Verde `#1D9E75`

**¿Qué hace?**
- Muestra la lista de láminas que ya tienes
- Cada ítem muestra: código, nombre del jugador, selección y badge verde "Obtenida"
- Indicador del total: "X / 106 láminas"

**ViewModel (`ObtainedViewModel`):**
- Expone `uiState: StateFlow<List<Sticker>>`
- Llama a `StickerRepository.getObtained()` al inicializarse

---

### 2. `PendingScreen` — Láminas Pendientes ⏳

**Ruta de navegación:** `pending`  
**Color de acento:** Rojo `#E24B4A`

**¿Qué hace?**
- Muestra las láminas que **te faltan**
- Cada ítem tiene un botón **"Registrar"** — al tocarlo, la lámina pasa a Obtenidas
- Si ya la tenías obtenida, se registra como repetida automáticamente

**ViewModel (`PendingViewModel`):**
- Expone `uiState: StateFlow<List<Sticker>>`
- Función `registerSticker(code: String)` → llama al POST `/api/stickers/register`

---

### 3. `RepeatedScreen` — Láminas Repetidas 🔁

**Ruta de navegación:** `repeated`  
**Color de acento:** Azul `#378ADD`

**¿Qué hace?**
- Muestra solo las láminas con `repeated > 0`
- Cada ítem muestra la cantidad de repetidas con un badge azul (ej: `×3`)

**ViewModel (`RepeatedViewModel`):**
- Expone `uiState: StateFlow<List<Sticker>>`
- Llama a `StickerRepository.getRepeated()`

---

### 4. `ExchangeScreen` — Intercambiar Lámina 🔄

**Ruta de navegación:** `exchange`  
**Color de acento:** Dorado `#C9A227`

**¿Qué hace?**
- Formulario con dos campos:
  - **"Lámina que das"** → debe ser una que tienes repetida (ej: `ARG-3`)
  - **"Lámina que recibes"** → debe ser una que te falta (ej: `ARG-4`)
- Validaciones antes de enviar:
  - La lámina dada debe existir y tener `repeated > 0`
  - La lámina recibida no debe estar ya obtenida
- Al confirmar: llama al POST `/api/stickers/exchange`
- Resultado: `repeated` de la dada baja en 1, la recibida se marca como obtenida

**ViewModel (`ExchangeViewModel`):**
- Expone `uiState: StateFlow<ExchangeUiState>` (Idle / Loading / Success / Error)
- Función `exchange(repeatedCode, newCode)`

---

### 5. `SearchScreen` — Buscar Jugador 🔍

**Ruta de navegación:** `search`  
**Color de acento:** Blanco / Secundario

**¿Qué hace?**
- Campo de búsqueda por nombre (ej: "Cristiano Ronaldo")
- Al buscar, consume TheSportsDB API a través del backend Panini
- Si encuentra resultados: muestra foto del jugador (cargada con Coil), nombre, equipo, posición, fecha de nacimiento, altura y peso
- Si no encuentra: muestra mensaje "Jugador no encontrado en la base de datos"

**ViewModel (`SearchViewModel`):**
- Expone `uiState: StateFlow<SearchUiState>` (Idle / Loading / Found / NotFound / Error)
- Función `searchPlayer(name: String)`

---

## 🧩 Componentes Reutilizables

### `ui/components/StickerCard.kt`

Card genérica usada en las pantallas Obtenidas, Pendientes y Repetidas.

| Prop | Tipo | Descripción |
|---|---|---|
| `sticker` | `Sticker` | Datos de la lámina |
| `onAction` | `((String) -> Unit)?` | Callback opcional (botón Registrar/Intercambiar) |
| `actionLabel` | `String?` | Texto del botón de acción |

**Variantes visuales según estado:**
- Obtenida → borde y badge verde
- Pendiente → borde y badge rojo
- Repetida → badge azul con cantidad `×N`

---

### `ui/components/SearchBar.kt`

Campo de búsqueda reutilizable con:
- Icono de lupa a la izquierda
- Botón de limpiar (×) cuando hay texto
- Acción al presionar Enter del teclado (`ImeAction.Search`)

---

### `ui/components/BottomNavBar.kt`

> En esta app el `BottomNavBar` está integrado directamente en `MainActivity.kt` dentro del `Scaffold`. Si en el futuro se extrae como componente independiente, irá aquí.

Configuración actual:
- 5 tabs: Obtenidas, Pendientes, Repetidas, Intercambio, Buscar
- Tab activo: ícono y texto en **dorado** `#C9A227`
- Tab inactivo: color terciario `#666666`
- Indicador: fondo `Surface2 #252525`

---

## 🗺 Navegación

**Archivo:** `ui/navigation/AppNavigation.kt`  
**Tipo:** Bottom Navigation con `NavHost`

```
AppNavigation
├── Screen.Obtained  → ruta: "obtained"   ícono: CheckCircle
├── Screen.Pending   → ruta: "pending"    ícono: RadioButtonUnchecked
├── Screen.Repeated  → ruta: "repeated"   ícono: Repeat
├── Screen.Exchange  → ruta: "exchange"   ícono: SwapHoriz
└── Screen.Search    → ruta: "search"     ícono: Search
```

Cada `Screen` es un `sealed class` con 3 propiedades: `route`, `label`, `icon`.

**Comportamiento de navegación:**
- `saveState = true` → al volver a una tab, recuerda el scroll y estado
- `launchSingleTop = true` → no duplica la pantalla en el back stack
- `popUpTo(startDestination)` → limpia el stack al cambiar de tab principal

---

## 🎨 Tema Visual

**Archivo:** `ui/theme/`  
**Modo:** Solo oscuro (`darkColorScheme`)

### Paleta de colores (`Color.kt`)

| Nombre | Hex | Uso |
|---|---|---|
| `GoldFIFA` | `#C9A227` | Acento principal, FAB, tab activo |
| `GoldFIFADark` | `#9A7A1A` | Variante oscura del dorado |
| `GreenObtained` | `#1D9E75` | Láminas obtenidas |
| `RedPending` | `#E24B4A` | Láminas pendientes |
| `BlueRepeated` | `#378ADD` | Láminas repetidas |
| `Surface0` | `#0F0F0F` | Fondo de pantalla principal |
| `Surface1` | `#1A1A1A` | Cards y sheets |
| `Surface2` | `#252525` | Inputs y elementos elevados |
| `Surface3` | `#2F2F2F` | Dividers y bordes |
| `TextPrimary` | `#FFFFFF` | Texto principal |
| `TextSecondary` | `#AAAAAA` | Subtítulos y labels |
| `TextTertiary` | `#666666` | Placeholders y tabs inactivos |
| `BorderSubtle` | `#2A2A2A` | Bordes sutiles |

### Tipografía (`Type.kt`)

| Estilo | Tamaño | Peso | Uso |
|---|---|---|---|
| `headlineLarge` | 28sp | Bold | Títulos de pantalla |
| `headlineMedium` | 22sp | SemiBold | Subtítulos grandes |
| `titleLarge` | 18sp | SemiBold | Encabezados de sección |
| `titleMedium` | 15sp | Medium | Nombre de lámina en card |
| `bodyLarge` | 15sp | Normal | Texto de cuerpo |
| `bodyMedium` | 13sp | Normal | Texto secundario, detalles |
| `labelSmall` | 11sp | Medium | Labels de tabs, badges |

---

## 🌐 APIs Externas

### 1. Panini Album 2026 API

**URL base:** `https://contro-panini-api.onrender.com`

| Endpoint | Método | Descripción |
|---|---|---|
| `/api/status` | GET | Health check |
| `/api/stickers/obtained` | GET | Lista láminas obtenidas |
| `/api/stickers/pending` | GET | Lista láminas pendientes |
| `/api/stickers/repeated` | GET | Lista láminas repetidas con cantidades |
| `/api/stickers/register` | POST | Registra una lámina nueva o repetida |
| `/api/stickers/exchange` | POST | Intercambia una repetida por una faltante |
| `/api/players/search?name=` | GET | Busca jugador (proxy a TheSportsDB) |

**Códigos de láminas disponibles:**

| Equipo | Códigos |
|---|---|
| Argentina 🇦🇷 | ARG-1 a ARG-16 |
| Brasil 🇧🇷 | BRA-1 a BRA-16 |
| España 🇪🇸 | ESP-1 a ESP-15 |
| Francia 🇫🇷 | FRA-1 a FRA-15 |
| Portugal 🇵🇹 | POR-1 a POR-15 |
| Alemania 🇩🇪 | ALE-1 a ALE-14 |
| Inglaterra 🏴󠁧󠁢󠁥󠁮󠁧󠁿 | ING-1 a ING-15 |

**Total: 106 láminas**

---

### 2. TheSportsDB API

**URL base:** `https://www.thesportsdb.com`  
**Endpoint:** `GET /api/v1/json/3/searchplayers.php?p={nombre}`

**Respuesta exitosa (jugador encontrado):**
```json
{
  "players": [{
    "id": "34146370",
    "name": "Cristiano Ronaldo",
    "nationality": "Portugal",
    "team": "Al Nassr",
    "position": "Centre-Forward",
    "birthDate": "1985-02-05",
    "photo": "https://r2.thesportsdb.com/...",
    "height": "1.87 m",
    "weight": "85 kg"
  }]
}
```

**Respuesta sin resultados:**
```json
{ "players": [], "message": "No se encontraron jugadores" }
```

> ⚠️ **Nota:** No todos los jugadores aparecen en esta API. La app maneja el caso `players = []` mostrando un mensaje amigable en lugar de un error.

---

## 🗄 Base de Datos Local (Room)

Room es el ORM oficial de Android para SQLite. Funciona con anotaciones `@Entity`, `@Dao` y `@Database`.

**Analogía con Flutter:** Es equivalente a usar `sqflite` + un ORM, pero Room genera el código SQL automáticamente en tiempo de compilación gracias a **KSP**.

**Tabla generada: `stickers`**

```sql
CREATE TABLE stickers (
    code     TEXT    PRIMARY KEY NOT NULL,
    name     TEXT    NOT NULL,
    team     TEXT    NOT NULL,
    obtained INTEGER NOT NULL DEFAULT 0,
    repeated INTEGER NOT NULL DEFAULT 0
);
```

**Flujo de sincronización:**

```
App inicia
    │
    ▼
Repository.sync()
    │
    ├── GET /api/stickers/obtained  ──► Room: upsert obtenidas
    ├── GET /api/stickers/pending   ──► Room: upsert pendientes
    └── GET /api/stickers/repeated  ──► Room: actualizar repetidas
    
Usuario registra lámina
    │
    ▼
POST /api/stickers/register  ──► Room: actualizar estado local
```

---

## 📦 Dependencias

```kotlin
// Compose BOM — versiona todo Compose automáticamente
val composeBom = platform("androidx.compose:compose-bom:2024.12.01")
implementation(composeBom)

// UI Compose + Material3
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.ui:ui-tooling-preview")
implementation("androidx.compose.material3:material3")
implementation("androidx.activity:activity-compose:1.9.3")

// Navegación
implementation("androidx.navigation:navigation-compose:2.8.4")

// ViewModel + Lifecycle
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")

// Room (SQLite)
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
ksp("androidx.room:room-compiler:2.6.1")

// Retrofit (HTTP)
implementation("com.squareup.retrofit2:retrofit:2.11.0")
implementation("com.squareup.retrofit2:converter-gson:2.11.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

// Coil (imágenes de red)
implementation("io.coil-kt:coil-compose:2.7.0")

// Debug
debugImplementation("androidx.compose.ui:ui-tooling")
```

---

## 🚀 Cómo correr el proyecto

### Requisitos

- Android Studio **Ladybug o Meerkat** (2024+)
- JDK 11+
- Android SDK API 26+
- Conexión a internet (para sincronizar con las APIs)

### Pasos

```bash
# 1. Clonar el repositorio
git clone <url-del-repo>
cd PaniniAlbum2026

# 2. Abrir en Android Studio
# File → Open → seleccionar la carpeta del proyecto

# 3. Sincronizar Gradle
# Click en "Sync Now" en el banner amarillo

# 4. Correr la app
# Click en el botón ▶️ Run o presionar Shift+F10
# Seleccionar emulador o dispositivo físico conectado
```

### Primera ejecución

Al iniciar por primera vez, la app:
1. Conecta con `https://contro-panini-api.onrender.com` para obtener las 106 láminas
2. Las guarda en SQLite local
3. Muestra todas las láminas como **pendientes** (sin obtener)

> ⚠️ El servidor Render puede tardar ~30 segundos en responder si lleva tiempo inactivo (free tier). Es normal.

---

## 👤 Autor

**Ángel Luna**  
GitHub: [@angelluna03030](https://github.com/angelluna03030)  
GitLab: [@angelluna03030](https://gitlab.com/angelluna03030)  
Portfolio: [portafoliowebangelluna.vercel.app](https://portafoliowebangelluna.vercel.app)

---

*PaniniAlbum2026 — Mundial de Fútbol 2026 🏆*
