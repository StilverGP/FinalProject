package com.github.StilverGP.view;

import com.github.StilverGP.model.dao.RoomDAO;
import com.github.StilverGP.model.entity.Room;
import com.github.StilverGP.utils.Alerts;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FormRoomController extends Controller implements Initializable {
    @FXML
    private ImageView imageView;

    @FXML
    private TextField roomNumber;

    @FXML
    private TextField roomType;

    @FXML
    private TextField roomBeds;

    @FXML
    private TextField roomPrice;

    @FXML
    private CheckBox roomAvailable;

    private BufferedImage roomImage;

    private AppController controller;

    @Override
    public void onOpen(Object input) {
        this.roomImage = null;
        this.controller = (AppController) input;
    }

    public void addRoom(Event event) {
        RoomDAO roomDAO = new RoomDAO();
        Room roomExists = roomDAO.findById(Integer.valueOf(roomNumber.getText()));
        if (roomExists == null) {
            Room room = new Room();
            room.setImage(roomImage);
            room.setRoomNumber(Integer.valueOf(roomNumber.getText()));
            room.setRoomType(room.setRoomTypeValue(roomType.getText()));
            room.setNumberOfBeds(Integer.valueOf(roomBeds.getText()));
            room.setPriceNight(Double.parseDouble(roomPrice.getText()));
            room.setAvailable(roomAvailable.isSelected());
            saveAndCloseWindow(room, event);
        } else {
            Alerts.showErrorAlert("Error al crear la habitación", "La habitación ya existe");
        }
    }

    public void getImage(Event event) throws IOException {
        Window window = ((Node)(event.getSource())).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imagen...");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Imágen", "*.jpg", "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(window);
        Image image = new Image(file.toURI().toString());
        roomImage = ImageIO.read(file);
        imageView.setImage(image);
        imageView.setFitHeight(85);
        imageView.setFitWidth(85);
    }

    public void saveAndCloseWindow(Room room, Event event) {
        this.controller.saveRoom(room);
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
