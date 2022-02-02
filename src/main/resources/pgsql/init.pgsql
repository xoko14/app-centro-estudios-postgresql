create type person_type as (dni text, nombre text, apellidos text);
create table person of person_type;

create table alumnos(
    num_exp serial,
    fecha_nac date
) inherits (person);

create table profesores
(
    id serial,
    departamento integer
) inherits (person);

create table asignaturas(
    id SERIAL,
    nombre text
);

create table departamentos(
    id SERIAL,
    nombre text
);

create table imparten(
    alumno integer,
    profesor integer,
    asignatura integer
);

create function fullname(p_row person_type) returns text
as
$$
 select concat_ws(' ', p_row.nombre, p_row.apellidos);
$$
language sql;