package com.xoquin.app_db_c_estudios.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.xoquin.app_db_c_estudios.util.ExceptionHandler;
import com.xoquin.app_db_c_estudios.vo.Asignatura;

import org.json.JSONArray;
import org.json.JSONObject;

public class AsignaturaDAO implements Dao<Asignatura> {

    @Override
    public Asignatura get(Connection conn, int id) {
        Asignatura asig = new Asignatura();
        try {
            PreparedStatement s = conn.prepareStatement("select * from asignaturas where id = ?");
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            rs.next();
            asig.setId(rs.getInt("id"));
            asig.setNombre(rs.getString("nombre"));

        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return asig;
    }

    @Override
    public List<Asignatura> getAll(Connection conn) {
        List<Asignatura> lista = new ArrayList<>();
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("select * from asignaturas");
            while (rs.next()) {
                Asignatura asig = new Asignatura();
                asig.setId(rs.getInt("id"));
                asig.setNombre(rs.getString("nombre"));
                lista.add(asig);
            }
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return lista;
    }

    public List<Asignatura> getByName(Connection conn, String query){
        List<Asignatura> lista = new ArrayList<>();
        try {
            PreparedStatement s = conn.prepareStatement("select * from asignaturas where upper(nombre) like upper(?)");
            s.setString(1, "%"+query+"%");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Asignatura asig = new Asignatura();
                asig.setId(rs.getInt("id"));
                asig.setNombre(rs.getString("nombre"));
                lista.add(asig);
            }
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return lista;
    }

    public List<Asignatura> getByProfesor(Connection conn, String query){
        List<Asignatura> lista = new ArrayList<>();
        try {
            PreparedStatement s = conn.prepareStatement("select * from asignaturas asi inner join imparten i on asi.id = i.asignatura inner join profesores p on i.profesor = p.id where p.dni = ? group by asi.id");
            s.setString(1, query);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Asignatura asig = new Asignatura();
                asig.setId(rs.getInt("id"));
                asig.setNombre(rs.getString("nombre"));
                lista.add(asig);
            }
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return lista;
    }

    public List<Asignatura> getByAlumno(Connection conn, String query){
        List<Asignatura> lista = new ArrayList<>();
        try {
            PreparedStatement s = conn.prepareStatement("select * from asignaturas asi inner join imparten i on asi.id = i.asignatura inner join alumnos a on i.alumno = a.num_exp where a.num_exp = ? group by asi.id");
            s.setString(1, query);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Asignatura asig = new Asignatura();
                asig.setId(rs.getInt("id"));
                asig.setNombre(rs.getString("nombre"));
                lista.add(asig);
            }
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return lista;
    }

    @Override
    public JSONObject toJSON(Connection conn) {
        JSONArray jsonArr = new JSONArray();
        List<Asignatura> list = this.getAll(conn);
        list.forEach(item -> {
            JSONObject obj = new JSONObject();
            obj.put("id", item.getId());
            obj.put("nombre", item.getNombre());
            jsonArr.put(obj);
        });
        return new JSONObject().put("asignaturas", jsonArr);
    }

    @Override
    public void batchInsert(Connection conn, List<Asignatura> list) {
        try {
            PreparedStatement s = conn.prepareStatement("insert into asignaturas values (?, ?)");
            for (Asignatura item : list) {
                s.setInt(1, item.getId());
                s.setString(2, item.getNombre());
                s.addBatch();
            }
            s.executeBatch();
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
    }

    @Override
    public List<Asignatura> getJSON(JSONArray arr) {
        List<Asignatura> list = new ArrayList<>();
        arr.forEach(o -> {
            Asignatura item = new Asignatura();
            item.setId(((JSONObject) o).getInt("id"));
            item.setNombre(((JSONObject) o).getString("nombre"));
            list.add(item);
        });
        return list;
    }

    public void newAsignatura(Connection conn, String nombre){
        try {
            PreparedStatement s = conn
                    .prepareStatement("insert into asignaturas (nombre) values (?)");
            s.setString(1, nombre);
            s.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
    }
}
