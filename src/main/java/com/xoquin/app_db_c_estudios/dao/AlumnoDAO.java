package com.xoquin.app_db_c_estudios.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.xoquin.app_db_c_estudios.util.ExceptionHandler;
import com.xoquin.app_db_c_estudios.vo.Alumno;

import org.json.JSONArray;
import org.json.JSONObject;

public class AlumnoDAO implements Dao<Alumno> {
    public static String ROW_NOMBRE = "nombre";
    public static String ROW_APELLIDOS = "apellidos";

    @Override
    public Alumno get(Connection conn, int id) {
        Alumno al = new Alumno();
        try {
            PreparedStatement s = conn.prepareStatement("select * from alumnos where num_exp = ?");
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            rs.next();
            al.setNumExp(rs.getInt("num_exp"));
            al.setDni(rs.getString("dni"));
            al.setNombre(rs.getString("nombre"));
            al.setApellidos(rs.getString("apellidos"));
            al.setFecha(rs.getDate("fecha_nac"));
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return al;
    }

    @Override
    public List<Alumno> getAll(Connection conn) {
        List<Alumno> lista = new ArrayList<>();
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("select * from alumnos");
            while (rs.next()) {
                Alumno al = new Alumno();
                al.setNumExp(rs.getInt("num_exp"));
                al.setDni(rs.getString("dni"));
                al.setNombre(rs.getString("nombre"));
                al.setApellidos(rs.getString("apellidos"));
                al.setFecha(rs.getDate("fecha_nac"));
                lista.add(al);
            }
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return lista;
    }

    public List<Alumno> getByDNI(Connection conn, String query) {
        List<Alumno> lista = new ArrayList<>();
        try {
            PreparedStatement s = conn.prepareStatement("select * from alumnos where dni = ?");
            s.setString(1, query);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Alumno al = new Alumno();
                al.setNumExp(rs.getInt("num_exp"));
                al.setDni(rs.getString("dni"));
                al.setNombre(rs.getString("nombre"));
                al.setApellidos(rs.getString("apellidos"));
                al.setFecha(rs.getDate("fecha_nac"));
                lista.add(al);
            }
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return lista;
    }

    public List<Alumno> getByRowLike(Connection conn, String row, String query) {
        List<Alumno> lista = new ArrayList<>();
        try {
            PreparedStatement s = conn.prepareStatement("select * from alumnos where upper(" + row + ") like upper(?)");
            s.setString(1, "%" + query + "%");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Alumno al = new Alumno();
                al.setNumExp(rs.getInt("num_exp"));
                al.setDni(rs.getString("dni"));
                al.setNombre(rs.getString("nombre"));
                al.setApellidos(rs.getString("apellidos"));
                al.setFecha(rs.getDate("fecha_nac"));
                lista.add(al);
            }
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return lista;
    }

    public List<Alumno> getByYear(Connection conn, String query) {
        List<Alumno> lista = new ArrayList<>();
        try {
            PreparedStatement s = conn.prepareStatement("select * from alumnos where fecha_nac regexp ?");
            s.setString(1, query + "-[0-9][0-9]-[0-9][0-9]");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Alumno al = new Alumno();
                al.setNumExp(rs.getInt("num_exp"));
                al.setDni(rs.getString("dni"));
                al.setNombre(rs.getString("nombre"));
                al.setApellidos(rs.getString("apellidos"));
                al.setFecha(rs.getDate("fecha_nac"));
                lista.add(al);
            }
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return lista;
    }

    public List<Alumno> getByProfesor(Connection conn, String query) {
        List<Alumno> lista = new ArrayList<>();
        try {
            PreparedStatement s = conn.prepareStatement(
                    "select * from alumnos a inner join imparten i on a.num_exp = i.alumno inner join profesores p on i.profesor = p.id where p.dni = ? group by a.num_exp");
            s.setString(1, query);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Alumno al = new Alumno();
                al.setNumExp(rs.getInt("num_exp"));
                al.setDni(rs.getString("dni"));
                al.setNombre(rs.getString("nombre"));
                al.setApellidos(rs.getString("apellidos"));
                al.setFecha(rs.getDate("fecha_nac"));
                lista.add(al);
            }
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return lista;
    }

    public boolean newAlumno(Connection conn, String dni, String nombre, String apellidos, String fecha) {
        try {
            PreparedStatement s = conn
                    .prepareStatement("insert into alumnos (dni, nombre, apellidos, fecha_nac) values (?, ?, ?, ?)");
            s.setString(1, dni);
            s.setString(2, nombre);
            s.setString(3, apellidos);
            s.setDate(4, Date.valueOf(fecha));
            int filas = s.executeUpdate();
            System.out.println(filas);
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
            return false;
        }
        return true;
    }

    @Override
    public JSONObject toJSON(Connection conn) {
        JSONArray jsonArr = new JSONArray();
        List<Alumno> list = this.getAll(conn);
        list.forEach(item -> {
            JSONObject obj = new JSONObject();
            obj.put("num_exp", item.getNumExp());
            obj.put("dni", item.getDni());
            obj.put("nombre", item.getNombre());
            obj.put("apellidos", item.getApellidos());
            obj.put("fecha_nac", item.getFecha().toString());
            jsonArr.put(obj);
        });
        return new JSONObject().put("alumnos", jsonArr);
    }

    @Override
    public void batchInsert(Connection conn, List<Alumno> list) {
        try {
            PreparedStatement s = conn.prepareStatement("insert into alumnos values (?, ?, ?, ?, ?)");
            for (Alumno item : list) {
                s.setInt(1, item.getNumExp());
                s.setString(2, item.getDni());
                s.setString(3, item.getNombre());
                s.setString(4, item.getApellidos());
                s.setDate(5, item.getFecha());
                s.addBatch();
            }
            s.executeBatch();
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
    }

    @Override
    public List<Alumno> getJSON(JSONArray arr) {
        List<Alumno> list = new ArrayList<>();
        arr.forEach(o -> {
            Alumno item = new Alumno();
            item.setNumExp(((JSONObject) o).getInt("num_exp"));
            item.setDni(((JSONObject) o).getString("dni"));
            item.setNombre(((JSONObject) o).getString("nombre"));
            item.setApellidos(((JSONObject) o).getString("apellidos"));
            item.setFecha(Date.valueOf(((JSONObject) o).getString("fecha_nac")));
            list.add(item);
        });
        return list;
    }

}
