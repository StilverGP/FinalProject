package com.github.StilverGP.model.connection;

public interface InitializeDatabaseInterfce {
    void initialize();

    void createDatabase();

    void useDatabase();

    void createTables();

    void insertDefaultData();

    void closeConnection();
}
