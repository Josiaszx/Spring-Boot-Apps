# Proyectos con Spring Boot
Este proyecto contiene 4 APIs REST independientes desarrolladas con Spring Boot, diseñadas para demostrar un dominio sólido de los conceptos fundamentales y avanzados del desarrollo backend en Java.

Cada proyecto aborda diferentes niveles de complejidad, desde CRUDs básico hasta implementación de seguridad con JWT y pruebas unitarias.

## Aplicacion de supermercado
API REST para gestionar un supermercado que permita administrar productos, sucursales y ventas.

**Requerimientos:**
- Spring Boot
- Spring Data JPA

**Implementación:**
1. [Código](supermercado/src/main/java/com/app/supermercado)
2. [Requerimientos](supermercado/SUPERMERCADO.md)
3. [Diagrama ER](supermercado/DIAGRAMA.png)

## Aplicacion de biblioteca
API REST para gestionar una biblioteca que permita administrar libros, autores, categorías, miembros y préstamos.

**Requerimientos:**
- Spring Boot
- Spring Data JPA

**Implementación:**
1. [Código](biblioteca/src/main/java/com/empresa/biblioteca)
2. [Requerimientos](biblioteca/BIBLIOTECA.md)
3. [Diagrama ER](biblioteca/DIAGRAMA.png)

## Aplicacion de universidad
API REST para gestionar una universidad que incluye estudiantes, profesores, cursos, departamentos y matrículas.

**Requerimientos:**
- Spring Boot
- Spring Data JPA
- Manejo de excepciones
- Testing unitario (JUnit con Mockito)

**Implementación:**
1. [Código](universidad/src/main/java/com/api/universidad)
2. [Requerimientos](universidad/UNIVERSIDAD.md)
3. [Diagrama ER](universidad/DIAGRAMA.png)

## Aplicacion para veterinaria
Desarrollar una API REST para una clínica veterinaria que permita gestionar mascotas, dueños, veterinarios, citas médicas e historiales clínicos. El sistema debe implementar autenticación y autorización con diferentes roles de usuario.

**Requerimientos:**
- Spring Boot
- Spring Data JPA
- Spring Security (Autenticación y autorización mediante JWT)
- Manejo de excepciones
- Testing unitario

**Implementación:**
1. [Código](veterinaria/src/main/java/com/app/veterinaria)
2. [Requerimientos](veterinaria/VETERINARIA.md)
3. [Diagrama ER](veterinaria/DIAGRAMA.png)