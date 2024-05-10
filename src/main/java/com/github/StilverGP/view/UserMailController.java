package com.github.StilverGP.view;

import com.github.StilverGP.model.Session;
import com.github.StilverGP.model.dao.UserDAO;
import com.github.StilverGP.model.entity.User;
import com.github.StilverGP.utils.Alerts;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserMailController extends Controller implements Initializable {
    @FXML
    private TextField mail;

    @FXML
    private PasswordField password;

    private AppController controller;

    @Override
    public void onOpen(Object input) throws IOException {
        this.controller = (AppController) input;
    }

    public void updateUser(Event event) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.findById(Session.getInstance().getLoggedInUser().getUsername());
        Alerts.showConfirmationAlert("Actualización de mail",
                "Esta a punto de actualizar su mail, " +
                        "¿Está totalmente seguro de esta acción?").showAndWait().ifPresent(response -> {
            if (!Objects.equals(Session.getInstance().getLoggedInUser().getMail(), mail.getText())) {
                if (Session.getInstance().getLoggedInUser().isMyPassword(password.getText())) {
                    if (response == ButtonType.OK) {
                        user.setUsername(mail.getText());
                        saveAndCloseWindow(user, event);
                    }
                } else {
                    Alerts.showErrorAlert("Error de contraseña", "Contraseña incorrecta");
                }
            } else {
                Alerts.showErrorAlert("Error de actualización", "No puedes cambiar el el mail por el mismo");
            }
        });
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void saveAndCloseWindow(User user, Event event) {
        this.controller.updateUserMail(user);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
