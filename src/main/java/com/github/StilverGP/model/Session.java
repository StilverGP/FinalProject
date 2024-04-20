package com.github.StilverGP.model;

import com.github.StilverGP.model.entity.User;

public class Session {
    private static Session _instance;
    private User loggedInUser;

    public Session() {
        loggedInUser = new User();
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
        if (loggedInUser == null) loggedInUser = user;
    }

    public void logout() {
        loggedInUser = null;
    }
}
