package com.github.StilverGP.model.entity;

import com.github.StilverGP.utils.Security;

import java.util.Objects;

public class User {
    private Integer id;
    private String dni;
    private String name;
    private String username;
    private String password;
    private String mail;
    private String phone;
    private boolean isAdmin;

    public User(String dni, String name,String username, String password, String mail, String phone) {
        this.dni = dni;
        this.name = name;
        this.username = username;
        setPassword(password);
        this.mail = mail;
        this.phone = phone;
        this.isAdmin = false;
    }

    public User() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Security.hashPassword(password);
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Checks if the provided password matches the stored hashed password.
     *
     * @param password the password to be checked.
     * @return true if the provided password matches the stored hashed password, false if not.
     */
    public boolean isMyPassword(String password) {
        return this.password.equals(Security.hashPassword(password));
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) || Objects.equals(dni, user.dni) || Objects.equals(mail, user.mail) || Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dni, mail, phone);
    }

    @Override
    public String toString() {
        return "User{" +
                "id_user=" + id +
                ", dni='" + dni + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
