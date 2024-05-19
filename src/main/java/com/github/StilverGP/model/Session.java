package com.github.StilverGP.model;

import com.github.StilverGP.model.entity.User;

public class Session implements SessionInterface {
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

    /**
     * Gets the currently logged-in user.
     *
     * @return the currently logged-in user, or null if no user is logged in.
     */
    @Override
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Logs in the user.
     *
     * @param user the user to be logged in.
     */
    @Override
    public void login(User user) {
        loggedInUser = user;
    }

    /**
     * Logs out the currently logged-in user.
     */
    @Override
    public void logout() {
        loggedInUser = null;
    }

    /**
     * Checks if a user is currently logged in.
     *
     * @return true if a user is logged in, false otherwise.
     */
    @Override
    public boolean isLoggedIn() {
        return loggedInUser != null;
    }
}
