# Ejercicio Práctico: Sistema de Gestión de Clínica Veterinaria

## 📋 Descripción del Proyecto

Desarrollar una API REST para una clínica veterinaria que permita gestionar mascotas, dueños, veterinarios, citas médicas e historiales clínicos. El sistema debe implementar autenticación y autorización con diferentes roles de usuario.

## 🎯 Objetivos de Aprendizaje

- Implementar relaciones complejas entre entidades (OneToMany, ManyToOne, ManyToMany)
- Configurar Spring Security con JWT
- Crear tests unitarios y de integración
- Manejar excepciones personalizadas
- Aplicar buenas prácticas de arquitectura en capas


## 🔐 Roles y Permisos

### Roles del Sistema

1. **ADMIN**: Acceso total al sistema
2. **VETERINARIAN**: Puede gestionar citas, mascotas y registros médicos
3. **OWNER**: Puede ver sus mascotas y solicitar citas

### Matriz de Permisos

| Funcionalidad | ADMIN | VETERINARIAN | OWNER |
|--------------|-------|--------------|-------|
| Crear usuarios | ✅ | ❌ | ❌ |
| Ver todos los dueños | ✅ | ✅ | ❌ |
| Ver sus propias mascotas | ✅ | ✅ | ✅ |
| Registrar mascotas | ✅ | ✅ | ✅ |
| Crear citas | ✅ | ✅ | ✅ |
| Ver todas las citas | ✅ | ✅ | ❌ |
| Crear registros médicos | ✅ | ✅ | ❌ |
| Ver historiales médicos | ✅ | ✅ | ✅ (solo sus mascotas) |

## 🔌 Endpoints Requeridos

### 1. Autenticación (`/api/auth`)

- **Response**: `201 Created`

#### POST `/api/auth/login`
- **Descripción**: Iniciar sesión
- **Acceso**: Público
- **Request Body**:
```json
{
   "username": "juanperez",
   "password": "password123"
}
```
- **Response**: `200 OK`
```json
{
   "token": "eyJhbGciOiJIUzI1NiIs...",
   "type": "Bearer",
   "username": "juanperez",
   "role": "OWNER"
}
```

### 2. Usuarios (`/api/users`)

#### POST `/api/users/veterinarian`
- **Descripción**: Crear un veterinario
- **Acceso**: ADMIN
- **Request Body**:
```json
{
   "username": "dra.garcia",
   "password": "vet123",
   "email": "garcia@clinica.com",
   "firstName": "María",
   "lastName": "García",
   "specialty": "Cirugía",
   "licenseNumber": "VET-12345"
}
```

#### GET `/api/users/me`
- **Descripción**: Obtener perfil del usuario autenticado
- **Acceso**: Todos los roles autenticados

### 3. Dueños (`/api/owners`)

#### GET `/api/owners`
- **Descripción**: Listar todos los dueños
- **Acceso**: ADMIN, VETERINARIAN
- **Query Params**: `page`, `size`, `sort`

#### GET `/api/owners/{id}`
- **Descripción**: Obtener un dueño por ID
- **Acceso**: ADMIN, VETERINARIAN, OWNER (solo su propio perfil)

#### PUT `/api/owners/{id}`
- **Descripción**: Actualizar información del dueño
- **Acceso**: ADMIN, OWNER (solo su propio perfil)

#### POST `api/owners`
- **Descripción**: Registrar nuevo dueño
- **Acceso**: ADMIN, VETERINARIAN
- **Request Body:**
```json
{
   "phoneNumber": "093232",
   "address": "Asuncion",
   "username": "carlos01",
   "firstName": "Carlos",
   "lastName": "Lopez",
   "password": "1234",
   "email": "car@gmail.com"
}
```

### 4. Mascotas (`/api/pets`)

#### POST `/api/pets`
- **Descripción**: Registrar una nueva mascota
- **Acceso**: ADMIN, VETERINARIAN, OWNER
- **Request Body**:
```json
{
   "name": "Rocky",
   "species": "Perro",
   "breed": "Golden Retriever",
   "birthDate": "2020-05-15",
   "gender": "Macho",
   "weight": 28.5
}
```

#### GET `/api/pets`
- **Descripción**: Listar mascotas
- **Acceso**: ADMIN (todas), VETERINARIAN (todas), OWNER (solo sus mascotas)
- **Query Params**: `ownerId`

#### GET `/api/pets/{id}`
- **Descripción**: Obtener una mascota por ID
- **Acceso**: ADMIN, VETERINARIAN, OWNER (solo sus mascotas)

#### PUT `/api/pets/{id}`
- **Descripción**: Actualizar información de mascota
- **Acceso**: ADMIN, VETERINARIAN, OWNER (solo sus mascotas)

#### DELETE `/api/pets/{id}`
- **Descripción**: Eliminar una mascota
- **Acceso**: ADMIN, OWNER (solo sus mascotas)

### 5. Veterinarios (`/api/veterinarians`)

#### GET `/api/veterinarians`
- **Descripción**: Listar todos los veterinarios
- **Acceso**: Todos los roles autenticados
- **Query Params**: `specialty`

#### GET `/api/veterinarians/{id}`
- **Descripción**: Obtener veterinario por ID
- **Acceso**: Todos los roles autenticados

#### GET `/api/veterinarians/{id}/appointments`
- **Descripción**: Obtener citas de un veterinario
- **Acceso**: ADMIN, VETERINARIAN (solo sus propias citas)
- **Query Params**: `date`, `status`

### 6. Citas (`/api/appointments`)

#### POST `/api/appointments`
- **Descripción**: Crear una nueva cita
- **Acceso**: ADMIN, VETERINARIAN, OWNER
- **Request Body**:
```json
{
   "petId": 1,
   "veterinarianId": 2,
   "appointmentDate": "2026-01-10T10:00:00",
   "reason": "Vacunación anual"
}
```

#### GET `/api/appointments`
- **Descripción**: Listar citas
- **Acceso**: ADMIN (todas), VETERINARIAN (todas), OWNER (solo de sus mascotas)
- **Query Params**: `petId`, `veterinarianId`, `status`, `date`, `page`, `size`

#### GET `/api/appointments/{id}`
- **Descripción**: Obtener cita por ID
- **Acceso**: ADMIN, VETERINARIAN, OWNER (solo citas de sus mascotas)

#### PUT `/api/appointments/{id}/status`
- **Descripción**: Cambiar estado de cita (PENDING, CONFIRMED, COMPLETED, CANCELLED)
- **Acceso**: ADMIN, VETERINARIAN

#### DELETE `/api/appointments/{id}`
- **Descripción**: Cancelar una cita
- **Acceso**: ADMIN, OWNER (solo sus citas si está PENDING)

### 7. Registros Médicos (`/api/medical-records`)

#### POST `/api/medical-records`
- **Descripción**: Crear un registro médico
- **Acceso**: ADMIN, VETERINARIAN
- **Request Body**:
```json
{
   "petId": 1,
   "appointmentId": 5,
   "diagnosis": "Gastroenteritis leve",
   "treatment": "Dieta blanda por 3 días",
   "prescriptions": "Metoclopramida 10mg cada 12h",
   "observations": "Mejoría esperada en 48-72 horas"
}
```

#### GET `/api/medical-records`
- **Descripción**: Listar registros médicos
- **Acceso**: ADMIN (todos), VETERINARIAN (todos), OWNER (solo de sus mascotas)
- **Query Params**: `petId`, `veterinarianId`, `startDate`, `endDate`

#### GET `/api/medical-records/{id}`
- **Descripción**: Obtener registro médico por ID
- **Acceso**: ADMIN, VETERINARIAN, OWNER (solo de sus mascotas)

#### GET `/api/medical-records/pet/{petId}`
- **Descripción**: Obtener historial médico completo de una mascota
- **Acceso**: ADMIN, VETERINARIAN, OWNER (solo sus mascotas)

#### PUT `/api/medical-records/{id}`
- **Descripción**: Actualizar registro médico
- **Acceso**: ADMIN, VETERINARIAN (solo sus propios registros)

## 🧪 Requisitos de Testing

### Tests Unitarios Requeridos

1. **Services**:
   - Verificar lógica de negocio de cada servicio
   - Mockear repositorios con Mockito
   - Probar casos de éxito y error
   - Ejemplo: `PetServiceTest`, `AppointmentServiceTest`

2. **Security**:
   - Verificar generación y validación de JWT
   - Probar carga de UserDetails
   - Validar roles y permisos

3. **Validators**:
   - Validar reglas de negocio (fechas, estados, etc.)
   - Ejemplo: No permitir citas en el pasado


## ⚠️ Manejo de Errores

### Excepciones Personalizadas a Implementar

```java
// Jerarquía de excepciones
- VeterinaryException (base)
  ├── ResourceNotFoundException
  ├── InvalidOperationException
  ├── DuplicateResourceException
  └── ValidationException
```

### Escenarios de Error a Manejar

1. **Autenticación/Autorización**:
   - Usuario no autenticado (401)
   - Permisos insuficientes (403)
   - Token expirado (401)

2. **Recursos no encontrados** (404):
   - Mascota no existe
   - Cita no existe
   - Usuario no encontrado

3. **Validaciones de negocio** (400):
   - Cita en fecha pasada
   - Mascota ya tiene cita en ese horario
   - Veterinario no disponible
   - Dueño intentando acceder a mascota de otro

4. **Conflictos** (409):
   - Username o email duplicado
   - Número de licencia duplicado

5. **Errores del servidor** (500):
   - Error de base de datos
   - Error inesperado

### Estructura de Respuesta de Error

```json
{
   "timestamp": "2026-01-05T10:30:00",
   "status": 404,
   "error": "Not Found",
   "message": "Mascota con ID 999 no encontrada",
   "path": "/api/pets/999"
}
```

## 📦 Estructura del Proyecto

```
src/main/java/com/veterinary/
├── config/
│   ├── SecurityConfig.java
│   └── JwtConfig.java
├── controller/
│   ├── AuthController.java
│   ├── PetController.java
│   ├── AppointmentController.java
│   └── MedicalRecordController.java
├── dto/
│   ├── request/
│   └── response/
├── entity/
│   ├── User.java
│   ├── Owner.java
│   ├── Veterinarian.java
│   ├── Pet.java
│   ├── Appointment.java
│   └── MedicalRecord.java
├── repository/
├── service/
│   ├── impl/
│   └── interfaces/
├── security/
│   ├── JwtTokenProvider.java
│   └── JwtAuthenticationFilter.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   └── custom exceptions
└── validation/
```

## 🎯 Criterios de Evaluación

- **Relaciones JPA (25%)**: Correcta implementación de @OneToMany, @ManyToOne, etc.
- **Spring Security (25%)**: JWT, roles, autorización por endpoint
- **Testing (20%)**: Cobertura mínima 70%, casos críticos cubiertos
- **Manejo de errores (15%)**: Excepciones personalizadas, responses claras
- **Código limpio (15%)**: Arquitectura en capas, principios SOLID

## 💡 Consejos de Implementación

1. Comienza por las entidades y relaciones
2. Implementa la autenticación básica antes que los endpoints
3. Crea los servicios con su lógica de negocio
4. Implementa los controllers con las validaciones de seguridad
5. Escribe los tests conforme avanzas
6. Agrega el manejo de errores al final

¡Buena suerte con el ejercicio! 🚀