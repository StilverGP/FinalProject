package com.github.StilverGP.controller;

import com.github.StilverGP.model.Session;
import com.github.StilverGP.model.dao.UserDAO;
import com.github.StilverGP.model.entity.User;
import com.github.StilverGP.utils.Alerts;
import com.github.StilverGP.utils.Security;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserNameController extends Controller implements Initializable {
    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    private AppController controller;

    @Override
    public void onOpen(Object input) throws IOException {
        this.controller = (AppController) input;
    }

    /**
     * Updates the user's name if the user confirms the action and provides the correct password.
     *
     * @param event the event that triggered the method.
     */
    public void updateUser(Event event) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.findById(Session.getInstance().getLoggedInUser().getUsername());
        Alerts.showConfirmationAlert("Actualización de nombre",
                "Esta a punto de actualizar su nombre, " +
                        "¿Está totalmente seguro de esta acción?").showAndWait().ifPresent(response -> {
            if (!Session.getInstance().getLoggedInUser().getName().equals(name.getText())) {
                if (Session.getInstance().getLoggedInUser().isMyPassword(Security.hashPassword(password.getText()))) {
                    if (response == ButtonType.OK) {
                        user.setUsername(name.getText());
                        saveAndCloseWindow(user, event);
                    }
                } else {
                    Alerts.showErrorAlert("Error de contraseña", "Contraseña incorrecta");
                }
            } else {
                Alerts.showErrorAlert("Error de actualización", "No puedes cambiar el nombre por el mismo");
            }
        });
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Saves the updated user's name and closes the window.
     *
     * @param user  the user object containing the updated name.
     * @param event the event that triggered the method.
     */
    public void saveAndCloseWindow(User user, Event event) {
        this.controller.updateUserName(user);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
