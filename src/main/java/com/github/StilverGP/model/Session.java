package com.github.StilverGP.model;

import com.github.StilverGP.model.entity.User;

public class Session {
    private static Session _instance;
    private User loggedInUser;

    public Session() {
        loggedInUser = null;
    }

    public static Session getInstance() {
        if (_instance == null) {
            _instance = new Session();
        }
        return _instance;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void login(User user) {
        loggedInUser = user;
    }

    public void logout() {
        loggedInUser = null;
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }
}
