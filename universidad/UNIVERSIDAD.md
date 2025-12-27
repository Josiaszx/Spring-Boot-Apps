# Ejercicio: Sistema de Gestión de Universidad con Spring Boot REST API

## Descripción del Proyecto

Desarrollarás una API REST para gestionar una universidad que incluye estudiantes, profesores, cursos, departamentos y matrículas.

## Diagrama de Entidades

## Relaciones del Modelo

### 1. Relación 1:1 (Uno a Uno)
- **Estudiante ↔ PerfilAcademico**: Cada estudiante tiene un único perfil académico con su información de rendimiento

### 2. Relación 1:N (Uno a Muchos)
- **Departamento ↔ Profesor**: Un departamento tiene múltiples profesores
- **Profesor ↔ Curso**: Un profesor imparte múltiples cursos
- **Estudiante ↔ Matricula**: Un estudiante tiene múltiples matrículas
- **Curso ↔ Matricula**: Un curso tiene múltiples estudiantes matriculados

### 3. Relación N:N (Muchos a Muchos)
- **Curso ↔ Asignatura**: Un curso puede requerir múltiples asignaturas como prerrequisitos, y una asignatura puede ser prerrequisito de múltiples cursos

## Estructura del Proyecto

```
src/main/java/com/universidad/
├── controller/
│   ├── EstudianteController.java
│   ├── ProfesorController.java
│   ├── CursoController.java
│   ├── DepartamentoController.java
│   ├── MatriculaController.java
│   └── AsignaturaController.java
├── service/
│   ├── EstudianteService.java
│   ├── ProfesorService.java
│   ├── CursoService.java
│   ├── DepartamentoService.java
│   ├── MatriculaService.java
│   └── AsignaturaService.java
├── repository/
│   ├── EstudianteRepository.java
│   ├── PerfilAcademicoRepository.java
│   ├── ProfesorRepository.java
│   ├── CursoRepository.java
│   ├── DepartamentoRepository.java
│   ├── MatriculaRepository.java
│   └── AsignaturaRepository.java
├── model/
│   ├── Estudiante.java
│   ├── PerfilAcademico.java
│   ├── Profesor.java
│   ├── Curso.java
│   ├── Departamento.java
│   ├── Matricula.java
│   └── Asignatura.java
├── dto/
│   ├── EstudianteDTO.java
│   ├── CursoDTO.java
│   ├── MatriculaDTO.java
│   ├── ProfesorDTO.java
│   └── ...
└── exception/
    ├── GlobalExceptionHandler.java
    ├── ResourceNotFoundException.java
    ├── BusinessException.java
    └── ValidationException.java
```

## Endpoints a Implementar

### **Estudiantes** (`/api/estudiantes`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/estudiantes` | Listar todos los estudiantes (paginado) |
| GET | `/api/estudiantes/{id}` | Obtener estudiante por ID con su perfil |
| GET | `/api/estudiantes/codigo/{codigo}` | Buscar estudiante por código |
| GET | `/api/estudiantes/{id}/matriculas` | Obtener matrículas de un estudiante |
| GET | `/api/estudiantes/{id}/perfil` | Obtener perfil académico del estudiante |
| POST | `/api/estudiantes` | Crear nuevo estudiante con perfil |
| PUT | `/api/estudiantes/{id}` | Actualizar estudiante |
| PATCH | `/api/estudiantes/{id}/perfil` | Actualizar perfil académico |
| DELETE | `/api/estudiantes/{id}` | Eliminar estudiante |

### **Profesores** (`/api/profesores`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/profesores` | Listar todos los profesores (paginado) |
| GET | `/api/profesores/{id}` | Obtener profesor por ID |
| GET | `/api/profesores/{id}/cursos` | Obtener cursos que imparte un profesor |
| GET | `/api/profesores/departamento/{deptoId}` | Listar profesores por departamento |
| POST | `/api/profesores` | Crear nuevo profesor |
| PUT | `/api/profesores/{id}` | Actualizar profesor |
| PUT | `/api/profesores/{id}/departamento` | Asignar profesor a departamento |
| DELETE | `/api/profesores/{id}` | Eliminar profesor |

### **Cursos** (`/api/cursos`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/cursos` | Listar todos los cursos (con filtros: semestre, profesor) |
| GET | `/api/cursos/{id}` | Obtener curso por ID |
| GET | `/api/cursos/codigo/{codigo}` | Buscar curso por código |
| GET | `/api/cursos/{id}/estudiantes` | Obtener estudiantes matriculados en un curso |
| GET | `/api/cursos/{id}/prerequisitos` | Obtener asignaturas prerrequisito |
| GET | `/api/cursos/disponibles` | Listar cursos con cupos disponibles |
| POST | `/api/cursos` | Crear nuevo curso |
| PUT | `/api/cursos/{id}` | Actualizar curso |
| PUT | `/api/cursos/{id}/prerequisitos` | Asignar asignaturas prerrequisito |
| PATCH | `/api/cursos/{id}/profesor` | Asignar/cambiar profesor del curso |
| DELETE | `/api/cursos/{id}` | Eliminar curso |

### **Departamentos** (`/api/departamentos`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/departamentos` | Listar todos los departamentos |
| GET | `/api/departamentos/{id}` | Obtener departamento por ID |
| GET | `/api/departamentos/{id}/profesores` | Obtener profesores de un departamento |
| POST | `/api/departamentos` | Crear nuevo departamento |
| PUT | `/api/departamentos/{id}` | Actualizar departamento |
| DELETE | `/api/departamentos/{id}` | Eliminar departamento |

### **Matrículas** (`/api/matriculas`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/matriculas` | Listar todas las matrículas (paginado) |
| GET | `/api/matriculas/{id}` | Obtener matrícula por ID |
| GET | `/api/matriculas/estudiante/{estudianteId}` | Matrículas de un estudiante |
| GET | `/api/matriculas/curso/{cursoId}` | Matrículas de un curso |
| POST | `/api/matriculas` | Crear nueva matrícula |
| PUT | `/api/matriculas/{id}` | Actualizar matrícula |
| PATCH | `/api/matriculas/{id}/nota` | Registrar nota final |
| PATCH | `/api/matriculas/{id}/asistencia` | Registrar asistencia |
| DELETE | `/api/matriculas/{id}` | Eliminar matrícula |

### **Asignaturas** (`/api/asignaturas`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/asignaturas` | Listar todas las asignaturas |
| GET | `/api/asignaturas/{id}` | Obtener asignatura por ID |
| GET | `/api/asignaturas/{id}/cursos` | Cursos que requieren esta asignatura |
| POST | `/api/asignaturas` | Crear nueva asignatura |
| PUT | `/api/asignaturas/{id}` | Actualizar asignatura |
| DELETE | `/api/asignaturas/{id}` | Eliminar asignatura |

## Requisitos Funcionales

### 1. **Validaciones de Negocio**

- No permitir matricular a un estudiante en un curso sin cupos disponibles
- Validar que el estudiante cumpla con los prerrequisitos antes de matricularse
- No permitir que un profesor tenga más de 5 cursos activos simultáneamente
- El código de estudiante, profesor y curso debe ser único
- El email debe ser único para estudiantes y profesores
- La nota final debe estar entre 0 y 100
- No permitir eliminar un departamento si tiene profesores asignados

### 2. **Manejo de Excepciones**

Debes crear las siguientes excepciones personalizadas:

- **ResourceNotFoundException**: Cuando no se encuentra un recurso (404)
- **BusinessException**: Para reglas de negocio violadas (400)
- **ValidationException**: Para errores de validación de datos (400)
- **DuplicateResourceException**: Para recursos duplicados (409)
- **InsufficientCapacityException**: Cuando no hay cupos disponibles (400)

### 3. **GlobalExceptionHandler**

Debe manejar:
- Excepciones personalizadas
- `MethodArgumentNotValidException` (errores de validación @Valid)
- `DataIntegrityViolationException` (errores de BD)
- `Exception` (errores genéricos)

Respuesta estándar de error:
```json
{
  "timestamp": "2024-12-26T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Estudiante con ID 999 no encontrado",
  "path": "/api/estudiantes/999"
}
```

### 4. **Testing Unitario**

Debes implementar tests para:

**Service Layer:**
- Crear, actualizar, eliminar y buscar entidades
- Validaciones de reglas de negocio
- Manejo de excepciones

**Controller Layer:**
- Todos los endpoints con MockMvc
- Casos exitosos y casos de error
- Validación de respuestas HTTP correctas

**Ejemplo de tests requeridos:**
```java
// EstudianteServiceTest
- testCrearEstudianteExitoso()
- testCrearEstudianteConEmailDuplicado()
- testActualizarPerfilAcademico()
- testObtenerEstudiantePorId()
- testEliminarEstudianteNoExistente()

// MatriculaServiceTest
- testMatricularEstudianteEnCurso()
- testMatricularSinCuposDisponibles()
- testMatricularSinPrerrequisitos()
- testRegistrarNotaFinal()

// CursoControllerTest
- testListarCursosConPaginacion()
- testCrearCurso()
- testActualizarProfesorDelCurso()
- testObtenerCursoNoExistente()
```

## Configuración Adicional

### application.properties
```properties
spring.datasource.url=jdbc:h2:mem:universidad
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.h2.console.enabled=true
```

### Dependencias Maven
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Entregables

1. Código fuente completo con todas las entidades, repositorios, servicios y controladores
2. Tests unitarios con cobertura mínima del 80%
3. Manejo de excepciones implementado
4. Colección de Postman con ejemplos de todos los endpoints
5. README con instrucciones de ejecución

¡Éxito con el ejercicio! Este proyecto te dará una comprensión sólida de las relaciones JPA y desarrollo de APIs REST con Spring Boot.