package com.xoquin.app_db_c_estudios.controller;

import java.sql.SQLException;

import com.xoquin.app_db_c_estudios.factory.DialogFactory;
import com.xoquin.app_db_c_estudios.util.ExceptionHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CrearDepartamentosController extends DBViewController{

    @FXML TextField txtNome;

    /**
     * Colle os valores da vista e, se non están baleiros, inserta un novo departamento.
     * @param ae ActionEvent
     */
    @FXML
    private void darAlta(ActionEvent ae){
        if(!txtNome.getText().isBlank()){
            try {
                db.getDepartamentoDAO().newDepartamento(db.getConnection(), txtNome.getText());
                DialogFactory df = new DialogFactory(DialogFactory.INFO_DIALOG);
                df.setText(String.format("Creouse o departamento \"%s\" correctamente", txtNome.getText()));
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
