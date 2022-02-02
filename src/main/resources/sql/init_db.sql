CREATE TABLE alumnos(
    num_exp INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    dni CHAR(9),
    nombre VARCHAR(255),
    apellidos VARCHAR(255),
    fecha_nac DATE
);
CREATE TABLE asignaturas(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255)
);
CREATE TABLE departamentos(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255)
);
CREATE TABLE profesores(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    dni CHAR(9),
    nombre VARCHAR(255),
    apellidos VARCHAR(255),
    departamento INT,
    FOREIGN KEY(departamento) REFERENCES departamentos(id)
);
CREATE TABLE imparten(
    profesor INT NOT NULL,
    asignatura INT NOT NULL,
    alumno INT NOT NULL,
    curso INT NOT NULL,
    PRIMARY KEY (profesor, asignatura, alumno, curso),
    FOREIGN KEY(profesor) REFERENCES profesores(id),
    FOREIGN KEY(asignatura) REFERENCES asignaturas(id),
    FOREIGN KEY(alumno) REFERENCES alumnos(num_exp)
);