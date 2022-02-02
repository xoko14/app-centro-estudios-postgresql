create view profesoresObject as select p.id, dni, p.nombre 'nombre', apellidos, d.nombre 'departamento', p.departamento 'id_departamento'
from profesores p inner join departamentos d on p.departamento = d.id;

create view departamentosObject as select d.id, d.nombre, count(p.id) 'num_prof'
from departamentos d left join profesores p on d.id = p.departamento
group by d.id;