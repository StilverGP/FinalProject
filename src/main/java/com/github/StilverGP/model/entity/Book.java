package com.github.StilverGP.model.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Book {

    private String cod_book;
    private LocalDate checkIn_date;
    private LocalDate checkOut_date;
    private int id_user;
    private int id_room;

    public Book(String cod_book, LocalDate checkIn_date, LocalDate checkOut_date, int id_user, int id_room) {
        this.cod_book = cod_book;
        this.checkIn_date = checkIn_date;
        this.checkOut_date = checkOut_date;
        this.id_user = id_user;
        this.id_room = id_room;
    }

    public Book() {
        this("",LocalDate.now(),LocalDate.now(),0,0);
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

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_room() {
        return id_room;
    }

    public void setId_room(int id_room) {
        this.id_room = id_room;
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
}
