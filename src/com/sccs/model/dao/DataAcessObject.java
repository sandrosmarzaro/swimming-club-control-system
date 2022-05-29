package com.sccs.model.dao;

import java.sql.Connection;

public abstract class DataAcessObject {

    protected static Connection connectionDAO;

    public static Connection getConnection() {
        return connectionDAO;
    }

    public static void setConnection(Connection connection) {
        DataAcessObject.connectionDAO = connection;
    }
}
