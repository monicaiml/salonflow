# 💇 SalonFlow

## 📌 Descripción del proyecto

SalonFlow es una aplicación web destinada a facilitar la gestión de una peluquería. Su objetivo es centralizar la información de clientes, peluqueros, servicios, citas y facturas, facilitando la organización del negocio.

La aplicación cuenta con una interfaz web conectada a un backend desarrollado en Java y una base de datos PostgreSQL.

## 🎯 Objetivos

- Permitir la gestión de los clientes y sus datos.
- Facilitar la organización de los servicios y las citas.
- Centralizar la información necesaria para la gestión de la peluquería.
- Reducir errores y mejorar la organización del trabajo.
- Desarrollar una aplicación web clara, intuitiva y adaptable a distintos dispositivos.

## ⚙️ Tecnologías utilizadas

- **Java:** desarrollo de la lógica del backend.
- **Spring Boot:** creación y configuración de la aplicación y sus servicios REST.
- **PostgreSQL:** almacenamiento y gestión de los datos.
- **JDBC:** conexión entre Java y la base de datos.
- **HTML y CSS:** estructura y diseño de la interfaz web.
- **JavaScript:** interactividad y comunicación con el backend.
- **Maven:** gestión de dependencias y construcción del proyecto.
- **Git y GitHub:** control de versiones y colaboración entre los integrantes del equipo.
- **Taiga:** planificación y seguimiento del trabajo mediante Scrum.

## 🏗️ Estructura del proyecto

El proyecto está organizado en los siguientes directorios:

- `BBDD/`: contiene los scripts SQL de la base de datos.
- `src/main/java/conexion/`: configuración y pruebas de conexión con PostgreSQL.
- `src/main/java/controller/`: controladores de la API REST.
- `src/main/java/dao/`: clases de acceso a datos.
- `src/main/java/modelo/`: clases que representan las entidades del proyecto.
- `src/main/java/salonflow/backend/`: clase principal de la aplicación.
- `src/main/resources/static/`: archivos del frontend, HTML, CSS y JavaScript.

## 🗄️ Base de datos

El proyecto utiliza PostgreSQL. El script principal para crear la estructura de la base de datos se encuentra en:

`SalonFLow/BBDD/base-datos-peluqueria.sql`

Para conectar la aplicación es necesario configurar el archivo local `src/main/resources/conexion/db.properties` con los siguientes datos:

```properties
db.url=jdbc:postgresql://localhost:5432/nombre_base_datos
db.user=usuario
db.password=contraseña
```

Los valores deben adaptarse a la configuración de PostgreSQL de cada equipo.

**Importante:** el archivo `db.properties` contiene credenciales y no debe subirse al repositorio.

## 🚀 Instalación y ejecución

### Requisitos previos

- Java 17 o superior.
- Maven.
- PostgreSQL.

### Pasos para ejecutar el proyecto

1. Clonar el repositorio de GitHub.
2. Crear la base de datos en PostgreSQL.
3. Ejecutar el script SQL principal incluido en la carpeta `BBDD`.
4. Crear y configurar el archivo `db.properties` con los datos de conexión.
5. Abrir una terminal en la carpeta `SalonFLow`.
6. Ejecutar la aplicación con Maven:

```bash
mvn spring-boot:run
```

7. Abrir el navegador y acceder a:

`http://localhost:8080`

La API de clientes está disponible en:

`http://localhost:8080/api/clientes`

## 📚 Documentación

La documentación del proyecto incluye:

- Introducción y requisitos funcionales y no funcionales.
- Historias de usuario y estimación de puntos.
- Estrategia de Git y organización del trabajo.
- Tecnologías seleccionadas y arquitectura.
- Solución implementada y capturas de pantalla.
- Diagramas técnicos y de datos.
- Conclusiones y posibles mejoras.
- Documentación de Scrum, sprints, reuniones Daily y seguimiento del proyecto.

## 📊 Estado del proyecto

El proyecto se encuentra en fase de desarrollo. Se ha implementado la estructura de la base de datos, la conexión con PostgreSQL y parte de la comunicación entre el frontend y el backend mediante Spring Boot.

Actualmente están comprobadas las operaciones de consulta y registro de clientes. Otras funcionalidades y operaciones de gestión se encuentran pendientes de completar o verificar.

## 👥 Equipo

- **Marc Bonet**
- **Isabel Martínez**

---

*Proyecto académico realizado en equipo.*
