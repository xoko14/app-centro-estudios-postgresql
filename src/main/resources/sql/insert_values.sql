INSERT INTO alumnos(dni, nombre, apellidos, fecha_nac) VALUES
("12345678A", "Carla", "Gómez Pais", "1999-06-25"),
("12345678B", "Alex", "Sánchez González", "2001-07-08"),
("12345678C", "Pedro", "Romero Fuentes", "2002-11-15"),
("12345678D", "Laura", "Ferreiro Outeiro", "1999-02-22"),
("12345678E", "Ariel", "López Suárez", "2001-05-14"),
("12345678F", "Xoán", "Blanco Do Val", "2003-10-23");

INSERT INTO departamentos (nombre) VALUES
("Matemáticas"),
("Informática"),
("Castelán"),
("Galego"),
("Inglés"),
("Historia"),
("Xeografía"),
("Filosofía");

INSERT INTO profesores (dni, nombre, apellidos, departamento) VALUES
("87654321Z", "Fabiola", "Reig", 1),
("87654321Y", "Sofía", "Mir", 2),
("87654321X", "Emiliano", "Ordoñez", 3),
("87654321W", "Nil", "Rivera", 4),
("87654321V", "Samuel", "Ferreiro", 5),
("87654321U", "Mary", "Fernandez", 6),
("87654321T", "Marcelino", "Valencia", 7),
("87654321S", "Isidora", "Carballo", 8),
("87654321R", "Juana", "Vargas", 8);

INSERT INTO asignaturas (nombre) VALUES
("Lengua Castellana"),
("Lingua Galega"),
("Matemáticas Académicas"),
("Matemáticas Aplicadas"),
("Filosofía"),
("Valores Éticos"),
("TIC"),
("Inglés"),
("Historia Contemporánea"),
("Historia do Arte"),
("Xeografía");

insert into imparten VALUES
(1, 3, 1, 2021),
(1, 3, 4, 2021),
(2, 7, 1, 2021),
(2, 7, 2, 2021),
(2, 7, 3, 2021),
(2, 7, 4, 2021),
(2, 7, 5, 2021),
(2, 7, 6, 2021);