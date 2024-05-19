package com.github.StilverGP.controller;

import com.github.StilverGP.App;
import com.github.StilverGP.model.Session;
import com.github.StilverGP.model.connection.InitializeDatabase;
import com.github.StilverGP.model.dao.UserDAO;
import com.github.StilverGP.model.entity.User;
import com.github.StilverGP.view.Scenes;
import com.github.StilverGP.view.View;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController extends Controller implements Initializable {

    @FXML
    private Menu account;

    @FXML
    private BorderPane borderPane;

    private Controller centerController;

    @Override
    public void onOpen(Object input) throws IOException {
        InitializeDatabase initializeDatabase = new InitializeDatabase();
        initializeDatabase.initialize();
        changeScene(Scenes.LOGIN, null);
    }

    /**
     * Changes the current scene to the specified scene.
     *
     * @param scene the enum representing the scene to switch to.
     * @param data additional data to pass to the new scene.
     * @throws IOException if an I/O error occurs.
     */
    public void changeScene(Scenes scene, Object data) throws IOException {
        if (Session.getInstance().isLoggedIn()) account.setVisible(true);
        View view = loadFXML(scene);
        borderPane.setCenter(view.scene);
        this.centerController = view.controller;
        this.centerController.onOpen(data);
    }

    /**
     * Opens a modal window with the specified scene and title.
     *
     * @param scene the enum representing the scene to load in the modal.
     * @param title the title of the modal window.
     * @param parent the parent controller of the new scene.
     * @param data additional data to pass to the new scene.
     * @throws IOException if an I/O error occurs.
     */
    public void openModal(Scenes scene, String title, Controller parent, Object data) throws IOException {
        View view = loadFXML(scene);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.stage);
        Scene _scene = new Scene(view.scene);
        stage.setScene(_scene);
        view.controller.onOpen(parent);
        stage.showAndWait();
    }

    @Override
    public void onClose(Object output) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Opens a modal window for updating the user's username.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void updUserUsername() throws IOException {
        App.currentController.openModal(Scenes.UPDATEUSERUSERNAME, "Actualizando username...", this, null);
    }

    /**
     * Opens a modal window for updating the user's name.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void updUserName() throws IOException {
        App.currentController.openModal(Scenes.UPDATEUSERNAME, "Actualizando username...", this, null);
    }

    /**
     * Opens a modal window for updating the user's email.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void updMail() throws IOException {
        App.currentController.openModal(Scenes.UPDATEUSERUSERNAME, "Actualizando username...", this, null);
    }

    /**
     * Updates the user's username in the database.
     *
     * @param user the user object with updated username.
     */
    public void updateUserUsername(User user) {
        UserDAO userDAO = new UserDAO();
        userDAO.update(user);
    }

    /**
     * Updates the user's name in the database.
     *
     * @param user the user object with updated name.
     */
    public void updateUserName(User user) {
        UserDAO userDAO = new UserDAO();
        userDAO.updateName(user);
    }

    /**
     * Updates the user's email in the database.
     *
     * @param user the user object with updated email.
     */
    public void updateUserMail(User user) {
        UserDAO userDAO = new UserDAO();
        userDAO.updateMail(user);
    }

    /**
     * Logs off the current user and changes the scene to the login screen.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void logOff() throws IOException {
        Session.getInstance().logout();
        changeScene(Scenes.LOGIN, null);
    }

    /**
     * Loads an FXML file and returns a View object containing the scene and controller.
     *
     * @param scenes the enum representing the scene to load.
     * @return the loaded View object.
     * @throws IOException if an I/O error occurs.
     */
    public static View loadFXML(Scenes scenes) throws IOException {
        String url = scenes.getURL();
        FXMLLoader loader = new FXMLLoader(App.class.getResource(url));
        Parent p = loader.load();
        Controller c = loader.getController();
        View view = new View();
        view.scene = p;
        view.controller = c;
        return view;
    }
}
