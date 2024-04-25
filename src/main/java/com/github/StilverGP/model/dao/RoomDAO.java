package com.github.StilverGP.model.dao;

import com.github.StilverGP.model.connection.ConnectionMariaDB;
import com.github.StilverGP.model.entity.Room;
import com.github.StilverGP.model.entity.RoomType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomDAO implements DAO<Room, Integer> {
    private static final String INSERT = "INSERT into Room (image, room_number, room_type, number_beds, price_night, available) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE Room SET room_number=? WHERE id_room=?";
    private static final String UPDATEPRICE = "UPDATE Room SET price=? WHERE id_room=?";
    private static final String UPDATEAVAILABILITY = "UPDATE Room SET availability=? WHERE id_room=?";
    private static final String FINDBYID = "SELECT id_room, image, room_number, room_type, number_beds, price_night, available FROM Room WHERE room_number=?";
    private static final String FINDBYTYPE = "SELECT id_room, image, room_number, room_type, number_beds, price_night, available FROM Room WHERE room_type=?";
    private static final String FINDBYBEDS = "SELECT id_room, image, room_number, room_type, number_beds, price_night, available FROM Room WHERE number_beds=?";
    private static final String DELETE = "DELETE FROM Room WHERE id_room=?";

    private Connection conn;

    public RoomDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Room add(Room entity) {
        Room room = entity;
        if (entity != null) {
            int roomNumber = entity.getRoomNumber();
            if (roomNumber > -1) {
                Room isInDataBase = findById(roomNumber);
                if (isInDataBase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(INSERT)) {
                        pst.setString(1, entity.getImagePath());
                        pst.setInt(2, entity.getRoomNumber());
                        pst.setString(3, String.valueOf(entity.getRoomType()));
                        pst.setInt(4, entity.getNumberOfBeds());
                        pst.setDouble(5, entity.getPriceNight());
                        pst.setBoolean(6, entity.isAvailable());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return room;
    }

    @Override
    public Room update(Room entity) {
        Room room = entity;
        if (entity != null) {
            int roomNumber = entity.getRoomNumber();
            if (roomNumber > -1) {
                Room isInDataBase = findById(roomNumber);
                if (isInDataBase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
                        pst.setInt(1, entity.getRoomNumber());
                        pst.setInt(2, entity.getId_Room());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return room;
    }

    public Room updatePrice(Room entity) {
        Room room = entity;
        if (entity != null) {
            int roomNumber = entity.getRoomNumber();
            if (roomNumber > -1) {
                Room isInDataBase = findById(roomNumber);
                if (isInDataBase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(UPDATEPRICE)) {
                        pst.setDouble(1, entity.getPriceNight());
                        pst.setInt(2, entity.getId_Room());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return room;
    }

    public Room updateAvailability(Room entity) {
        Room room = entity;
        if (entity != null) {
            int roomNumber = entity.getRoomNumber();
            if (roomNumber > -1) {
                Room isInDataBase = findById(roomNumber);
                if (isInDataBase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(UPDATEAVAILABILITY)) {
                        pst.setBoolean(1, entity.isAvailable());
                        pst.setInt(2, entity.getId_Room());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return room;
    }

    @Override
    public Room findById(Integer id) {
        Room result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Room room = new Room();
                    room.setId_room(rs.getInt("id_room"));
                    room.setImagePath(rs.getString("image"));
                    room.setRoomNumber(rs.getInt("room_number"));
                    room.setRoomType(room.setRoomTypeValue(rs.getString("room_type")));
                    room.setNumberOfBeds(rs.getInt("number_beds"));
                    room.setPriceNight(rs.getDouble("price_night"));
                    room.setAvailable(rs.getBoolean("available"));
                    result = room;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Room findByType(RoomType type) {
        Room result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYTYPE)) {
            pst.setString(1, String.valueOf(type));
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Room room = new Room();
                    room.setId_room(rs.getInt("id_room"));
                    room.setImagePath(rs.getString("image"));
                    room.setRoomNumber(rs.getInt("room_number"));
                    room.setRoomType(RoomType.valueOf(rs.getString("room_type")));
                    room.setNumberOfBeds(rs.getInt("number_beds"));
                    room.setPriceNight(rs.getDouble("price_night"));
                    room.setAvailable(rs.getBoolean("available"));
                    result = room;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return result;
    }

    public Room findByBeds(int beds) {
        Room result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYBEDS)) {
            pst.setInt(1, beds);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Room room = new Room();
                    room.setId_room(rs.getInt("id_room"));
                    room.setImagePath(rs.getString("image"));
                    room.setRoomNumber(rs.getInt("room_number"));
                    room.setRoomType(RoomType.valueOf(rs.getString("room_type")));
                    room.setNumberOfBeds(rs.getInt("number_beds"));
                    room.setPriceNight(rs.getDouble("price_night"));
                    room.setAvailable(rs.getBoolean("available"));
                    result = room;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Room delete(Room entity) {
        if (entity != null) {
            try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
                pst.setInt(1, entity.getId_Room());
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
