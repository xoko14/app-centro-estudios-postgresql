package com.xoquin.app_db_c_estudios.factory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Clase utilizada para crear diálogos que bloquean o foco do resto das ventás no diálogo.
 */
public class DialogFactory {
    /**
     * Diálogo de resposta aceptar/cancelar.
     */
    public static final int YES_NO_DIALOG = 1;

    /**
     * Dialogo para mostrar información simple.
     */
    public static final int INFO_DIALOG = 2;

    /**
     * Diálogo para mostrar unha excepción.
     */
    public static final int EXCEPTION_DIALOG = 3;

    /**
     * Resultado se o diálogo non remata a execución.
     */
    public static final int RESULT_UNEXECUTED = -1;

    /**
     * Resultado por defecto se o diálogo remata a súa execución.
     */
    public static final int RESULT_DEFAULT = 0;

    /**
     * Resultado de responder cancelar.
     */
    public static final int RESULT_NO = 1;

    /**
     * Resultado de responder aceptar.
     */
    public static final int RESULT_YES = 2;

    private int currentMode;

    private String text;

    /**
     * Obter a mensaxe que se mostra no diálogo.
     * @return Mensaxe se mostra no diálogo.
     */
    public String getText() {
        return this.text;
    }

    /**
     * Establecer a mensaxe que se mostra no diálogo.
     * @param text Mensaxe que se mostra no diálogo.
     */
    public void setText(String text) {
        this.text = text;
    }

    private int result;

    /**
     * Obter o resultado de executar o diálogo.
     * @return Resultado de executar o diálogo.
     */
    public int getResult() {
        return result;
    }

    private Exception exception;

    /**
     * Establece a excepción a mostrar.
     * @param e Excepción a mostrar.
     */
    public void setException(Exception e){
        this.exception = e;
    }

    /**
     * Crea un DialogFactory.
     * @param mode Modo de diálogo.
     */
    public DialogFactory(int mode) {
        currentMode = mode;
        result = RESULT_UNEXECUTED;
    }

    /**
     * Lanzar o diálogo unha vez establecidas as propiedades.
     */
    public void launch() {
        switch (currentMode) {
        case YES_NO_DIALOG:
            createAlert();
            break;

        case INFO_DIALOG:
            createInfo();
            break;

        case EXCEPTION_DIALOG:
            createException();
            break;
        }

    }

    /**
     * Lanzar un diálogo de tipo YES_NO_DIALOG e obter o resultado.
     */
    private void createAlert() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Alerta");
        alert.setHeaderText("Confirmar");
        alert.setContentText(text);

        Optional<ButtonType> resultB = alert.showAndWait();
        if (resultB.get() == ButtonType.OK) {
            result = RESULT_YES;
        } else {
            result = RESULT_NO;
        }
    }

    /**
     * Lanzar un diálogo de tipo INFO_DIALOG.
     */
    private void createInfo() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
        result = RESULT_DEFAULT;
    }

    /**
     * Tratar a excepción obtida e lanzar un diálogo de tipo EXCEPTION_DIALOG.
     */
    private void createException() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Excepción");
        alert.setHeaderText("Ocorreu un erro");
        alert.setContentText(exception.getLocalizedMessage());

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("Información da excepción:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
        result = RESULT_DEFAULT;
    }
}
