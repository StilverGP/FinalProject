package com.github.StilverGP.model.entity;

import java.util.Objects;

public class Room {
    private int roomNumber;
    private RoomType roomType;
    private int numberOfBeds;
    private double priceNight;
    private boolean available;

    public Room(int roomNumber, RoomType roomType, int numberOfBeds, double priceNight, boolean available) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.priceNight = priceNight;
        this.available = available;
    }

    public Room() {
        this(0,RoomType.STANDARD,0,0.00,true);
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public double getPriceNight() {
        return priceNight;
    }

    public void setPriceNight(double priceNight) {
        this.priceNight = priceNight;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber == room.roomNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(roomNumber);
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", roomType=" + roomType +
                ", numberOfBeds=" + numberOfBeds +
                ", priceNight=" + priceNight +
                ", available=" + available +
                '}';
    }
}
