package com.github.StilverGP.model.dao;

import com.github.StilverGP.model.connection.ConnectionMariaDB;
import com.github.StilverGP.model.entity.Book;
import com.github.StilverGP.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO implements DAO<Book, String> {
    private static final String INSERT = "INSERT into Book (cod_book, checkIn_date, checkOut_date, id_user, id_room) VALUES (?,?,?,?,?)";
    private static final String UPDATE = "UPDATE Book SET cod_book=? WHERE id_book=?";
    private static final String UPDATECHECKINDATE = "UPDATE Book SET checkIn_date=? WHERE id_book=?";
    private static final String UPDATECHECKOUTDATE = "UPDATE Book SET checkOut_date=? WHERE id_book=?";
    private static final String FINDBYID = "SELECT id_book, cod_book, checkIn_date, checkOut_date, id_user, id_room FROM Book WHERE cod_book=?";
    private static final String FINDBYUSER = "SELECT id_book, cod_book, checkIn_date, checkOut_date, id_user, id_room FROM Book WHERE id_user=?";
    private static final String DELETE = "DELETE FROM Book WHERE id_book=?";

    Connection conn;

    public BookDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    /**
     * Adds a new Book to the database.
     *
     * @param entity the Book to be added to the database.
     * @return the added Book.
     */
    @Override
    public Book add(Book entity) {
        Book book = entity;
        if (entity != null) {
            String cod_book = entity.getCod_book();
            if (cod_book != null) {
                Book isInDataBase = findById(cod_book);
                if (isInDataBase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(INSERT)) {
                        pst.setString(1, entity.getCod_book());
                        pst.setDate(2, Date.valueOf(entity.getCheckIn_date()));
                        pst.setDate(3, Date.valueOf(entity.getCheckOut_date()));
                        pst.setString(4, entity.getUser().getUsername());
                        pst.setInt(5, entity.getRoom().getRoomNumber());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return book;
    }

    /**
     * Updates a Book 'cod_book' in the database.
     *
     * @param entity the Book entity to be updated.
     * @return the updated Book entity.
     */
    @Override
    public Book update(Book entity) {
        Book book = entity;
        if (entity != null) {
            String cod_book = entity.getCod_book();
            if (cod_book != null) {
                Book isInDataBase = findById(cod_book);
                if (isInDataBase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
                        pst.setString(1, entity.getCod_book());
                        pst.setInt(2, entity.getId());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return book;
    }

    /**
     * Updates a Book 'checkIn_date' in the database.
     *
     * @param entity the Book entity to be updated.
     * @return the updated Book entity.
     */
    public Book updateCheckIn(Book entity) {
        Book book = entity;
        if (entity != null) {
            String cod_book = entity.getCod_book();
            if (cod_book != null) {
                Book isInDataBase = findById(cod_book);
                if (isInDataBase != null) {
                    try (PreparedStatement pst = conn.prepareStatement(UPDATECHECKINDATE)) {
                        pst.setDate(1, Date.valueOf(entity.getCheckIn_date()));
                        pst.setInt(2, entity.getId());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return book;
    }

    /**
     * Updates a Book 'checkOut_date' in the database.
     *
     * @param entity the Book entity to be updated.
     * @return the updated Book entity.
     */
    public Book updateCheckOut(Book entity) {
        Book book = entity;
        if (entity != null) {
            String cod_book = entity.getCod_book();
            if (cod_book != null) {
                Book isInDataBase = findById(cod_book);
                if (isInDataBase != null) {
                    try (PreparedStatement pst = conn.prepareStatement(UPDATECHECKOUTDATE)) {
                        pst.setDate(1, Date.valueOf(entity.getCheckOut_date()));
                        pst.setInt(2, entity.getId());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return book;
    }

    /**
     * Finds a Book in the database by its ID.
     *
     * @param id the ID of the Book entity to find.
     * @return the found Book, or null if not found.
     */
    @Override
    public Book findById(String id) {
        Book result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYID)) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("id_book"));
                    book.setCod_book(rs.getString("cod_book"));
                    book.setCheckIn_date(rs.getDate("checkIn_date").toLocalDate());
                    book.setCheckOut_date(rs.getDate("checkOut_date").toLocalDate());
                    UserDAO uDAO = new UserDAO();
                    book.setUser(uDAO.findById(rs.getString("id_user")));
                    RoomDAO rDAO = new RoomDAO();
                    book.setRoom(rDAO.findById(rs.getInt("id_room")));
                    result = book;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Finds all Books associated with a given User.
     *
     * @param user the User whose books want to be found.
     * @return a list of Books associated with the User.
     */
    public List<Book> findByUser(User user) {
        List<Book> books = new ArrayList<>();
        if (user != null) {
            try (PreparedStatement pst = conn.prepareStatement(FINDBYUSER)) {
                pst.setString(1, user.getUsername());
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        Book book = new Book();
                        book.setId(rs.getInt("id_book"));
                        book.setCod_book(rs.getString("cod_book"));
                        book.setCheckIn_date(rs.getDate("checkIn_date").toLocalDate());
                        book.setCheckOut_date(rs.getDate("checkOut_date").toLocalDate());
                        book.setUser(user);
                        RoomDAO rDAO = new RoomDAO();
                        book.setRoom(rDAO.findById(rs.getInt("id_room")));
                        books.add(book);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return books;
    }

    /**
     * Deletes a Book from the database.
     *
     * @param entity the Book to be deleted.
     * @return the deleted Book, or null if the deletion fails.
     */
    @Override
    public Book delete(Book entity) {
        if (entity != null) {
            try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
                pst.setInt(1, entity.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                entity = null;
            }
        }
        return entity;
    }

    @Override
    public void close() {

    }
}