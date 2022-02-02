package com.xoquin.app_db_c_estudios.factory;

import java.sql.Connection;
import java.sql.SQLException;

import com.xoquin.app_db_c_estudios.dao.AlumnoDAO;
import com.xoquin.app_db_c_estudios.dao.AsignaturaDAO;
import com.xoquin.app_db_c_estudios.dao.DepartamentoDAO;
import com.xoquin.app_db_c_estudios.dao.ProfesorDAO;
import com.xoquin.app_db_c_estudios.pool.BasicConnectionPool;
import com.xoquin.app_db_c_estudios.util.ExceptionHandler;

public class PostgreSQLDAOFactory extends DAOFactory{
    final static String url = "jdbc:postgresql://localhost:5432/centroestudios";
    final static String user = "postgres";
    final static String password = "1234";
    static BasicConnectionPool bcp;

    public PostgreSQLDAOFactory(){
        try {
            bcp = BasicConnectionPool.create(url, user, password);
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
    }
    
    @Override
    public Connection getConnection() throws SQLException {
        return bcp.getConnection();
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        return bcp.releaseConnection(connection);
    }

    @Override
    public int getSize() {
        return bcp.getSize();
    }

    // add getUser, getURL....
    @Override
    public void shutdown() throws SQLException {
        bcp.shutdown();
    }

    @Override
    public AlumnoDAO getAlumnoDAO() {
        return new AlumnoDAO();
    }

    @Override
    public AsignaturaDAO getAsignaturaDAO() {
        return new AsignaturaDAO();
    }

    @Override
    public DepartamentoDAO getDepartamentoDAO() {
        return new DepartamentoDAO();
    }

    @Override
    public ProfesorDAO getProfesorDAO() {
        return new ProfesorDAO();
    }

    @Override
    public boolean createDB() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean clearDB() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean volcarDB(Connection conn, String location) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean cargarVolcadoDB(Connection conn, String location) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
