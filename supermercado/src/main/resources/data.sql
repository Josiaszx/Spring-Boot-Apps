-- Inicialización de Sucursales
INSERT INTO sucursales (nombre, direccion) VALUES ('Sucursal Central', 'Av. Siempre Viva 123');
INSERT INTO sucursales (nombre, direccion) VALUES ('Sucursal Norte', 'Calle Falsa 456');
INSERT INTO sucursales (nombre, direccion) VALUES ('Sucursal Sur', 'Av. de los Próceres 789');

-- Inicialización de Productos
INSERT INTO productos (nombre, categoria, precio, cantidad) VALUES ('Leche Entera 1L', 'Lácteos', 1200, 50);
INSERT INTO productos (nombre, categoria, precio, cantidad) VALUES ('Pan Tajado', 'Panadería', 3500, 30);
INSERT INTO productos (nombre, categoria, precio, cantidad) VALUES ('Arroz 1kg', 'Granos', 4000, 100);
INSERT INTO productos (nombre, categoria, precio, cantidad) VALUES ('Aceite Girasol 900ml', 'Abarrotes', 8500, 40);
INSERT INTO productos (nombre, categoria, precio, cantidad) VALUES ('Detergente en Polvo 1kg', 'Limpieza', 12000, 25);
INSERT INTO productos (nombre, categoria, precio, cantidad) VALUES ('Manzana Roja kg', 'Frutas', 6000, 20);

-- Inicialización de Ventas
-- Venta 1: Sucursal Central (id: 1), Fecha: Hoy
INSERT INTO ventas (fecha, estado, sucursal_id, total) VALUES (CURRENT_DATE, 'FINALIZADA', 1, 5900);
-- Detalle Venta 1: 2 Leches (2 * 1200) + 1 Pan (1 * 3500) = 2400 + 3500 = 5900
INSERT INTO detalle_venta (producto_id, venta_id, cantidad) VALUES (1, 1, 2);
INSERT INTO detalle_venta (producto_id, venta_id, cantidad) VALUES (2, 1, 1);

-- Venta 2: Sucursal Norte (id: 2), Fecha: Hoy
INSERT INTO ventas (fecha, estado, sucursal_id, total) VALUES (CURRENT_DATE, 'FINALIZADA', 2, 16500);
-- Detalle Venta 2: 2 Arroz (2 * 4000) + 1 Aceite (1 * 8500) = 8000 + 8500 = 16500
INSERT INTO detalle_venta (producto_id, venta_id, cantidad) VALUES (3, 2, 2);
INSERT INTO detalle_venta (producto_id, venta_id, cantidad) VALUES (4, 2, 1);

-- Venta 3: Sucursal Sur (id: 3), Fecha: 2026-03-13 (Ayer)
INSERT INTO ventas (fecha, estado, sucursal_id, total) VALUES ('2026-03-13', 'FINALIZADA', 3, 24000);
-- Detalle Venta 3: 2 Detergentes (2 * 12000) = 24000
INSERT INTO detalle_venta (producto_id, venta_id, cantidad) VALUES (5, 3, 2);
