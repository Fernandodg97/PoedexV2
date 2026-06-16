# Pokedex

[![Java](https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![JavaFX](https://img.shields.io/badge/JavaFX_21-007396?style=for-the-badge&logo=java&logoColor=white)](https://openjfx.io/)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)

---

## 👋 Para recruiters

Aplicacion de escritorio desarrollada con **Java 21 + JavaFX** que consume la **PokeAPI** en tiempo real para explorar la Pokedex completa. Demuestra el uso de concurrencia en Java (hilos de fondo), deserializacion de JSON con Jackson y construccion de interfaces graficas con FXML y CSS.

**Stack:** Java 21 · JavaFX 21 · FXML · Jackson Databind · Apache HttpClient · Maven

**Destacado:**
- Consumo de la **PokeAPI REST** con `HttpURLConnection` y deserializacion de JSON con Jackson sin Spring
- Carga asincrona con `javafx.concurrent.Task` y hilo daemon para no bloquear la UI durante la descarga
- Arquitectura en capas: separacion clara de modelo (`model`), logica de conexion (`connection`) y controlador de vista (`control`)
- Navegacion por la Pokedex con botones Anterior/Siguiente, busqueda por nombre y carga bajo demanda del catalogo completo
- Interfaz definida en **FXML** con estilos CSS personalizados

---

## 🛠️ Stack tecnologico

**Aplicacion de escritorio** Java 21 · JavaFX 21 · FXML · CSS

**Comunicacion con API** Apache HttpClient · Jackson Databind

**API externa** PokeAPI v2 (pokeapi.co)

**Build** Maven

---

## ✨ Funcionalidades

| Modulo | Descripcion |
|---|---|
| **Lista de Pokemon** | Carga los 151 Pokemon originales al arrancar, con ID formateado (001, 002...) |
| **Cargar todos** | Boton que descarga el catalogo completo desde la PokeAPI de forma asincrona |
| **Busqueda** | Campo de texto que localiza un Pokemon por nombre en la lista cargada |
| **Detalle** | Muestra imagen oficial, tipos, HP, ataque, defensa, ataque especial, defensa especial y velocidad |
| **Navegacion** | Botones Anterior y Siguiente para recorrer la Pokedex secuencialmente |
| **Estado de carga** | Etiqueta en tiempo real que indica el progreso de descarga (X / total) |

---

## ✨ Aspectos tecnicos destacados

| Tecnica | Detalle |
|---|---|
| **Concurrencia con `Task`** | La descarga de Pokemon se ejecuta en un hilo daemon con `javafx.concurrent.Task`; las actualizaciones de UI se delegan a `Platform.runLater` para respetar el hilo de JavaFX |
| **Deserializacion con Jackson** | `ObjectMapper` convierte el JSON de la PokeAPI en clases POJO (`PokemonData`, `Sprites`, `Stats`, `Type`) con anotaciones de mapeo |
| **Arquitectura en capas** | `PokeAPIConnection` centraliza todas las llamadas HTTP; `MainWindow` solo gestiona la vista; `Pokemon` es un modelo limpio sin dependencias externas |
| **FXML + CSS** | La interfaz se define declarativamente en `MainWindow.fxml` con `BorderPane` y los estilos se aplican desde `style.css` sin mezclar logica con presentacion |
| **Fallback de imagen** | Si el sprite principal es nulo, intenta el sprite shiny; si tampoco existe, carga una imagen local por defecto |

---

## 📂 Estructura del proyecto

```
src/main/java/mp3/dam/elpuig/pokedex/
├── connection/
│   └── PokeAPIConnection.java   # Llamadas HTTP a PokeAPI y deserializacion JSON
├── control/
│   └── MainWindow.java          # Controlador FXML: eventos, carga asincrona y navegacion
├── model/
│   ├── Pokemon.java             # Entidad principal con stats e imagen
│   ├── PokemonData.java         # DTO para deserializar la respuesta de PokeAPI
│   ├── PokemonList.java         # DTO para obtener el total de Pokemon disponibles
│   ├── Sprites.java             # Mapeo de URLs de imagenes
│   ├── Stats.java               # Mapeo de estadisticas base
│   └── Type.java                # Mapeo de tipos de Pokemon
└── PokedexApp.java              # Punto de entrada JavaFX

src/main/resources/mp3/dam/elpuig/pokedex/fxml/
├── MainWindow.fxml              # Layout declarativo de la interfaz
└── style.css                    # Estilos de la aplicacion
```

---

## 🚀 Instalacion y puesta en marcha

### Prerrequisitos

- Java 21
- Maven

### Pasos

1. **Clona el repositorio**
   ```bash
   git clone https://github.com/Fernandodg97/PoedexV2.git
   cd PoedexV2
   ```

2. **Ejecuta la aplicacion**
   ```bash
   mvn javafx:run
   ```

---

## 👨‍💻 Autor

| | |
|---|---|
| **Fernando Diaz** | [github.com/Fernandodg97](https://github.com/Fernandodg97) |

---

## 📄 Licencia

[CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/deed.es)
