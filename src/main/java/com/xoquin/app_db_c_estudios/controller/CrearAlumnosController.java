package com.xoquin.app_db_c_estudios.controller;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import com.xoquin.app_db_c_estudios.factory.DialogFactory;
import com.xoquin.app_db_c_estudios.util.ExceptionHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CrearAlumnosController extends DBViewController{
    @FXML TextField txtDNI;
    @FXML TextField txtNome;
    @FXML TextField txtApelidos;
    @FXML DatePicker data;
    @FXML Button btnAlta;

    private DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Colle os valores da vista e, se non están baleiros, inserta un novo alumno.
     * @param ae ActionEvent
     */
    @FXML
    private void darAlta(ActionEvent ae){
        String dni = txtDNI.getText();
        String nombre = txtNome.getText();
        String apellidos = txtApelidos.getText();
        String fecha;

        if(data.getValue() != null && !txtDNI.getText().isBlank() && !txtNome.getText().isBlank() && !txtApelidos.getText().isBlank()){
            fecha = data.getValue().format(format);
            try {
                db.getAlumnoDAO().newAlumno(db.getConnection(), dni, nombre, apellidos, fecha);
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
