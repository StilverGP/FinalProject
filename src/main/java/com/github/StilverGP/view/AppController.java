package com.github.StilverGP.view;

import com.github.StilverGP.App;
import com.github.StilverGP.model.Session;
import com.github.StilverGP.model.dao.RoomDAO;
import com.github.StilverGP.model.dao.UserDAO;
import com.github.StilverGP.model.entity.Room;
import com.github.StilverGP.model.entity.User;
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
    private Menu admin;

    @FXML
    private BorderPane borderPane;

    private Controller centerController;

    @Override
    public void onOpen(Object input) throws IOException {
        changeScene(Scenes.MAIN, null);
    }

    public void changeScene(Scenes scene, Object data) throws IOException {
        View view = loadFXML(scene);
        borderPane.setCenter(view.scene);
        this.centerController = view.controller;
        this.centerController.onOpen(data);
        if (LoginController.checkUserIsAdmin()) userIsAdmin();
    }

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

    public void addRoom() throws IOException {
        App.currentController.openModal(Scenes.FORMROOM, "Agregando habitación...", this, null);
    }

    public void removeRoom() throws IOException {
        App.currentController.openModal(Scenes.FORMDELETEROOM, "Eliminando habitación...", this, null);
    }

    public void updUserUsername() throws IOException {
        App.currentController.openModal(Scenes.UPDATEUSERUSERNAME, "Actualizando username...", this, null);
    }

    public void updUserName() throws IOException {
        App.currentController.openModal(Scenes.UPDATEUSERNAME, "Actualizando username...", this, null);
    }

    public void updMail() throws IOException {
        App.currentController.openModal(Scenes.UPDATEUSERUSERNAME, "Actualizando username...", this, null);
    }

    public void saveRoom(Room room) {
        RoomDAO roomDAO = new RoomDAO();
        roomDAO.add(room);
    }

    public void deleteRoom(Room room) {
        RoomDAO roomDAO = new RoomDAO();
        roomDAO.delete(room);
    }

    public void updateUserUsername(User user) {
        UserDAO userDAO = new UserDAO();
        userDAO.update(user);
    }

    public void updateUserName(User user) {
        UserDAO userDAO = new UserDAO();
        userDAO.updateName(user);
    }

    public void updateUserMail(User user) {
        UserDAO userDAO = new UserDAO();
        userDAO.updateMail(user);
    }

    public void userIsAdmin() {
        admin.setVisible(true);
    }

    public void logOff() throws IOException {
        Session.getInstance().logout();
        changeScene(Scenes.LOGIN, null);
    }

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
