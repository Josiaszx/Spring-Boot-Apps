-- Autores
INSERT INTO authors (name, last_name, email, nationality, birth_date, biography) VALUES
('Gabriel', 'Garcia Marquez', 'gabriel.garcia@email.com', 'Colombiana', '1927-03-06', 'Escritor colombiano, premio Nobel de Literatura 1982'),
('Mario', 'Vargas Llosa', 'mario.vargas@email.com', 'Peruana', '1936-03-28', 'Escritor y político peruano, premio Nobel de Literatura 2010'),
('Isabel', 'Allende', 'isabel.allende@email.com', 'Chilena', '1942-08-02', 'Escritora chilena, autora de La casa de los espíritus'),
('Jorge', 'Luis Borges', 'jorge.borges@email.com', 'Argentina', '1899-08-24', 'Escritor argentino, maestro del realismo mágico y la literatura fantástica'),
('Paulo', 'Coelho', 'paulo.coelho@email.com', 'Brasileña', '1947-08-24', 'Escritor brasileño autor de El alquimista');

-- Categorías
INSERT INTO categories (name, description) VALUES
('Ficción', 'Obras de literatura ficcional'),
('No Ficción', 'Obras basadas en hechos reales'),
('Ciencia Ficción', 'Narrativas ambientadas en futuros posibles o imposibles'),
('Romance', 'Novelas centradas en relaciones amorosas'),
('Misterio', 'Historias centradas en resolver enigmas o crímenes'),
('Autoayuda', 'Libros de desarrollo personal y motivación');

-- Libros
INSERT INTO books (isbn, title, publisher, published_date, available_copies, total_copies, author_id, category_id) VALUES
('978-0307474278', 'Cien Años de Soledad', 'Editorial Sudamericana', '1967-06-05', 5, 5, 1, 1),
('978-0451524935', '1984', 'Signet Classic', '1949-06-08', 3, 3, NULL, 3),
('978-0062316097', 'La Casa de los Espíritus', 'Debolsillo', '1982-01-01', 4, 4, 3, 1),
('978-0307740999', 'El Tunel', 'Editorial Losada', '1948-01-01', 2, 2, 4, 1),
('978-0062315007', 'El Alquimista', 'HarperOne', '1988-01-01', 6, 6, 5, 4),
('978-0451526984', 'Orgullo y Prejuicio', 'Penguin Classics', '1813-01-01', 3, 3, NULL, 4),
('978-8433978787', 'La Ciudad y los Perros', 'Alfaguara', '1963-01-01', 2, 2, 2, 1),
('978-0307741002', 'El Señor de los Anillos', 'Minotauro', '1954-07-29', 4, 4, NULL, 3);

-- Miembros
INSERT INTO members (member_ship_number, first_name, last_name, email, phone, registration_date, active) VALUES
('MEM001', 'Juan', 'Pérez', 'juan.perez@email.com', '1234567890', '2024-01-15', true),
('MEM002', 'María', 'González', 'maria.gonzalez@email.com', '1234567891', '2024-02-20', true),
('MEM003', 'Carlos', 'Rodríguez', 'carlos.rodriguez@email.com', '1234567892', '2024-03-10', true),
('MEM004', 'Ana', 'Martínez', 'ana.martinez@email.com', '1234567893', '2024-04-05', true),
('MEM005', 'Luis', 'Sánchez', 'luis.sanchez@email.com', '1234567894', '2024-05-12', false);

-- Préstamos
INSERT INTO loans (book_id, member_id, loan_date, due_date, return_date, status) VALUES
(1, 1, '2025-01-10', '2025-02-10', '2025-02-08', 'CLOSED'),
(3, 2, '2025-02-15', '2025-03-15', NULL, 'ACTIVE'),
(5, 1, '2025-02-20', '2025-03-20', NULL, 'ACTIVE'),
(8, 3, '2025-01-05', '2025-02-05', '2025-02-10', 'CLOSED'),
(2, 4, '2025-01-20', '2025-02-20', NULL, 'OVERDUE'),
(6, 2, '2024-12-01', '2024-12-31', '2025-01-05', 'CLOSED');
