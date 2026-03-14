-- Departamentos
INSERT INTO departamentos (nombre, codigo, edificio, telefono, email) VALUES ('Ingeniería', 'ING-01', 'Edificio Central', '555-0101', 'ingenieria@uni.edu');
INSERT INTO departamentos (nombre, codigo, edificio, telefono, email) VALUES ('Ciencias Exactas', 'CE-02', 'Pabellón A', '555-0102', 'ciencias@uni.edu');
INSERT INTO departamentos (nombre, codigo, edificio, telefono, email) VALUES ('Humanidades', 'HUM-03', 'Pabellón B', '555-0103', 'humanidades@uni.edu');

-- Profesores
INSERT INTO profesores (nombre, apellido, codigo, email, especialidad, fecha_contratacion, departamento_id) VALUES ('Juan', 'Pérez', 'P001', 'juan.perez@uni.edu', 'Sistemas', '2020-01-15', 1);
INSERT INTO profesores (nombre, apellido, codigo, email, especialidad, fecha_contratacion, departamento_id) VALUES ('María', 'García', 'P002', 'maria.garcia@uni.edu', 'Matemáticas', '2019-03-20', 2);
INSERT INTO profesores (nombre, apellido, codigo, email, especialidad, fecha_contratacion, departamento_id) VALUES ('Carlos', 'López', 'P003', 'carlos.lopez@uni.edu', 'Literatura', '2021-08-10', 3);

-- Asignaturas
INSERT INTO asignaturas (nombre, descripcion, creditos_requeridos) VALUES ('Programación I', 'Introducción a la programación', 0);
INSERT INTO asignaturas (nombre, descripcion, creditos_requeridos) VALUES ('Cálculo I', 'Fundamentos de cálculo diferencial', 0);
INSERT INTO asignaturas (nombre, descripcion, creditos_requeridos) VALUES ('Algoritmos', 'Estructuras de datos y algoritmos', 4);

-- Cursos
INSERT INTO cursos (nombre, codigo, descripcion, creditos, cupos_maximos, cupos_disponibles, semestre, activo, profesor_id) VALUES ('Programación Básica', 'CUR-PROG1', 'Curso de fundamentos de programación', 4, 30, 30, '2024-1', true, 1);
INSERT INTO cursos (nombre, codigo, descripcion, creditos, cupos_maximos, cupos_disponibles, semestre, activo, profesor_id) VALUES ('Cálculo Diferencial', 'CUR-CALC1', 'Curso de cálculo diferencial intensivo', 5, 25, 25, '2024-1', true, 2);
INSERT INTO cursos (nombre, codigo, descripcion, creditos, cupos_maximos, cupos_disponibles, semestre, activo, profesor_id) VALUES ('Literatura Universal', 'CUR-LIT1', 'Historia de la literatura universal', 3, 40, 40, '2024-1', true, 3);

-- Relación Curso-Asignatura (Prerequisitos)
INSERT INTO curso_asignatura (curso_id, asignatura_id) VALUES (1, 1);
INSERT INTO curso_asignatura (curso_id, asignatura_id) VALUES (2, 2);

-- Perfiles Académicos
INSERT INTO perfiles_academicos (promedio_general, creditos_completados, nivel_academico, estado_academico, fecha_actualizacion) VALUES (8.5, 20, 'SEGUNDO', 'ACTIVO', '2024-03-01');
INSERT INTO perfiles_academicos (promedio_general, creditos_completados, nivel_academico, estado_academico, fecha_actualizacion) VALUES (9.2, 45, 'TERCERO', 'ACTIVO', '2024-03-05');
INSERT INTO perfiles_academicos (promedio_general, creditos_completados, nivel_academico, estado_academico, fecha_actualizacion) VALUES (7.8, 10, 'PRIMERO', 'ACTIVO', '2024-02-15');

-- Estudiantes
INSERT INTO estudiantes (nombre, apellido, codigo, email, fecha_ingreso, carrera, perfil_academico_id) VALUES ('Ana', 'Martínez', 'E001', 'ana.martinez@uni.edu', '2023-08-01', 'Sistemas', 1);
INSERT INTO estudiantes (nombre, apellido, codigo, email, fecha_ingreso, carrera, perfil_academico_id) VALUES ('Luis', 'Rodríguez', 'E002', 'luis.rodriguez@uni.edu', '2022-08-01', 'Ciencias', 2);
INSERT INTO estudiantes (nombre, apellido, codigo, email, fecha_ingreso, carrera, perfil_academico_id) VALUES ('Sofía', 'Castro', 'E003', 'sofia.castro@uni.edu', '2024-02-01', 'Humanidades', 3);

-- Matriculas
INSERT INTO matriculas (fecha_matricula, nota_final, estado, asistencias, estudiante_id, curso_id) VALUES ('2024-02-10', NULL, 'CURSANDO', 10, 1, 1);
INSERT INTO matriculas (fecha_matricula, nota_final, estado, asistencias, estudiante_id, curso_id) VALUES ('2024-02-12', NULL, 'CURSANDO', 8, 2, 2);
INSERT INTO matriculas (fecha_matricula, nota_final, estado, asistencias, estudiante_id, curso_id) VALUES ('2024-02-15', NULL, 'CURSANDO', 12, 3, 3);
