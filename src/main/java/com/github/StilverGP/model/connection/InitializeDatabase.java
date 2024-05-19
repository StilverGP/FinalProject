package com.github.StilverGP.model.connection;

import java.io.IOException;
import java.sql.*;

public class InitializeDatabase implements InitializeDatabaseInterfce {
    private static final String CREATEDATABASE = "CREATE DATABASE IF NOT EXISTS hotel";
    private static final String USEDATABASE = "USE hotel";
    private static final String CREATETABLEUSER = "create table if not exists User " +
            "(" +
            "    id_user int auto_increment primary key," +
            "    dni char(9) not null unique, " +
            "    name varchar(60), " +
            "    username varchar(60) unique, " +
            "    password char(64), " +
            "    mail char(128) unique, " +
            "    phone varchar (15), " +
            "    isAdmin boolean default false" +
            ")";
    private static final String CREATETABLEROOM = "create table if not exists Room\n" +
            "(" +
            "    id_room int auto_increment primary key, " +
            "    image longblob, " +
            "    room_number int unique, " +
            "    room_type char(10), " +
            "    number_beds int(1), " +
            "    price_night double(5,2), " +
            "    available boolean" +
            ")";
    private static final String CREATETABLEBOOK = "create table if not exists Book\n" +
            "(" +
            "    id_book int auto_increment primary key, " +
            "    cod_book char(16) not null unique, " +
            "    checkIn_date date, " +
            "    checkOut_date date, " +
            "    id_room int, " +
            "    id_user varchar(60), " +
            "    foreign key (id_room) references Room(room_number) on delete cascade on update cascade, " +
            "    foreign key (id_user) references  User(username) on delete cascade on update cascade" +
            ")";

    private Connection conn;

    public InitializeDatabase() {
        try {
            conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Initializes the database by creating it, creating the necessary tables,
     * inserting default data, and then closing the connection.
     */
    @Override
    public void initialize() {
        createDatabase();
        createTables();
        insertDefaultData();
        closeConnection();
    }

    /**
     * Creates the database.
     */
    @Override
    public void createDatabase() {
        try (PreparedStatement pst = conn.prepareStatement(CREATEDATABASE)) {
            pst.executeUpdate();
            useDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches to the new created database.
     */
    @Override
    public void useDatabase() {
        try (PreparedStatement upst = conn.prepareStatement(USEDATABASE)) {
            upst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the necessary tables in the database.
     */
    @Override
    public void createTables() {
        try (PreparedStatement pst = conn.prepareStatement(CREATETABLEUSER)) {
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement pst = conn.prepareStatement(CREATETABLEROOM)) {
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement pst = conn.prepareStatement(CREATETABLEBOOK)) {
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts default data into the database.
     */
    @Override
    public void insertDefaultData() {
        try {
            InsertContent.addDefaultUser();
            InsertContent.addDefaultRooms();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the database connection.
     */
    @Override
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
