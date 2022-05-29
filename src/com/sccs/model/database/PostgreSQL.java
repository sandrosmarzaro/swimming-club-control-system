package com.sccs.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PostgreSQL implements Database {

    private static final Database instance = new PostgreSQL();

    private PostgreSQL() {}

    public static Database getInstance() {
        return instance;
    }

    @Override
    public Connection connect() {
        final String url = "jdbc:postgresql://127.0.0.1/scen";
        final String user = "postgres";
        final String password = "postgres";

        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, user, password);
        }
        catch (ClassNotFoundException | SQLException classException) {
            Logger.getLogger(PostgreSQL.class.getName()).log(Level.SEVERE, null, classException);
            return null;
        }
    }

    @Override
    public void disconnect(Connection connection) {
        try {
            connection.close();
        }
        catch (SQLException sqlException) {
            Logger.getLogger(PostgreSQL.class.getName()).log(Level.SEVERE, null, sqlException);
        }
    }

}

