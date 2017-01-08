package com.dragovorn.mccw.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.util.Arrays;

public class Database {

    private MongoClient client;

    private final String ip;
    private final String database;

    private final int port;

    public Database(String ip, int port, String username, String database, char[] password) {
        this.ip = ip;
        this.port = port;
        this.database = database;
        this.client = new MongoClient(new ServerAddress(ip, port), Arrays.asList(MongoCredential.createMongoCRCredential(username, database, password)));
    }

    public void close() {
        if (this.client == null) {
            return;
        }

        this.client.close();
    }
}