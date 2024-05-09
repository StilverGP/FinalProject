package com.github.StilverGP.view;

import com.github.StilverGP.App;
import com.github.StilverGP.model.Session;
import com.github.StilverGP.model.dao.UserDAO;
import com.github.StilverGP.model.entity.User;
import com.github.StilverGP.utils.Alerts;
import com.github.StilverGP.utils.Security;
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

    public void login(User user) {
        Session.getInstance().login(user);
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

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

    public static boolean checkUserIsAdmin() {
        return Session.getInstance().getLoggedInUser().isAdmin();
    }

    public void signIn() throws IOException {
        App.currentController.openModal(Scenes.FORMSIGNIN, "Agregando usuario...",this, null);
    }

    public void saveUser(User user) {
        UserDAO userDAO = new UserDAO();
        userDAO.add(user);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
