package com.sccs.model.database;

public class SingletonDatabase {

    public static Database getDatabase(String name) {
        if (name.equals("postgresql")) {
            return PostgreSQL.getInstance();
        }
        return null;
    }
}
