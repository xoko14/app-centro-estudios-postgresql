select * from alumnos a 
inner join imparten i on a.num_exp = i.alumno 
inner join profesores p on i.profesor = p.id 
where p.dni = "87654321Z" 
group by a.num_exp