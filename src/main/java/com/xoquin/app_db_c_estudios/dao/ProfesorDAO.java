package com.xoquin.app_db_c_estudios.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.xoquin.app_db_c_estudios.util.ExceptionHandler;
import com.xoquin.app_db_c_estudios.vo.Profesor;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProfesorDAO implements Dao<Profesor> {
    public static String ROW_NOMBRE = "nombre";
    public static String ROW_APELLIDOS = "apellidos";
    public static String ROW_DEPARTAMENTO = "departamento";

    @Override
    public Profesor get(Connection conn, int id) {
        Profesor pro = new Profesor();
        try {
            PreparedStatement s = conn.prepareStatement("select * from profesoresObject where id = ?");
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            rs.next();
            pro.setId(rs.getInt("id"));
            pro.setDni(rs.getString("dni"));
            pro.setNombre(rs.getString("nombre"));
            pro.setApellidos(rs.getString("apellidos"));
            pro.setDepartamento(rs.getString("departamento"));
            pro.setDepartamentoId(rs.getInt("id_departamento"));
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return pro;
    }

    @Override
    public List<Profesor> getAll(Connection conn) {
        List<Profesor> lista = new ArrayList<>();
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("select * from profesores");
            while (rs.next()) {
                Profesor pro = new Profesor();
                pro.setId(rs.getInt("id"));
                pro.setDni(rs.getString("dni"));
                pro.setNombre(rs.getString("nombre"));
                pro.setApellidos(rs.getString("apellidos"));
                pro.setDepartamento(rs.getString("departamento"));
                pro.setDepartamentoId(rs.getInt("id_departamento"));
                lista.add(pro);
            }
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return lista;
    }

    public List<Profesor> getByDNI(Connection conn, String query){
        List<Profesor> lista = new ArrayList<>();
        try {
            PreparedStatement s = conn.prepareStatement("select * from profesoresObject where dni = ?");
            s.setString(1, query);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Profesor pro = new Profesor();
                pro.setId(rs.getInt("id"));
                pro.setDni(rs.getString("dni"));
                pro.setNombre(rs.getString("nombre"));
                pro.setApellidos(rs.getString("apellidos"));
                pro.setDepartamento(rs.getString("departamento"));
                pro.setDepartamentoId(rs.getInt("id_departamento"));
                lista.add(pro);
            }
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return lista;
    }

    public List<Profesor> getByRowLike(Connection conn, String row, String query){
        List<Profesor> lista = new ArrayList<>();
        try {
            PreparedStatement s = conn.prepareStatement("select * from profesoresObject where upper("+row+") like upper(?)");
            s.setString(1, "%"+query+"%");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Profesor pro = new Profesor();
                pro.setId(rs.getInt("id"));
                pro.setDni(rs.getString("dni"));
                pro.setNombre(rs.getString("nombre"));
                pro.setApellidos(rs.getString("apellidos"));
                pro.setDepartamento(rs.getString("departamento"));
                pro.setDepartamentoId(rs.getInt("id_departamento"));
                lista.add(pro);
            }
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return lista;
    }

    @Override
    public JSONObject toJSON(Connection conn) {
        JSONArray jsonArr = new JSONArray();
        List<Profesor> list = this.getAll(conn);
        list.forEach(item -> {
            JSONObject obj = new JSONObject();
            obj.put("id", item.getId());
            obj.put("dni", item.getDni());
            obj.put("nombre", item.getNombre());
            obj.put("apellidos", item.getApellidos());
            obj.put("departamento", item.getDepartamentoId());
            jsonArr.put(obj);
        });
        return new JSONObject().put("profesores", jsonArr);
    }

    @Override
    public void batchInsert(Connection conn, List<Profesor> list) {
        try {
            PreparedStatement s = conn.prepareStatement("insert into profesores values (?, ?, ?, ?, ?)");
            for (Profesor item : list) {
                s.setInt(1, item.getId());
                s.setString(2, item.getDni());
                s.setString(3, item.getNombre());
                s.setString(4, item.getApellidos());
                s.setInt(5, item.getDepartamentoId());
                s.addBatch();
            }
            s.executeBatch();
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
    }

    @Override
    public List<Profesor> getJSON(JSONArray arr) {
        List<Profesor> list = new ArrayList<>();
        arr.forEach(o -> {
            Profesor item = new Profesor();
            item.setId(((JSONObject) o).getInt("id"));
            item.setDni(((JSONObject) o).getString("dni"));
            item.setNombre(((JSONObject) o).getString("nombre"));
            item.setApellidos(((JSONObject) o).getString("apellidos"));
            item.setDepartamentoId(((JSONObject) o).getInt("departamento"));
            list.add(item);
        });
        return list;
    }

    public void newProfesor(Connection conn, String dni, String nombre, String apellidos, int departamento){
        try {
            PreparedStatement s = conn
                    .prepareStatement("insert into profesores (dni, nombre, apellidos, departamento) values (?, ?, ?, ?)");
            s.setString(1, dni);
            s.setString(2, nombre);
            s.setString(3, apellidos);
            s.setInt(4, departamento);
            s.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
    }

}
