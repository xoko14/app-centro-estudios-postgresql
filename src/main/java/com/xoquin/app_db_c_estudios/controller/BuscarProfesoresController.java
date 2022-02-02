package com.xoquin.app_db_c_estudios.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.xoquin.app_db_c_estudios.dao.ProfesorDAO;
import com.xoquin.app_db_c_estudios.util.ExceptionHandler;
import com.xoquin.app_db_c_estudios.vo.Profesor;

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

public class BuscarProfesoresController extends DBViewController implements Initializable{
    @FXML private Button btnInitDB;
    @FXML private TableView<Profesor> tabProfesores;
    @FXML private TableColumn<Profesor, String> colDNI;
    @FXML private TableColumn<Profesor, String> colNombre;
    @FXML private TableColumn<Profesor, String> colApellidos;
    @FXML private TableColumn<Profesor, Date> colDept;
    @FXML private ComboBox<String> cbxBuscarPor; 
    @FXML private TextField txtBusqueda;

    private String selectedItem = null;

    /**
     * Inicializa componentes.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colDNI.setCellValueFactory(new PropertyValueFactory<Profesor, String>("dni"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Profesor, String>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<Profesor, String>("apellidos"));
        colDept.setCellValueFactory(new PropertyValueFactory<Profesor, Date>("departamento"));

        cbxBuscarPor.getItems().setAll("Todos", "DNI", "Nombre", "Apellidos", "Departamento");
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
              case "DNI": findByDNI(); break;
              case "Nombre": findByRowLike(ProfesorDAO.ROW_NOMBRE); break;
              case "Apellidos": findByRowLike(ProfesorDAO.ROW_APELLIDOS); break;
              case "Departamento": findByRowLike(ProfesorDAO.ROW_DEPARTAMENTO); break;
            }
          }
    }

    private void findAll(){
      List<Profesor> profs = new ArrayList<>();
        try {
            profs = db.getProfesorDAO().getAll(db.getConnection());
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        tabProfesores.getItems().setAll(profs);
    }

    private void findByDNI(){
        List<Profesor> profs = new ArrayList<>();
        try {
            profs = db.getProfesorDAO().getByDNI(db.getConnection(), txtBusqueda.getText());
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        tabProfesores.getItems().setAll(profs);
    }

    private void findByRowLike(String row){
      List<Profesor> profs = new ArrayList<>();
      try {
          profs = db.getProfesorDAO().getByRowLike(db.getConnection(), row, txtBusqueda.getText());
      } catch (SQLException e) {
          ExceptionHandler.handle(e);
      }
      tabProfesores.getItems().setAll(profs);
  }
}
