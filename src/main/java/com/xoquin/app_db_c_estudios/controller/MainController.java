package com.xoquin.app_db_c_estudios.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.xoquin.app_db_c_estudios.util.ExceptionHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Este controlador abre as distintas vistas segundo o botón accionado.
 */
public class MainController implements Initializable {
    @FXML
    private Button btnBuscarAlumnos;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    @FXML
    private void abrirBuscarAlumnos(ActionEvent ae) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/buscar_alumnos.fxml"));
        BuscarAlumnosController c = new BuscarAlumnosController();
        loader.setController(c);
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.getIcons().add(new Image("/img/icon.png"));
            stage.setTitle("Buscar Alumnos");
            stage.show();
        } catch (IOException e) {
            ExceptionHandler.handle(e);
        }
    }

    @FXML
    private void abrirBuscarProfesores(ActionEvent ae) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/buscar_profesores.fxml"));
        BuscarProfesoresController c = new BuscarProfesoresController();
        loader.setController(c);
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.getIcons().add(new Image("/img/icon.png"));
            stage.setTitle("Buscar Profesores");
            stage.show();
        } catch (IOException e) {
            ExceptionHandler.handle(e);
        }
    }

    @FXML
    private void abrirBuscarDepartamentos(ActionEvent ae) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/buscar_departamentos.fxml"));
        BuscarDepartamentosController c = new BuscarDepartamentosController();
        loader.setController(c);
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.getIcons().add(new Image("/img/icon.png"));
            stage.setTitle("Buscar Departamentos");
            stage.show();
        } catch (IOException e) {
            ExceptionHandler.handle(e);
        }
    }

    @FXML
    private void abrirBuscarAsignaturas(ActionEvent ae) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/buscar_asignaturas.fxml"));
        BuscarAsignaturasController c = new BuscarAsignaturasController();
        loader.setController(c);
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.getIcons().add(new Image("/img/icon.png"));
            stage.setTitle("Buscar Asignaturas");
            stage.show();
        } catch (IOException e) {
            ExceptionHandler.handle(e);
        }
    }

    @FXML
    private void abrirCrearAlumnos(ActionEvent ae){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/crear_alumnos.fxml"));
        CrearAlumnosController c = new CrearAlumnosController();
        loader.setController(c);
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.getIcons().add(new Image("/img/icon.png"));
            stage.setTitle("Crear Alumnos");
            stage.show();
        } catch (IOException e) {
            ExceptionHandler.handle(e);
        }
    }

    @FXML
    private void abrirDangerZone(ActionEvent ae){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/danger_zone.fxml"));
        DangerZoneController c = new DangerZoneController();
        loader.setController(c);
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.getIcons().add(new Image("/img/icon.png"));
            stage.setTitle("Xestionar Base de Datos");
            stage.show();
        } catch (IOException e) {
            ExceptionHandler.handle(e);
        }
    }

    @FXML
    private void abrirCrearProfesores(ActionEvent ae){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/crear_profesores.fxml"));
        CrearProfesoresController c = new CrearProfesoresController();
        loader.setController(c);
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.getIcons().add(new Image("/img/icon.png"));
            stage.setTitle("Crear Profesores");
            stage.show();
        } catch (IOException e) {
            ExceptionHandler.handle(e);
        }
    }
    @FXML
    private void abrirCrearDepartamentos(ActionEvent ae){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/crear_departamentos.fxml"));
        CrearDepartamentosController c = new CrearDepartamentosController();
        loader.setController(c);
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.getIcons().add(new Image("/img/icon.png"));
            stage.setTitle("Crear Departamentos");
            stage.show();
        } catch (IOException e) {
            ExceptionHandler.handle(e);
        }
    }
    @FXML
    private void abrirCrearAsignaturas(ActionEvent ae){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/crear_asignaturas.fxml"));
        CrearAsignaturasController c = new CrearAsignaturasController();
        loader.setController(c);
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.getIcons().add(new Image("/img/icon.png"));
            stage.setTitle("Crear Asignaturas");
            stage.show();
        } catch (IOException e) {
            ExceptionHandler.handle(e);
        }
    }
}
