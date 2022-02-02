package com.xoquin.app_db_c_estudios.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.xoquin.app_db_c_estudios.util.ExceptionHandler;
import com.xoquin.app_db_c_estudios.vo.Departamento;

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

public class BuscarDepartamentosController extends DBViewController implements Initializable{
    @FXML private Button btnInitDB;
    @FXML private TableView<Departamento> tabDepartamentos;
    @FXML private TableColumn<Departamento, String> colNombre;
    @FXML private TableColumn<Departamento, String> colNumProf;
    @FXML private ComboBox<String> cbxBuscarPor; 
    @FXML private TextField txtBusqueda;

    private String selectedItem = null;

    /**
     * Inicializa componentes.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colNombre.setCellValueFactory(new PropertyValueFactory<Departamento, String>("nombre"));
        colNumProf.setCellValueFactory(new PropertyValueFactory<Departamento, String>("numProf"));

        cbxBuscarPor.getItems().setAll("Todos", "Nombre", "DNI de profesor");
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
                case "Nombre": findByName(); break;
                case "DNI de profesor": findByProfesor(); break;
            }
          }
    }

    private void findAll(){
        List<Departamento> depts = new ArrayList<>();
        try {
            depts = db.getDepartamentoDAO().getAll(db.getConnection());
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        tabDepartamentos.getItems().setAll(depts);
    }

    private void findByName(){
        List<Departamento> depts = new ArrayList<>();
        try {
            depts = db.getDepartamentoDAO().getByName(db.getConnection(), txtBusqueda.getText());
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        tabDepartamentos.getItems().setAll(depts);
    }

    private void findByProfesor(){
      List<Departamento> depts = new ArrayList<>();
        try {
            depts = db.getDepartamentoDAO().getByProfesor(db.getConnection(), txtBusqueda.getText());
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
        tabDepartamentos.getItems().setAll(depts);
    }

}
