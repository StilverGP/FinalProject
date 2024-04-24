package com.github.StilverGP.model.dao;

import com.github.StilverGP.model.connection.ConnectionMariaDB;
import com.github.StilverGP.model.entity.Book;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class BookDAO implements DAO<Book, String> {
    private static final String INSERT = "INSERT into Room (cod_book, checkIn_date, checkOut_date, id_user, id_room) VALUES (?,?,?,?,?)";
    private static final String UPDATE = "UPDATE Book SET cod_book=? WHERE id_book=?";
    private static final String UPDATECHECKINDATE = "UPDATE Book SET checkIn_date=? WHERE id_book=?";
    private static final String UPDATECHECKOUTDATE = "UPDATE Book SET checkOut_date=? WHERE id_book=?";
    private static final String FINDBYID = "SELECT id_book, cod_book, checkIn_date, checkOut_date, id_user, id_room FROM Book WHERE cod_book=?";
    private static final String FIDBYUSER = "SELECT id_book, cod_book, checkIn_date, checkOut_date, id_user, id_room FROM Book WHERE id_user=?";
    private static final String FINDBYROOM = "SELECT id_book, cod_book, checkIn_date, checkOut_date, id_user, id_room FROM Book WHERE id_room=?";
    private static final String DELETE = "DELETE FROM Book WHERE id_book=?";


    Connection conn;

    public BookDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Book add(Book entity) {
        Book book = entity;
        if (entity != null) {
            String cod_book = entity.getCod_book();
            if (cod_book != null) {
                Book isInDataBase = findById(cod_book);
                if (isInDataBase != null) {
                    try(PreparedStatement pst = conn.prepareStatement(INSERT)) {
                        pst.setString(1, entity.getCod_book());
//                        pst.setDate(2, entity.getCheckIn_date());
//                        pst.setDate(3, entity.getCheckOut_date());
                        pst.setInt(4, entity.getUser().getId_user());
                        pst.setInt(5, entity.getRoom().getId_Room());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return book;
    }

    @Override
    public Book update(Book entity) {
        Book book = entity;
        if (entity != null) {
            String cod_book = entity.getCod_book();
            if (cod_book != null) {
                Book isInDataBase = findById(cod_book);
                if (isInDataBase != null) {
                    try(PreparedStatement pst = conn.prepareStatement(UPDATE)) {
                        pst.setString(1, entity.getCod_book());
                        pst.setInt(2, entity.getId_book());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return book;
    }

    @Override
    public Book findById(String id) {
        return null;
    }

    @Override
    public Book delete(Book entity) {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
