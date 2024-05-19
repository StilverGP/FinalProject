package com.github.StilverGP.model;

import com.github.StilverGP.model.entity.User;

public interface SessionInterface {
    User getLoggedInUser();

    void login(User user);

    void logout();

    boolean isLoggedIn();

}
