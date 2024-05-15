package com.github.StilverGP.model.entity;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class Room {
    private Integer id;
    private BufferedImage image;
    private Integer roomNumber;
    private RoomType roomType;
    private Integer numberOfBeds;
    private double priceNight;
    private Boolean available;

    public Room(BufferedImage image, Integer roomNumber, RoomType roomType, Integer numberOfBeds, double priceNight, Boolean available) {
        this.image = image;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.priceNight = priceNight;
        this.available = available;
    }

    public Room() {}

    public Integer getId_Room() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Integer getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(Integer numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public double getPriceNight() {
        return priceNight;
    }

    public void setPriceNight(double priceNight) {
        this.priceNight = priceNight;
    }

    public Boolean isAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public RoomType setRoomTypeValue(String value) {
        RoomType type = null;
        if (value.matches("Estandar")) type = RoomType.STANDARD;
        if (value.matches("Suite")) type = RoomType.SUITE;
        if (value.matches("Familiar")) type = RoomType.FAMILY;
        if (value.matches("Deluxe")) type = RoomType.DELUXE;
        return type;
    }

    public String getRoomTypeValue(RoomType type) {
        String typeValue = "";
        if (type == RoomType.STANDARD) typeValue = "Estandar";
        if (type == RoomType.SUITE) typeValue = "Suite";
        if (type == RoomType.FAMILY) typeValue = "Familiar";
        if (type == RoomType.DELUXE) typeValue = "Deluxe";
        return typeValue;
    }

    public String getAvailabilityValue(Boolean available) {
        String abailavilityValue = "";
        if (available) abailavilityValue = "Disponible";
        if (!available) abailavilityValue = "Ocupada";
        return abailavilityValue;
    }

    public boolean setAvailabilityValue(String s) {
        boolean available = true;
        if (s.matches("disponible")) available = true;
        if (s.matches("ocupada")) available = false;
        return available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomNumber, room.roomNumber) || Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id_room=" + id +
                ", image=" + image +
                ", roomNumber=" + roomNumber +
                ", roomType=" + roomType +
                ", numberOfBeds=" + numberOfBeds +
                ", priceNight=" + priceNight +
                ", available=" + available +
                '}';
    }

}
