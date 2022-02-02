package com.xoquin.app_db_c_estudios.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.xoquin.app_db_c_estudios.util.ExceptionHandler;
import com.xoquin.app_db_c_estudios.vo.Departamento;

import org.json.JSONArray;
import org.json.JSONObject;

public class DepartamentoDAO implements Dao<Departamento> {

    @Override
    public Departamento get(Connection conn, int id) {
        Departamento dept = new Departamento();
        try {
            PreparedStatement s = conn.prepareStatement("select * from DepartamentosObject where id = ?");
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            rs.next();
            dept.setId(rs.getInt("id"));
            dept.setNombre(rs.getString("nombre"));
            dept.setNumProf(rs.getInt("num_prof"));

        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return dept;
    }

    @Override
    public List<Departamento> getAll(Connection conn) {
        List<Departamento> lista = new ArrayList<>();
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("select * from departamentosObject");
            while (rs.next()) {
                Departamento dept = new Departamento();
                dept.setId(rs.getInt("id"));
                dept.setNombre(rs.getString("nombre"));
                dept.setNumProf(rs.getInt("num_prof"));
                lista.add(dept);
            }
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return lista;
    }

    public List<Departamento> getByName(Connection conn, String query){
        List<Departamento> lista = new ArrayList<>();
        try {
            PreparedStatement s = conn.prepareStatement("select * from departamentosObject where upper(nombre) like upper(?)");
            s.setString(1, "%"+query+"%");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Departamento dept = new Departamento();
                dept.setId(rs.getInt("id"));
                dept.setNombre(rs.getString("nombre"));
                dept.setNumProf(rs.getInt("num_prof"));
                lista.add(dept);
            }
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return lista;
    }

    public List<Departamento> getByProfesor(Connection conn, String query){
        List<Departamento> lista = new ArrayList<>();
        try {
            PreparedStatement s = conn.prepareStatement("select * from departamentosObject d inner join profesores p on d.id = p.departamento where p.dni =  ? group by d.id");
            s.setString(1, query);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Departamento dept = new Departamento();
                dept.setId(rs.getInt("id"));
                dept.setNombre(rs.getString("nombre"));
                dept.setNumProf(rs.getInt("num_prof"));
                lista.add(dept);
            }
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return lista;
    }

    @Override
    public JSONObject toJSON(Connection conn) {
        JSONArray jsonArr = new JSONArray();
        List<Departamento> list = this.getAll(conn);
        list.forEach(item -> {
            JSONObject obj = new JSONObject();
            obj.put("id", item.getId());
            obj.put("nombre", item.getNombre());
            jsonArr.put(obj);
        });
        return new JSONObject().put("departamentos", jsonArr);
    }

    @Override
    public void batchInsert(Connection conn, List<Departamento> list) {
        try {
            PreparedStatement s = conn.prepareStatement("insert into departamentos values (?, ?)");
            for (Departamento item : list) {
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
    public List<Departamento> getJSON(JSONArray arr) {
        List<Departamento> list = new ArrayList<>();
        arr.forEach(o -> {
            Departamento item = new Departamento();
            item.setId(((JSONObject) o).getInt("id"));
            item.setNombre(((JSONObject) o).getString("nombre"));
            list.add(item);
        });
        return list;
    }

    public void newDepartamento(Connection conn, String nombre){
        try {
            PreparedStatement s = conn
                    .prepareStatement("insert into departamentos (nombre) values (?)");
            s.setString(1, nombre);
            s.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
    }

}
