package com.github.StilverGP.model.connection;

import com.github.StilverGP.App;
import com.github.StilverGP.model.dao.RoomDAO;
import com.github.StilverGP.model.dao.UserDAO;
import com.github.StilverGP.model.entity.Room;
import com.github.StilverGP.model.entity.RoomType;
import com.github.StilverGP.model.entity.User;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InsertContent {

    public static void addDefaultUser() {
        UserDAO uDAO = new UserDAO();

        User admin = new User("12345678K", "admin", "admin", "admin", "admin@admin.admin", "123456789");
        admin.setAdmin(true);
        if (!admin.equals(uDAO.findById(admin.getUsername()))) {
            uDAO.add(admin);
        }
    }

    public static void addDefaultRooms() throws IOException {
        RoomDAO roomDAO = new RoomDAO();
        List<Room> roomList = new ArrayList<>();
        Room room1 = new Room(ImageIO.read(App.class.getResourceAsStream("images/image1.jpg")), 101, RoomType.STANDARD, 1, 50.0, true);
        roomList.add(room1);
        Room room2 = new Room(ImageIO.read(App.class.getResourceAsStream("images/image2.jpg")), 102, RoomType.STANDARD, 2, 60.0, true);
        roomList.add(room2);
        Room room3 = new Room(ImageIO.read(App.class.getResourceAsStream("images/image3.jpg")), 103, RoomType.SUITE, 1, 90.0, true);
        roomList.add(room3);
        Room room4 = new Room(ImageIO.read(App.class.getResourceAsStream("images/image4.jpg")), 104, RoomType.SUITE, 2, 100.0, true);
        roomList.add(room4);
        Room room5 = new Room(ImageIO.read(App.class.getResourceAsStream("images/image5.jpg")), 105, RoomType.DELUXE, 1, 120.0, true);
        roomList.add(room5);
        Room room6 = new Room(ImageIO.read(App.class.getResourceAsStream("images/image6.jpg")), 106, RoomType.DELUXE, 2, 150.0, true);
        roomList.add(room6);
        Room room7 = new Room(ImageIO.read(App.class.getResourceAsStream("images/image7.jpg")), 107, RoomType.FAMILY, 3, 80.0, true);
        roomList.add(room7);
        Room room8 = new Room(ImageIO.read(App.class.getResourceAsStream("images/image8.jpg")), 108, RoomType.FAMILY, 5, 65.0, true);
        roomList.add(room8);
        Room room9 = new Room(ImageIO.read(App.class.getResourceAsStream("images/image9.jpg")), 109, RoomType.STANDARD, 1, 40.0, true);
        roomList.add(room9);
        Room room10 = new Room(ImageIO.read(App.class.getResourceAsStream("images/image10.jpg")), 110, RoomType.STANDARD, 2, 20.0, true);
        roomList.add(room10);
        Room room11 = new Room(ImageIO.read(App.class.getResourceAsStream("images/image11.jpg")), 111, RoomType.STANDARD, 1, 55.0, true);
        roomList.add(room11);
        Room room12 = new Room(ImageIO.read(App.class.getResourceAsStream("images/image12.jpg")), 112, RoomType.STANDARD, 2, 45.0, true);
        roomList.add(room12);
        Room room13 = new Room(ImageIO.read(App.class.getResourceAsStream("images/image13.jpg")), 113, RoomType.SUITE, 1, 95.0, true);
        roomList.add(room13);
        Room room14 = new Room(ImageIO.read(App.class.getResourceAsStream("images/image14.jpg")), 114, RoomType.SUITE, 2, 87.0, true);
        roomList.add(room14);
        Room room15 = new Room(ImageIO.read(App.class.getResourceAsStream("images/image15.jpg")), 115, RoomType.DELUXE, 1, 130.0, true);
        roomList.add(room15);
        Room room16 = new Room(ImageIO.read(App.class.getResourceAsStream("images/image16.jpg")), 116, RoomType.DELUXE, 2, 160.0, true);
        roomList.add(room16);
        Room room17 = new Room(ImageIO.read(App.class.getResourceAsStream("images/image17.jpg")), 117, RoomType.FAMILY, 3, 85.0, true);
        roomList.add(room17);
        Room room18 = new Room(ImageIO.read(App.class.getResourceAsStream("images/image18.jpg")), 118, RoomType.FAMILY, 4, 70.0, true);
        roomList.add(room18);
        Room room19 = new Room(ImageIO.read(App.class.getResourceAsStream("images/image19.jpg")), 119, RoomType.STANDARD, 1, 45.0, true);
        roomList.add(room19);
        Room room20 = new Room(ImageIO.read(App.class.getResourceAsStream("images/image20.jpg")), 120, RoomType.STANDARD, 2, 15.0, true);
        roomList.add(room20);
        for (Room room : roomList) {
            roomDAO.add(room);
        }
    }
}
