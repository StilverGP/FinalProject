package com.github.StilverGP.model.dao;

import com.github.StilverGP.model.connection.ConnectionMariaDB;
import com.github.StilverGP.model.entity.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements DAO<User, String> {
    private static final String INSERT = "INSERT into User(dni, name, username, password, mail, phone) VALUES (?,?,?,?,?,?)";
    private static final String UPDATEUSERNAME = "UPDATE User SET username=? where id_user=?";
    private static final String UPDATENAME = "UPDATE User SET name=? where id_user=?";
    private static final String UPDATEMAIL = "UPDATE User SET mail=? where id_user=?";
    private static final String FINDBYID = "SELECT id_user, dni, name, username, password, mail, phone FROM User WHERE username = ?";
    private static final String DELETE = "DELETE FROM User WHERE id_user=?";

    private Connection conn;

    public UserDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public User add(User entity) {
        User user = entity;
        if (entity != null) {
            String dni = entity.getDni();
            if (dni != null) {
                User isInDataBase = findById(dni);
                if (isInDataBase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(INSERT)) {
                        pst.setString(1, entity.getDni());
                        pst.setString(2, entity.getName());
                        pst.setString(3, entity.getUsername());
                        pst.setString(4, entity.getPassword());
                        pst.setString(5, entity.getMail());
                        pst.setString(6, entity.getPhone());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return user;
    }

    @Override
    public User update(User entity) {
        User user = entity;
        if (entity != null) {
            String dni = entity.getDni();
            if (dni != null) {
                User isInDataBase = findById(dni);
                if (isInDataBase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(UPDATEUSERNAME)) {
                        pst.setString(1, entity.getUsername());
                        pst.setInt(2, entity.getId());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return user;
    }

    public User updateName(User entity) {
        User user = entity;
        if (entity != null) {
            String dni = entity.getDni();
            if (dni != null) {
                User isInDataBase = findById(dni);
                if (isInDataBase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(UPDATENAME)) {
                        pst.setString(1, entity.getName());
                        pst.setInt(2, entity.getId());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return user;
    }

    public User updateMail(User entity) {
        User user = entity;
        if (entity != null) {
            String dni = entity.getDni();
            if (dni != null) {
                User isInDataBase = findById(dni);
                if (isInDataBase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(UPDATEMAIL)) {
                        pst.setString(1, entity.getMail());
                        pst.setInt(2, entity.getId());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return user;
    }

    @Override
    public User findById(String id) {
        User result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYID)) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id_user"));
                    user.setDni(rs.getString("dni"));
                    user.setName(rs.getString("name"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setMail(rs.getString("mail"));
                    user.setPhone(rs.getString("phone"));
                    result = user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public User delete(User entity) {
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
    public void close() throws IOException {

    }
}
