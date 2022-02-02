package com.xoquin.app_db_c_estudios.factory;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

import com.xoquin.app_db_c_estudios.dao.AlumnoDAO;
import com.xoquin.app_db_c_estudios.dao.AsignaturaDAO;
import com.xoquin.app_db_c_estudios.dao.DepartamentoDAO;
import com.xoquin.app_db_c_estudios.dao.ImpartenDAO;
import com.xoquin.app_db_c_estudios.dao.ProfesorDAO;
import com.xoquin.app_db_c_estudios.pool.BasicConnectionPool;
import com.xoquin.app_db_c_estudios.util.ExceptionHandler;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.json.JSONArray;
import org.json.JSONObject;

public class MariaDBDAOFactory extends DAOFactory{
    final static String url = "jdbc:mariadb://localhost/centro_estudios";
    final static String user = "admin";
    final static String password = "1234";
    static BasicConnectionPool bcp;

    private ImpartenDAO impartenDAO = new ImpartenDAO();

    public MariaDBDAOFactory() {
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

    /**
     * Crea as táboas da base de datos.
     */
    @Override
    public boolean createDB() {
        try {
            ScriptRunner sr = new ScriptRunner(getConnection());
            Reader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/sql/init_db.sql")));
            sr.runScript(reader);
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
            return false;
        }
        return true;
    }

    /**
     * Borra as táboas da base de datos.
     */
    @Override
    public boolean clearDB() {
        try {
            ScriptRunner sr = new ScriptRunner(getConnection());
            Reader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/sql/clear_db.sql")));
            sr.runScript(reader);
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
            return false;
        }
        return true;
    }

    /**
     * Volca os contidos das táboas a arquivos JSON.
     */
    @Override
    public boolean volcarDB(Connection conn, String location) {
        Writer writer;
        try {
            writer = new FileWriter(Paths.get(location, "alumnos.json").toFile());
            this.getAlumnoDAO().toJSON(conn).write(writer);
            writer.flush();
            writer.close();
            
            writer = new FileWriter(Paths.get(location.toString(), "departamentos.json").toFile());
            this.getDepartamentoDAO().toJSON(conn).write(writer);
            writer.flush();
            writer.close();

            writer = new FileWriter(Paths.get(location.toString(), "profesores.json").toFile());
            this.getProfesorDAO().toJSON(conn).write(writer);
            writer.flush();
            writer.close();

            writer = new FileWriter(Paths.get(location.toString(), "asignaturas.json").toFile());
            this.getAsignaturaDAO().toJSON(conn).write(writer);
            writer.flush();
            writer.close();

            writer = new FileWriter(Paths.get(location.toString(), "imparten.json").toFile());
            impartenDAO.toJSON(conn).write(writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            ExceptionHandler.handle(e);
        }
        return true;
    }

    /**
     * Carga o volcado desde arquivos JSON.
     */
    @Override
    public boolean cargarVolcadoDB(Connection conn, String location) {
        String content;
        try {
            content = Files.readString(Paths.get(location, "alumnos.json"), StandardCharsets.UTF_8);
            JSONArray als = new JSONObject(content).getJSONArray("alumnos");
            getAlumnoDAO().batchInsert(getConnection(), getAlumnoDAO().getJSON(als));

            content = Files.readString(Paths.get(location, "asignaturas.json"), StandardCharsets.UTF_8);
            JSONArray asig = new JSONObject(content).getJSONArray("asignaturas");
            getAsignaturaDAO().batchInsert(getConnection(), getAsignaturaDAO().getJSON(asig));

            content = Files.readString(Paths.get(location, "departamentos.json"), StandardCharsets.UTF_8);
            JSONArray depts = new JSONObject(content).getJSONArray("departamentos");
            getDepartamentoDAO().batchInsert(getConnection(), getDepartamentoDAO().getJSON(depts));

            content = Files.readString(Paths.get(location, "profesores.json"), StandardCharsets.UTF_8);
            JSONArray profs = new JSONObject(content).getJSONArray("profesores");
            getProfesorDAO().batchInsert(getConnection(), getProfesorDAO().getJSON(profs));

            content = Files.readString(Paths.get(location, "imparten.json"), StandardCharsets.UTF_8);
            JSONArray imp = new JSONObject(content).getJSONArray("imparten");
            impartenDAO.batchInsert(getConnection(), impartenDAO.getJSON(imp));
        } catch (IOException e) {
            ExceptionHandler.handle(e);
            return false;
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        return true;
    }
}
