-- Inicialización de datos para la base de datos veterinaria

-- Insertar roles
INSERT INTO roles (id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id, role) VALUES (2, 'ROLE_USER');
INSERT INTO roles (id, role) VALUES (3, 'ROLE_VETERINARIAN');

-- Insertar usuarios
-- Admin (role 1)
INSERT INTO users (id, username, password, email, role_id, active, created_at) 
VALUES (1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsL8lFQqS5C2P2tQZ/YdW', 'admin@veterinaria.com', 1, true, CURRENT_DATE());

-- Dueño (role 2)
INSERT INTO users (id, username, password, email, role_id, active, created_at) 
VALUES (2, 'juan.perez', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsL8lFQqS5C2P2tQZ/YdW', 'juan.perez@email.com', 2, true, CURRENT_DATE());

-- Veterinario (role 3)
INSERT INTO users (id, username, password, email, role_id, active, created_at) 
VALUES (3, 'dra.lopez', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsL8lFQqS5C2P2tQZ/YdW', 'maria.lopez@vet.com', 3, true, CURRENT_DATE());

-- Otro dueño (role 2)
INSERT INTO users (id, username, password, email, role_id, active, created_at) 
VALUES (4, 'ana.garcia', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsL8lFQqS5C2P2tQZ/YdW', 'ana.garcia@email.com', 2, true, CURRENT_DATE());

-- Insertar dueños (owners)
INSERT INTO owners (id, user_id, name, last_name, phone_number, address) 
VALUES (1, 2, 'Juan', 'Pérez', '+1234567890', 'Calle Falsa 123, Ciudad');

INSERT INTO owners (id, user_id, name, last_name, phone_number, address) 
VALUES (2, 4, 'Ana', 'García', '+0987654321', 'Avenida Real 456, Ciudad');

-- Insertar veterinarios (veterinarians)
INSERT INTO veterinarians (id, user_id, first_name, last_name, specialty, license_number) 
VALUES (1, 3, 'María', 'López', 'Cirugía', 'VET-12345');

-- Insertar mascotas (pets)
INSERT INTO pets (id, owner_id, name, species, breed, birth_date, gender, weight) 
VALUES (1, 1, 'Max', 'Perro', 'Labrador Retriever', '2020-05-15', 'Macho', 25.5);

INSERT INTO pets (id, owner_id, name, species, breed, birth_date, gender, weight) 
VALUES (2, 1, 'Luna', 'Gato', 'Siamés', '2021-08-20', 'Hembra', 4.2);

INSERT INTO pets (id, owner_id, name, species, breed, birth_date, gender, weight) 
VALUES (3, 2, 'Rocky', 'Perro', 'Bulldog Francés', '2019-11-10', 'Macho', 12.8);

-- Insertar citas (appointments)
INSERT INTO appointments (id, pet_id, veterinarian_id, date, status, reason, notes) 
VALUES (1, 1, 1, '2024-03-15 10:30:00', 'CLOSED', 'Vacunación anual', 'Paciente en buen estado');

INSERT INTO appointments (id, pet_id, veterinarian_id, date, status, reason, notes) 
VALUES (2, 2, 1, '2024-03-16 14:00:00', 'SCHEDULED', 'Control general', 'Revisión de peso y salud');

INSERT INTO appointments (id, pet_id, veterinarian_id, date, status, reason, notes) 
VALUES (3, 3, 1, '2024-03-17 11:00:00', 'CANCELLED', 'Consulta por tos', 'Cancelada por dueño');

-- Insertar registros médicos (medical_records)
INSERT INTO medical_records (id, pet_id, veterinarian_id, appointment_id, record_date, description) 
VALUES (1, 1, 1, 1, '2024-03-15', 'Mascota saludable, se aplicó vacuna antirrábica. Comportamiento tranquilo.');

INSERT INTO medical_records (id, pet_id, veterinarian_id, appointment_id, record_date, description) 
VALUES (2, 2, 1, 2, '2024-03-16', 'Control de peso: 4.2 kg. Se recomienda dieta balanceada y ejercicio.');
