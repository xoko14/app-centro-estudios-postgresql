delete from imparten where 1=1;
delete from alumnos where 1=1;
delete from profesores where 1=1;
delete from departamentos where 1=1;
delete from asignaturas where 1=1;

ALTER TABLE imparten AUTO_INCREMENT = 1;
ALTER TABLE alumnos AUTO_INCREMENT = 1;
ALTER TABLE profesores AUTO_INCREMENT = 1;
ALTER TABLE departamentos AUTO_INCREMENT = 1;
ALTER TABLE asignaturas AUTO_INCREMENT = 1;