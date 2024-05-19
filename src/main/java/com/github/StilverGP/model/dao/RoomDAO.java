package com.github.StilverGP.model.dao;

import com.github.StilverGP.model.connection.ConnectionMariaDB;
import com.github.StilverGP.model.entity.Room;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO implements DAO<Room, Integer> {
    private static final String INSERT = "INSERT into Room (image, room_number, room_type, number_beds, price_night, available) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE Room SET room_number=? WHERE id_room=?";
    private static final String UPDATEPRICE = "UPDATE Room SET price_night=? WHERE room_number=?";
    private static final String UPDATEAVAILABILITY = "UPDATE Room SET available=? WHERE room_number=?";
    private static final String FINDALL = "SELECT id_room, image, room_number, room_type, number_beds, price_night, available FROM Room";
    private static final String FINDALLAVAILABLE = "SELECT id_room, image, room_number, room_type, number_beds, price_night, available FROM Room WHERE available=true";
    private static final String FINDBYID = "SELECT id_room, image, room_number, room_type, number_beds, price_night, available FROM Room WHERE room_number=?";
    private static final String DELETE = "DELETE FROM Room WHERE room_number=?";

    private Connection conn;

    public RoomDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    /**
     * Adds a new Room to the database.
     *
     * @param entity the Room to be added to the database.
     * @return the added Room.
     */
    @Override
    public Room add(Room entity) {
        Room room = entity;
        if (entity != null) {
            int roomNumber = entity.getRoomNumber();
            if (roomNumber > -1) {
                Room isInDataBase = findById(roomNumber);
                if (isInDataBase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(INSERT)) {
                        pst.setBinaryStream(1, imageToStream(entity.getImage()));
                        pst.setInt(2, entity.getRoomNumber());
                        pst.setString(3, String.valueOf(entity.getRoomTypeValue(entity.getRoomType())));
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

    /**
     * Updates a Room 'room_number' in the database.
     *
     * @param entity the Room to be updated.
     * @return the updated Room.
     */
    @Override
    public Room update(Room entity) {
        Room room = entity;
        if (entity != null) {
            int roomNumber = entity.getRoomNumber();
            if (roomNumber > 0) {
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

    /**
     * Updates a Room 'price_night' in the database.
     *
     * @param entity the Room to be updated.
     * @return the updated Room.
     */
    public Room updatePrice(Room entity) {
        Room room = entity;
        if (entity != null) {
            int roomNumber = entity.getRoomNumber();
            if (roomNumber > 0) {
                Room isInDataBase = findById(roomNumber);
                if (isInDataBase != null) {
                    try (PreparedStatement pst = conn.prepareStatement(UPDATEPRICE)) {
                        pst.setDouble(1, entity.getPriceNight());
                        pst.setInt(2, entity.getRoomNumber());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return room;
    }

    /**
     * Updates a Room availability in the database.
     *
     * @param entity the Room to be updated.
     * @return the updated Room.
     */
    public Room updateAvailability(Room entity) {
        Room room = entity;
        if (entity != null) {
            int roomNumber = entity.getRoomNumber();
            if (roomNumber > 0) {
                Room isInDataBase = findById(roomNumber);
                if (isInDataBase != null) {
                    try (PreparedStatement pst = conn.prepareStatement(UPDATEAVAILABILITY)) {
                        pst.setBoolean(1, entity.isAvailable());
                        pst.setInt(2, entity.getRoomNumber());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return room;
    }

    /**
     * Retrieves all Rooms from the database.
     *
     * @return a list containing all Rooms.
     */
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(FINDALL)){
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Room room = new Room();
                    room.setId(rs.getInt("id_room"));
                    InputStream is = rs.getBinaryStream("image");
                    if (is != null) {
                        BufferedImage image = ImageIO.read(is);
                        room.setImage(image);
                    }
                    room.setRoomNumber(rs.getInt("room_number"));
                    room.setRoomType(room.setRoomTypeValue(rs.getString("room_type")));
                    room.setNumberOfBeds(rs.getInt("number_beds"));
                    room.setPriceNight(rs.getDouble("price_night"));
                    room.setAvailable(rs.getBoolean("available"));
                    rooms.add(room);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return rooms;
    }

    /**
     * Retrieves all available Rooms from the database.
     *
     * @return a list containing all available Rooms.
     */
    public List<Room> findAllAvailable() {
        List<Room> rooms = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(FINDALLAVAILABLE)){
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Room room = new Room();
                    room.setId(rs.getInt("id_room"));
                    InputStream is = rs.getBinaryStream("image");
                    if (is != null) {
                        BufferedImage image = ImageIO.read(is);
                        room.setImage(image);
                    }
                    room.setRoomNumber(rs.getInt("room_number"));
                    room.setRoomType(room.setRoomTypeValue(rs.getString("room_type")));
                    room.setNumberOfBeds(rs.getInt("number_beds"));
                    room.setPriceNight(rs.getDouble("price_night"));
                    room.setAvailable(rs.getBoolean("available"));
                    rooms.add(room);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return rooms;
    }

    /**
     * Finds a Room in the database by its ID.
     *
     * @param id the ID of the Room to find.
     * @return the found Room, or null if not found.
     */
    @Override
    public Room findById(Integer id) {
        Room result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Room room = new Room();
                    room.setId(rs.getInt("id_room"));
                    Blob blob = rs.getBlob("image");
                    InputStream is = blob.getBinaryStream();
                    BufferedImage image = ImageIO.read(is);
                    room.setImage(image);
                    room.setRoomNumber(rs.getInt("room_number"));
                    room.setRoomType(room.setRoomTypeValue(rs.getString("room_type")));
                    room.setNumberOfBeds(rs.getInt("number_beds"));
                    room.setPriceNight(rs.getDouble("price_night"));
                    room.setAvailable(rs.getBoolean("available"));
                    result = room;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Deletes a Room from the database.
     *
     * @param entity the Room to be deleted.
     * @return the deleted Room, or null if the deletion fails.
     */
    @Override
    public Room delete(Room entity) {
        if (entity != null) {
            try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
                pst.setInt(1, entity.getRoomNumber());
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

    /**
     * Converts a BufferedImage to an InputStream.
     *
     * @param image the BufferedImage to be converted.
     * @return an InputStream representing the BufferedImage.
     */
    public InputStream imageToStream(BufferedImage image) {
        ByteArrayInputStream bais = null;
        if (image != null) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                bais = new ByteArrayInputStream(baos.toByteArray());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return bais;
    }
}
