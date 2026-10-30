INSERT INTO servicio (nombre_servicio, precio)
VALUES
    ('Corte de pelo', 12.00),
    ('Lavado y peinado', 15.00),
    ('Tinte completo', 30.00),
    ('Mechas', 45.00),
    ('Corte y lavado', 18.00),
    ('Tratamiento capilar', 20.00),
    ('Recogido para eventos', 35.00),
    ('Arreglo de barba', 8.00);


--Consultar todos los servicios
SELECT *
FROM servicio;

--Consultar los servicios ordenados por precio
SELECT nombre_servicio, precio
FROM servicio
ORDER BY precio ASC;

--Consultar los servicios que cuestan menos de 20 €
SELECT *
FROM servicio
WHERE precio < 20;

--Consultar los servicios más caros
SELECT *
FROM servicio
WHERE precio >= 30
ORDER BY precio DESC;

--Buscar un servicio por su nombre
SELECT *
FROM servicio
WHERE nombre_servicio ILIKE '%corte%';