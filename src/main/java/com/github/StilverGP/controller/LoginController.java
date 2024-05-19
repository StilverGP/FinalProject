package com.github.StilverGP.controller;

import com.github.StilverGP.App;
import com.github.StilverGP.model.Session;
import com.github.StilverGP.model.dao.UserDAO;
import com.github.StilverGP.model.entity.User;
import com.github.StilverGP.utils.Alerts;
import com.github.StilverGP.utils.Security;
import com.github.StilverGP.view.Scenes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    /**
     * Logs in the user using the session instance.
     *
     * @param user the user to be logged in.
     */
    public void login(User user) {
        Session.getInstance().login(user);
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    /**
     * Navigates to the main scene if the user credentials are valid.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void goToMain() throws IOException {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.findById(username.getText());
        if (user != null && user.isMyPassword(Security.hashPassword(password.getText()))) {
            login(user);
            App.currentController.changeScene(Scenes.MAIN, null);
        } else {
            Alerts.showErrorAlert("Error de inicio de sesión",
                    "Usuario o contraseña incorrectos, por favor intente nuevamente");
        }
    }

    /**
     * Checks if the logged in user is an admin.
     *
     * @return true if the logged in user is an admin, false otherwise.
     */
    public static boolean checkUserIsAdmin() {
        return Session.getInstance().getLoggedInUser().isAdmin();
    }

    /**
     * Opens the sign in form to add a new user.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void signIn() throws IOException {
        App.currentController.openModal(Scenes.FORMSIGNIN, "Agregando usuario...", this, null);
    }

    /**
     * Saves a new user and adds it to the database.
     *
     * @param user the user to be saved.
     */
    public void saveUser(User user) {
        UserDAO userDAO = new UserDAO();
        userDAO.add(user);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
