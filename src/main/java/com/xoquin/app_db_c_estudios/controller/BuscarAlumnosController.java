package com.xoquin.app_db_c_estudios.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.xoquin.app_db_c_estudios.dao.AlumnoDAO;
import com.xoquin.app_db_c_estudios.util.ExceptionHandler;
import com.xoquin.app_db_c_estudios.vo.Alumno;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BuscarAlumnosController extends DBViewController implements Initializable{
    @FXML private Button btnInitDB;
    @FXML private TableView<Alumno> tabAlumnos;
    @FXML private TableColumn<Alumno, Integer> colNum;
    @FXML private TableColumn<Alumno, String> colDNI;
    @FXML private TableColumn<Alumno, String> colNombre;
    @FXML private TableColumn<Alumno, String> colApellidos;
    @FXML private TableColumn<Alumno, Date> colNac;
    @FXML private ComboBox<String> cbxBuscarPor; 
    @FXML private TextField txtBusqueda;

    private String selectedItem = null;

    /**
     * Inicializa componentes.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colNum.setCellValueFactory(new PropertyValueFactory<Alumno, Integer>("numExp"));
        colDNI.setCellValueFactory(new PropertyValueFactory<Alumno, String>("dni"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Alumno, String>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<Alumno, String>("apellidos"));
        colNac.setCellValueFactory(new PropertyValueFactory<Alumno, Date>("fecha"));

        cbxBuscarPor.getItems().setAll("Todos", "Número de expediente", "DNI", "Nombre", "Apellidos", "Año de nacimiento", "DNI de profesor");
        cbxBuscarPor.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> selected, String oldI, String newI) {
              selectedItem = newI;
              if(selectedItem.equals("Todos")) txtBusqueda.disableProperty().set(true);
              else txtBusqueda.disableProperty().set(false);
            }
          });
    }

    /**
     * Realiza a búsqueda segundo o escollido na vista.
     * @param ae ActionEvent
     */
    @FXML
    private void buscar(ActionEvent ae){
        if (selectedItem != null) {
            switch(selectedItem) {
                case "Todos": findAll(); break; 
                case "Número de expediente": findByID(); break;
                case "DNI": findByDNI(); break;
                case "Nombre": findByRowLike(AlumnoDAO.ROW_NOMBRE); break;
                case "Apellidos": findByRowLike(AlumnoDAO.ROW_APELLIDOS); break;
                case "Año de nacimiento": findByAnho(); break;
                case "DNI de profesor": findByProfesor(); break;
            }
          }
    }

    private void findAll(){
        List<Alumno> als = new ArrayList<>();
        try {
            als = db.getAlumnoDAO().getAll(db.getConnection());
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        tabAlumnos.getItems().setAll(als);
    }

    private void findByID(){
        int id = Integer.parseInt(txtBusqueda.getText());
        List<Alumno> als = new ArrayList<>();
        try {
            als.add(db.getAlumnoDAO().get(db.getConnection(), id));
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        tabAlumnos.getItems().setAll(als);
    }

    private void findByDNI(){
        List<Alumno> als = new ArrayList<>();
        try {
            als = db.getAlumnoDAO().getByDNI(db.getConnection(), txtBusqueda.getText());
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        tabAlumnos.getItems().setAll(als);
    }

    private void findByRowLike(String row){
        List<Alumno> als = new ArrayList<>();
        try {
            als = db.getAlumnoDAO().getByRowLike(db.getConnection(), row, txtBusqueda.getText());
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        tabAlumnos.getItems().setAll(als);
    }

    private void findByAnho(){
        List<Alumno> als = new ArrayList<>();
        try {
            als = db.getAlumnoDAO().getByYear(db.getConnection(), txtBusqueda.getText());
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        tabAlumnos.getItems().setAll(als);
    }

    private void findByProfesor(){
        List<Alumno> als = new ArrayList<>();
        try {
            als = db.getAlumnoDAO().getByProfesor(db.getConnection(), txtBusqueda.getText());
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        tabAlumnos.getItems().setAll(als);
    }
}
