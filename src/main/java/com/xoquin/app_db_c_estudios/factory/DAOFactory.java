package com.xoquin.app_db_c_estudios.factory;

import java.sql.Connection;
import java.sql.SQLException;

import com.xoquin.app_db_c_estudios.dao.AlumnoDAO;
import com.xoquin.app_db_c_estudios.dao.AsignaturaDAO;
import com.xoquin.app_db_c_estudios.dao.DepartamentoDAO;
import com.xoquin.app_db_c_estudios.dao.ProfesorDAO;


public abstract class DAOFactory {
    // List of DAO types supported by the factory
    public static final int MYSQL = 1;
    public static final int MARIADB = 2;
    public static final int POSTGRESQL = 3;

    public abstract Connection getConnection() throws SQLException;

    // There will be a method for each DAO that can be
    // created. The concrete factories will have to
    // implement these methods.
    public abstract AlumnoDAO getAlumnoDAO();
    public abstract AsignaturaDAO getAsignaturaDAO();
    public abstract DepartamentoDAO getDepartamentoDAO();
    public abstract ProfesorDAO getProfesorDAO();
    
    public abstract boolean createDB();
    public abstract boolean clearDB();
    public abstract boolean volcarDB(Connection conn, String location);
    public abstract boolean cargarVolcadoDB(Connection conn, String location);


    public static DAOFactory getDAOFactory(int whichFactory) {

        switch (whichFactory) {
        case MARIADB:
            return new MariaDBDAOFactory();
        case POSTGRESQL:
            return new PostgreSQLDAOFactory();
        default:
            return null;
        }
    }

    public boolean releaseConnection(Connection connection) {
        // TODO Auto-generated method stub
        return false;
    }

    public int getSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void shutdown() throws Exception {
        // TODO Auto-generated method stub
    }
}
