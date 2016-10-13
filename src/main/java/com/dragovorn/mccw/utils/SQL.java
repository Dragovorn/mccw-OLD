package com.dragovorn.mccw.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL {

    private final String hostname;
    private final String database;
    private final String user;

    private final int port;

    private final Connection connection;

    public SQL(String hostname, int port, String database, String user, String password) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":"  + port + "/" + database + "?user=" + user + "&password=" + password + "&useUnicode=true&characterEncoding=UTF-8");
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.user = user;
    }

    public String getHostname() {
        return this.hostname;
    }

    public String getDatabase() {
        return this.database;
    }

    public String getUser() {
        return this.user;
    }

    public int getPort() {
        return this.port;
    }

    public Connection getConnection() {
        return this.connection;
    }
}