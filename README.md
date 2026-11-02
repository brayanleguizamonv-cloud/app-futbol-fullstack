# ⚽ Football Tracker App - Full Stack Architecture

Aplicación móvil nativa para el seguimiento en tiempo real de las principales ligas de fútbol europeas (La Liga, Premier League, Champions League, Serie A y Bundesliga). Proyecto Full Stack desarrollado como práctica de arquitectura integral, separando el cliente móvil, el servidor intermediario y la persistencia de datos.

## 🛠 Stack Tecnológico

**Frontend (Android Mobile):**
*   **Lenguaje:** Java Native (Android Studio).
*   **UI/UX:** Material Design, TabLayout para filtrado por competiciones, SwipeRefreshLayout.
*   **Componentes:** RecyclerView optimizado con layouts anidados, carga asíncrona de imágenes de red mediante Glide.

**Backend (REST API):**
*   **Framework:** Spring Boot (Java).
*   **Integración Externa:** Consumo de API REST de terceros (football-data.org) mediante RestTemplate con control de excepciones y bloqueos de red.
*   **Procesamiento:** Transformación y unificación de DTOs multicompetición, estandarización de husos horarios (UTC).

**Base de Datos & Entorno:**
*   **Motor:** PostgreSQL.
*   **Infraestructura:** Gestión de base de datos local en contenedores Docker.

## ⚙️ Arquitectura y Lógica de Negocio

1. **Sincronización:** El servidor de Spring Boot orquesta peticiones iterativas a la API externa para unificar múltiples competiciones europeas en un único payload JSON, optimizando el consumo de red del cliente móvil.
2. **Persistencia y Tiempos:** Los datos temporales se manejan estrictamente en formato absoluto UTC en el backend.
3. **Distribución:** El cliente Android consume la API propia, descarga las rutas de las imágenes en tiempo real y convierte dinámicamente las fechas UTC al huso horario local (TimeZone) del dispositivo del usuario.
4. **UX Dinámica:** Implementación de un algoritmo de proximidad temporal que escanea las fechas de la base de datos comparándolas con el reloj del sistema, calculando matemáticamente la posición de la jornada actual y auto-posicionando el scroll del RecyclerView mediante offsets exactos.

## 📱 Demostración Visual
<img width="313" height="710" alt="Captura de pantalla 2026-11-02 013339" src="https://github.com/user-attachments/assets/dc4fb34a-f3a4-4e7c-9f38-d2ba215d0426" />
<img width="316" height="707" alt="Captura de pantalla 2026-11-02 013228" src="https://github.com/user-attachments/assets/34f3b4a9-b7aa-4a53-99a7-7b707bd323ac" />
<img width="320" height="330" alt="Captura de pantalla 2026-11-02 013114" src="https://github.com/user-attachments/assets/d046042e-3159-456b-a8e9-d4e1a967aeb6" />

## 🚀 Próximos Pasos (Roadmap v2.0)
*   Integración de Tablas de Clasificación completas.
*   Ranking de Máximos Goleadores por competición.
*   Integración de interfaz con BottomNavigationView.

---
*Desarrollado y estructurado de forma independiente bajo licencia de todos los derechos reservados.*
