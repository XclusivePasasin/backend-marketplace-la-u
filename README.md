# Marketplace La U

API RESTful de un marketplace estudiantil que permite a los estudiantes publicar, buscar y adquirir productos. Construida con SpringBoot, SpringSecurity y JWT para autenticación, y MySQL como base de datos.

---

## Tecnologías

- Java 25
- Spring Boot 3
- Spring Security + JWT
- MySQL
- JPA / Hibernate
- Lombok
- Maven

---

## Requisitos previos

- JDK 25
- MySQL corriendo localmente
- Maven 

---

## Configuración

Crear o editar el archivo `src/main/resources/application.properties` con los siguientes valores:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/marketplace_la_u
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

app.base-url=http://localhost:8080
```
---

## Cómo correr el proyecto

```bash
# Clonar el repositorio
git clone https://github.com/tu-usuario/marketplace_la_u.git
cd marketplace_la_u

# Compilar y ejecutar
./mvnw spring-boot:run
```
---

## Endpoints

### Autenticación — `/api/auth`

| Método | Ruta | Descripción | Auth requerida |
|--------|------|-------------|----------------|
| POST | `/api/auth/login` | Iniciar sesión, retorna JWT | No |

### Usuarios — `/api/users`

| Método | Ruta | Descripción | Auth requerida |
|--------|------|-------------|----------------|
| POST | `/api/users/register` | Registrar nuevo usuario | No |
| GET | `/api/users` | Listar todos los usuarios | Sí |
| GET | `/api/users/{id}` | Consultar usuario por ID | Sí |
| PUT | `/api/users/{id}` | Actualizar usuario | Sí |
| DELETE | `/api/users/{id}` | Eliminar usuario | Sí |

### Productos — `/api/products`

| Método | Ruta | Descripción | Auth requerida |
|--------|------|-------------|----------------|
| GET | `/api/products` | Listar todos los productos | No |
| GET | `/api/products/{id}` | Consultar producto por ID | No |
| POST | `/api/products` | Crear producto | Sí |
| PUT | `/api/products/{id}` | Actualizar producto (solo el dueño) | Sí |
| DELETE | `/api/products/{id}` | Eliminar producto (solo el dueño) | Sí |

### Categorías — `/api/categories`

| Método | Ruta | Descripción | Auth requerida |
|--------|------|-------------|----------------|
| GET | `/api/categories` | Listar todas las categorías | No |
| GET | `/api/categories/{id}` | Consultar categoría por ID | No |
| POST | `/api/categories` | Crear categoría | Sí |
| PUT | `/api/categories/{id}` | Actualizar categoría | Sí |
| DELETE | `/api/categories/{id}` | Eliminar categoría | Sí |

### Órdenes — `/api/orders`

| Método | Ruta | Descripción | Auth requerida |
|--------|------|-------------|----------------|
| GET | `/api/orders` | Listar órdenes del usuario autenticado | Sí |
| GET | `/api/orders/{id}` | Consultar orden por ID | Sí |
| POST | `/api/orders` | Crear nueva orden | Sí |

### Imágenes — `/api/images`

| Método | Ruta | Descripción | Auth requerida |
|--------|------|-------------|----------------|
| POST | `/api/images/upload` | Subir imagen (WebP, JPEG, PNG — máx. 5MB) | No |

---

## Autenticación

El proyecto usa **JWT (JSON Web Token)**. Para acceder a los endpoints protegidos:

1. Hacer login en `POST /api/auth/login` con usuario y contraseña.
2. Copiar el token recibido en la respuesta.
3. Incluirlo en el header de cada petición:

---

## Estructura del proyecto

```
src/main/java/marketplace_la_u/
├── config/         # Configuración de seguridad
├── controller/     # Controladores REST
├── DTO/            # Objetos de transferencia de datos
├── error/          # Manejo de errores
├── model/          # Entidades JPA
├── repository/     # Repositorios Spring Data
├── security/       # JWT: JwtService, JwtAuthenticationFilter, SecurityConfig, StaticResourceConfig, ApplicationConfig
├── service/        # Lógica de negocio
 
```

---
