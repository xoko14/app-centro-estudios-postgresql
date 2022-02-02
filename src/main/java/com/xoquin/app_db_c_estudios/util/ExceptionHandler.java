package com.xoquin.app_db_c_estudios.util;

import com.xoquin.app_db_c_estudios.factory.DialogFactory;

/**
 * Clase para xestionar excepcións. Só ten un método estático que lanzará un diálogo coa excepción trataada.
 */
public class ExceptionHandler {

    /**
     * Lanza un diálogo coa excepción tratada.
     * @param e Excepción a amosar no diálogo.
     */
    public static void handle(Exception e){
        DialogFactory df = new DialogFactory(DialogFactory.EXCEPTION_DIALOG);
        df.setException(e);
        df.launch();
    }
}
