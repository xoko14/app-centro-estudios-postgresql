package com.xoquin.app_db_c_estudios.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.xoquin.app_db_c_estudios.factory.DialogFactory;
import com.xoquin.app_db_c_estudios.util.ExceptionHandler;
import com.xoquin.app_db_c_estudios.vo.Departamento;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class CrearProfesoresController extends DBViewController implements Initializable {
    @FXML
    TextField txtDNI;
    @FXML
    TextField txtNome;
    @FXML
    TextField txtApelidos;
    @FXML
    ComboBox<Departamento> cbxDepartamento;

    private List<Departamento> departamentos;
    Departamento currentDept = null;

    /**
     * Inicializa a vista cargando os departamentos da base de datos no selector de departamentos.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            departamentos = db.getDepartamentoDAO().getAll(db.getConnection());
            cbxDepartamento.getItems().setAll(departamentos);
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }

        cbxDepartamento.setConverter(new StringConverter<Departamento>() {
            @Override
            public Departamento fromString(String string) {
                return cbxDepartamento.getItems().stream()
                        .filter(departamento -> departamento.getNombre().equals(string)).findFirst().orElse(null);
            }

            @Override
            public String toString(Departamento dept) {
                return dept.getNombre();
            }
        });

        cbxDepartamento.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Departamento>() {
            @Override
            public void changed(ObservableValue<? extends Departamento> selected, Departamento oldI,
                    Departamento newI) {
                currentDept = newI;
            }
        });

    }

    /**
     * Colle os valores da vista e, se non están baleiros, inserta un novo profesor.
     * @param ae ActionEvent
     */
    @FXML
    private void darAlta(ActionEvent ae) {
        if (currentDept != null && !txtDNI.getText().isBlank() && !txtNome.getText().isBlank() && !txtApelidos.getText().isBlank()) {
            String dni = txtDNI.getText();
            String nombre = txtNome.getText();
            String apellidos = txtApelidos.getText();

            try {
                db.getProfesorDAO().newProfesor(db.getConnection(), dni, nombre, apellidos, currentDept.getId());

                DialogFactory df = new DialogFactory(DialogFactory.INFO_DIALOG);
                df.setText(String.format("Creouse \"%s %s\" correctamente", nombre, apellidos));
                df.launch();

            } catch (SQLException e) {
                ExceptionHandler.handle(e);
            }
        } else {
            DialogFactory df = new DialogFactory(DialogFactory.INFO_DIALOG);
            df.setText("Hai datos en branco");
            df.launch();
        }
    }

}
