# 🍣 AppDelivery Sushi — Aplicación de Compra y Delivery
### Proyecto Final de Curso

He desarrollado **AppDelivery Sushi** como proyecto final, una aplicación Android para la compra de sushi con sistema de gestión y reparto integrado.

La aplicación cuenta con **3 roles principales**:

- 👑 **Admin**
- 👤 **Cliente**
- 🛵 **Delivery**

---

# 🧩 Tecnologías Utilizadas

## 📱 Frontend (Android)
- Kotlin
- Android Studio
- Gradle (KTS)
- Acceso a cámara
- Intents para llamadas telefónicas
- Google Maps API

## 🌐 Backend
- Node.js
- API REST
- PostgreSQL

## ☁️ Servicios externos
- Firebase Storage (almacenamiento de imágenes)
- Google Maps API (ubicación y navegación)
- Postman (testing de endpoints)

---

# 👑 Funcionalidades por Rol

## Admin
- Crear, editar y eliminar productos
- Gestión de usuarios
- Subida de imágenes a Firebase
- Control del catálogo

## Cliente
- Registro e inicio de sesión
- Visualización del catálogo
- Compra de productos
- Visualización de ubicación en mapa
- Llamada directa al delivery o contacto
- Uso de cámara (según implementación: perfil o comprobación)

## Delivery
- Visualización de pedidos asignados
- Navegación con Google Maps
- Llamada directa al cliente
- Actualización del estado del pedido

---

# 🏗️ Arquitectura del Proyecto

## 📱 App Android
- Interfaz desarrollada en Kotlin
- Consumo de API REST mediante HTTP
- Integración con:
  - Google Maps SDK
  - Firebase Storage
  - Intents del sistema (cámara y llamadas)

## 🌐 Backend Node.js
- Servidor desarrollado en Node.js
- Conexión a base de datos PostgreSQL
- Endpoints REST para:
  - Login
  - Gestión de usuarios
  - Gestión de productos
  - Pedidos

## 🗄️ Base de Datos
- PostgreSQL
- Tablas principales:
  - Usuarios
  - Productos
  - Pedidos
  - Roles

---

# 🔐 Permisos Utilizados en Android

La aplicación requiere los siguientes permisos:

- INTERNET
- ACCESS_FINE_LOCATION
- CAMERA
- CALL_PHONE

Ejemplo en `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.CALL_PHONE"/>
