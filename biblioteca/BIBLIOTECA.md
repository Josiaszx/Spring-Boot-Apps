# Ejercicio: Sistema de Gestión de Biblioteca con Spring Boot REST API

## 📋 Descripción del Proyecto

Crear una API REST para gestionar una biblioteca que permita administrar libros, autores, categorías, miembros y préstamos.

## 🎯 Objetivos de Aprendizaje

- Implementar endpoints REST (GET, POST, PUT, DELETE)
- Utilizar Spring Data JPA para persistencia
- Manejar relaciones entre entidades (OneToMany, ManyToOne)
- Implementar validaciones
- Gestionar excepciones personalizadas
- Usar DTOs para transferencia de datos

## 📦 Estructura del Proyecto

```
src/main/java/com/biblioteca/
├── model/
│   ├── Author.java
│   ├── Book.java
│   ├── Category.java
│   ├── Member.java
│   ├── Loan.java
│   └── LoanStatus.java             # enum para guardar estado de prestamos
│
├── repository/
│   ├── AuthorRepository.java
│   ├── BookRepository.java
│   ├── CategoryRepository.java
│   ├── MemberRepository.java
│   └── LoanRepository.java
│
├── service/
│   ├── AuthorService.java
│   ├── BookService.java
│   ├── CategoryService.java
│   ├── MemberService.java
│   ├── LoanService.java
│   └── LoanSchedulerService.java   # servicio para verificar estado de prestamos cada 24hs.
│
├── controller/
│   ├── AuthorController.java
│   ├── BookController.java
│   ├── CategoryController.java
│   ├── MemberController.java
│   └── LoanController.java
│
├── dto/
│   ├── AuthorDTO.java
│   ├── BookDTO.java
│   ├── CategoryDTO.java
│   ├── LoanDTO.java
│   ├── MemberDTO.java
│   ├── PostBookDTO.java            # para @RequestBody de POST requests
│   └── PostLoanDTO.java            # para @RequestBody de POST requests
│
├── exception/
│   └── InvalidOperationException.java
│
└── hadler/
    └── GlobalExceptionHandler.java
```

## 🧪 Tareas del Ejercicio

### Nivel Básico
1. Implementar CRUD completo para Author, Category y Member
2. Crear endpoints REST para cada entidad
3. Probar todos los endpoints con Postman o similar

### Nivel Intermedio
4. Implementar la entidad Loan con su lógica de negocio
5. Crear endpoint para registrar un préstamo (disminuir availableCopies)
6. Crear endpoint para devolver un libro (aumentar availableCopies)
7. Validar que no se pueda prestar un libro sin copias disponibles

### Nivel Avanzado
8. Implementar DTOs para evitar exponer entidades directamente
9. Agregar paginación a los endpoints de listado
10. Crear endpoint para obtener el historial de préstamos de un miembro
11. Crear endpoint para obtener libros más prestados

## 📝 Endpoints a Implementar

### Authors
- `GET /api/authors` - Listar todos
- `GET /api/authors/{id}` - Obtener por ID
- `GET /api/authors/{id}/books` - Libros de un autor
- `POST /api/authors` - Crear autor
- `PUT /api/authors/{id}` - Actualizar
- `DELETE /api/authors/{id}` - Eliminar

### Books
- `GET /api/books` - Obtener todos los libros
- `GET /api/books/{id}` - Obtener libro por ID
- `GET /api/books/isbn/{isbn}` - Obtener libro por ISBN
- `GET /api/books/search?title=xyz` - Buscar libros por título
- `GET /api/books/available` - Obtener libros disponibles
- `POST /api/books` - Crear nuevo libro
- `PUT /api/books/{id}` - Actualizar libro
- `DELETE /api/books/{id}` - Eliminar libro

### Category
- `GET /api/categories` - listar en forma de paginas
- `POST /api/categories` - agregar categoria
- `GET /api/categories` - obtener por id
- `PUT /api/categories` - actualizar
- `DELETE /api/categories` - eliminar

### Members
- `POST api/members/` - agregar meimbro
- `GET api/members/` - mostrar los miembros paginados
- `GET api/members/id` - mostrar segun id
- `DELETE api/members/id` - eliminar
- `PUT api/members/id` - actualizar
- `GET api/members/id/loans` - mostrar prestamos de un miembro

### Loans
- `POST /api/loans` - Registrar préstamo
- `PUT /api/loans/{id}/return` - Devolver libro
- `GET /api/loans/active` - Préstamos activos
- `GET /api/loans/overdue` - Préstamos vencidos
- `GET /api/members/{id}/loans` - Historial de miembro

## 🎯 Criterios de Evaluación

- ✅ Correcta implementación de entidades con JPA
- ✅ Relaciones entre entidades funcionando
- ✅ CRUD completo para todas las entidades
- ✅ Validaciones implementadas
- ✅ Manejo de excepciones
- ✅ Código organizado y siguiendo convenciones
- ✅ Endpoints REST correctamente diseñados
