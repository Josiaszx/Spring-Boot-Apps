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

## 🛠️ Configuración Inicial

### 1. Dependencias en `pom.xml`

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
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
</dependencies>
```

### 2. Configuración en `application.properties`

```properties
spring.application.name=biblioteca-api

# H2 Database
spring.datasource.url=jdbc:h2:mem:bibliotecadb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

## 📦 Estructura del Proyecto

```
src/main/java/com/biblioteca/
├── model/
│   ├── Author.java
│   ├── Book.java
│   ├── Category.java
│   ├── Member.java
│   └── Loan.java
├── repository/
│   ├── AuthorRepository.java
│   ├── BookRepository.java
│   ├── CategoryRepository.java
│   ├── MemberRepository.java
│   └── LoanRepository.java
├── service/
│   ├── AuthorService.java
│   ├── BookService.java
│   ├── CategoryService.java
│   ├── MemberService.java
│   └── LoanService.java
├── controller/
│   ├── AuthorController.java
│   ├── BookController.java
│   ├── CategoryController.java
│   ├── MemberController.java
│   └── LoanController.java
├── dto/
│   ├── BookDTO.java
│   ├── LoanDTO.java
│   └── MemberDTO.java
└── exception/
    ├── ResourceNotFoundException.java
    └── GlobalExceptionHandler.java
```

## 💻 Implementación

### 1. Entidades (Ejemplo: Book.java)

```java
package com.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    @NotBlank(message = "ISBN es obligatorio")
    private String isbn;
    
    @NotBlank(message = "Título es obligatorio")
    private String title;
    
    private String publisher;
    
    @Temporal(TemporalType.DATE)
    private Date publishedDate;
    
    @Min(value = 0, message = "Copias disponibles no puede ser negativo")
    private Integer availableCopies;
    
    @Min(value = 0, message = "Total de copias no puede ser negativo")
    private Integer totalCopies;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Loan> loans;
}
```

### 2. Repository (Ejemplo: BookRepository.java)

```java
package com.biblioteca.repository;

import com.biblioteca.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    Optional<Book> findByIsbn(String isbn);
    
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    List<Book> findByCategoryId(Long categoryId);
    
    List<Book> findByAuthorId(Long authorId);
    
    @Query("SELECT b FROM Book b WHERE b.availableCopies > 0")
    List<Book> findAvailableBooks();
}
```

### 3. Service (Ejemplo: BookService.java)

```java
package com.biblioteca.service;

import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.model.Book;
import com.biblioteca.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con id: " + id));
    }
    
    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con ISBN: " + isbn));
    }
    
    @Transactional
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }
    
    @Transactional
    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);
        book.setTitle(bookDetails.getTitle());
        book.setIsbn(bookDetails.getIsbn());
        book.setPublisher(bookDetails.getPublisher());
        book.setPublishedDate(bookDetails.getPublishedDate());
        book.setAvailableCopies(bookDetails.getAvailableCopies());
        book.setTotalCopies(bookDetails.getTotalCopies());
        return bookRepository.save(book);
    }
    
    @Transactional
    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }
    
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
    
    public List<Book> getAvailableBooks() {
        return bookRepository.findAvailableBooks();
    }
}
```

### 4. Controller (Ejemplo: BookController.java)

```java
package com.biblioteca.controller;

import com.biblioteca.model.Book;
import com.biblioteca.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }
    
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String title) {
        return ResponseEntity.ok(bookService.searchBooksByTitle(title));
    }
    
    @GetMapping("/available")
    public ResponseEntity<List<Book>> getAvailableBooks() {
        return ResponseEntity.ok(bookService.getAvailableBooks());
    }
    
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        return new ResponseEntity<>(bookService.createBook(book), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
```

### 5. Exception Handler

```java
package com.biblioteca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
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
12. Implementar búsqueda avanzada con múltiples filtros

## 📝 Endpoints a Implementar

### Books
- `GET /api/books` - Obtener todos los libros
- `GET /api/books/{id}` - Obtener libro por ID
- `GET /api/books/isbn/{isbn}` - Obtener libro por ISBN
- `GET /api/books/search?title=xyz` - Buscar libros por título
- `GET /api/books/available` - Obtener libros disponibles
- `POST /api/books` - Crear nuevo libro
- `PUT /api/books/{id}` - Actualizar libro
- `DELETE /api/books/{id}` - Eliminar libro

### Authors
- `GET /api/authors` - Listar todos
- `GET /api/authors/{id}` - Obtener por ID
- `GET /api/authors/{id}/books` - Libros de un autor
- `POST /api/authors` - Crear autor
- `PUT /api/authors/{id}` - Actualizar
- `DELETE /api/authors/{id}` - Eliminar

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

## 🚀 Extensiones Opcionales

- Agregar autenticación con Spring Security
- Implementar tests unitarios y de integración
- Agregar documentación con Swagger/OpenAPI
- Implementar cache con Redis
- Agregar auditoría (createdAt, updatedAt)
- Sistema de multas por retraso en devoluciones