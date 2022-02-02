package com.xoquin.app_db_c_estudios.controller;

import com.xoquin.app_db_c_estudios.factory.DAOFactory;

/**
 * Clase pai de todas as que necesiten conectarse á base de datos
 * de maneira que só haxa unha instancia de MariaDBDAOFactory.
 */
public class DBViewController {
    protected DAOFactory db = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
}
