package com.sccs.model.database;

import java.sql.Connection;

public interface Database {
    Connection connect();
    void disconnect(Connection connection);
}
