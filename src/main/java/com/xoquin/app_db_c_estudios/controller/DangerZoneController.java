package com.xoquin.app_db_c_estudios.controller;

import java.io.File;
import java.sql.SQLException;

import com.xoquin.app_db_c_estudios.factory.DialogFactory;
import com.xoquin.app_db_c_estudios.util.ExceptionHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class DangerZoneController extends DBViewController {
    @FXML
    Button btnInicializar;
    @FXML
    Button btnBorrar;
    @FXML
    Button btnSeleccionar;
    @FXML
    Button btnVolcar;
    @FXML
    Button btnCargarVolcado;
    @FXML
    TextField txtVolcadoFolder;

    /**
     * Abre o diálogo de selección de carpeta e establece a carpeta do volcado.
     * @param ae
     */
    @FXML
    public void seleccionarCarpeta(ActionEvent ae) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Selecciona carpeta para volcado");
        File defaultDirectory = new File("./");
        chooser.setInitialDirectory(defaultDirectory);
        Stage stage = new Stage();
        File selectedDirectory = chooser.showDialog(stage);
        if (selectedDirectory != null)
            txtVolcadoFolder.setText(selectedDirectory.toPath().toString());
    }

    /**
     * Borra as tablas da base de datos.
     * @param ae
     */
    @FXML
    public void borrar(ActionEvent ae) {
        DialogFactory df = new DialogFactory(DialogFactory.YES_NO_DIALOG);
        df.setText("De verdad quieres borrar la base de datos?");
        df.launch();
        if (df.getResult() == DialogFactory.RESULT_YES) {
            db.clearDB();
        }
    }

    /**
     * Inicializa as tablas da base de datos.
     * @param ae
     */
    @FXML
    public void init(ActionEvent ae) {
        DialogFactory df = new DialogFactory(DialogFactory.YES_NO_DIALOG);
        df.setText("De verdad quieres inicializar la base de datos?");
        df.launch();
        if (df.getResult() == DialogFactory.RESULT_YES) {
            System.out.println("inicializando");
        }
    }

    /**
     * Volcar as tablas na ruta seleccionada.
     */
    @FXML
    public void volcar() {
        if(comprobarRuta()){
            DialogFactory df = new DialogFactory(DialogFactory.YES_NO_DIALOG);
            df.setText("Se va a volcar de la base de datos.");
            df.launch();
            if(df.getResult() == DialogFactory.RESULT_YES){
                try {
                    db.volcarDB(db.getConnection(), txtVolcadoFolder.getText());
                } catch (SQLException e) {
                    ExceptionHandler.handle(e);
                }
            }
        } else {
            DialogFactory df = new DialogFactory(DialogFactory.INFO_DIALOG);
            df.setText("Por favor, especifica una ruta");
            df.launch();
        }
    }

    /**
     * Cargar os datos do volcado da ruta especificada.
     */
    @FXML
    public void cargarVolcado(){
        if(comprobarRuta()){
            DialogFactory df = new DialogFactory(DialogFactory.YES_NO_DIALOG);
            df.setText("Se va a cargar el volcado de la base de datos.");
            df.launch();
            if(df.getResult() == DialogFactory.RESULT_YES){
                try {
                    db.cargarVolcadoDB(db.getConnection(), txtVolcadoFolder.getText());
                } catch (SQLException e) {
                    ExceptionHandler.handle(e);
                }
            }
        } else {
            DialogFactory df = new DialogFactory(DialogFactory.INFO_DIALOG);
            df.setText("Por favor, especifica una ruta");
            df.launch();
        }
    }

    private boolean comprobarRuta() {
        return !txtVolcadoFolder.getText().isEmpty();
    }
}
