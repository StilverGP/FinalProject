package com.github.StilverGP.model.entity;

import java.awt.*;
import java.util.Objects;

public class Room {
    private Integer id_room;
    private Image image;
    private Integer roomNumber;
    private RoomType roomType;
    private Integer numberOfBeds;
    private double priceNight;
    private Boolean available;

    public Room(Image image, Integer roomNumber, RoomType roomType, Integer numberOfBeds, double priceNight, Boolean available) {
        this.image = image;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.priceNight = priceNight;
        this.available = available;
    }

    public Room() {}

    public Integer getId_Room() {
        return id_room;
    }

    public void setId_room(Integer id_room) {
        this.id_room = id_room;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
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
        if (value.toLowerCase().matches("standard")) type = RoomType.STANDARD;
        if (value.toLowerCase().matches("suite")) type = RoomType.SUITE;
        if (value.toLowerCase().matches("family")) type = RoomType.FAMILY;
        if (value.toLowerCase().matches("deluxe")) type = RoomType.DELUXE;
        return type;
    }

    public String getRoomTypeValue(RoomType type) {
        String typeValue = "";
        if (type == RoomType.STANDARD) typeValue = "standard";
        if (type == RoomType.SUITE) typeValue = "suite";
        if (type == RoomType.FAMILY) typeValue = "family";
        if (type == RoomType.DELUXE) typeValue = "deluxe";
        return typeValue;
    }

    public String getAvailabilityValue(Boolean available) {
        String abailavilityValue = "";
        if (available) abailavilityValue = "disponible";
        if (!available) abailavilityValue = "ocupada";
        return abailavilityValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomNumber, room.roomNumber) || Objects.equals(id_room, room.id_room);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id_room);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id_room=" + id_room +
                ", imagePath=" + image +
                ", roomNumber=" + roomNumber +
                ", roomType=" + roomType +
                ", numberOfBeds=" + numberOfBeds +
                ", priceNight=" + priceNight +
                ", available=" + available +
                '}';
    }

}
