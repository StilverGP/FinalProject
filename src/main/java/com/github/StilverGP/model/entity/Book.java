package com.github.StilverGP.model.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Book {

    private Integer id;
    private String cod_book;
    private LocalDate checkIn_date;
    private LocalDate checkOut_date;
    private User user;
    private Room room;

    public Book(String cod_book, LocalDate checkIn_date, LocalDate checkOut_date, User user, Room room) {
        this.cod_book = cod_book;
        this.checkIn_date = checkIn_date;
        this.checkOut_date = checkOut_date;
        this.user = user;
        this.room = room;
    }

    public Book() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCod_book() {
        return cod_book;
    }

    public void setCod_book(String cod_book) {
        this.cod_book = cod_book;
    }

    public LocalDate getCheckIn_date() {
        return checkIn_date;
    }

    public void setCheckIn_date(LocalDate checkIn_date) {
        this.checkIn_date = checkIn_date;
    }

    public LocalDate getCheckOut_date() {
        return checkOut_date;
    }

    public void setCheckOut_date(LocalDate checkOut_date) {
        this.checkOut_date = checkOut_date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(cod_book, book.cod_book);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cod_book);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id_book=" + id +
                ", cod_book='" + cod_book + '\'' +
                ", checkIn_date=" + checkIn_date +
                ", checkOut_date=" + checkOut_date +
                ", user=" + user +
                ", room=" + room +
                '}';
    }
}
