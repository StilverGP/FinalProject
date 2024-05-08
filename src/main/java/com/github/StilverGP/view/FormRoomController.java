package com.github.StilverGP.view;

import com.github.StilverGP.model.dao.RoomDAO;
import com.github.StilverGP.model.entity.Room;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FormRoomController extends Controller implements Initializable {

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

    private AppController controller;

    @Override
    public void onOpen(Object input) {
        this.controller = (AppController) input;
    }

    public void addRoom(Event event) throws IOException {
        RoomDAO roomDAO = new RoomDAO();
        Room roomExists = roomDAO.findById(Integer.valueOf(roomNumber.getText()));
        if (roomExists == null) {
            Room room = new Room();
            room.setImage(imageSelector(event));
            room.setRoomNumber(Integer.valueOf(roomNumber.getText()));
            room.setRoomType(room.setRoomTypeValue(roomType.getText()));
            room.setNumberOfBeds(Integer.valueOf(roomBeds.getText()));
            room.setPriceNight(Double.parseDouble(roomPrice.getText()));
            room.setAvailable(roomAvailable.isSelected());
            this.controller.saveRoom(room);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error al crear la habitación");
            alert.setContentText("La habitación ya existe");
            alert.show();
        }
    }

    public Image fileToImage(File file) throws IOException {
        return ImageIO.read(file);
    }

    public void addImage(Event event) throws IOException {
        imageSelector(event);
    }

    public Image imageSelector(Event event) throws IOException {
        Window window = ((Node)(event.getSource())).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imagen");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Imágen", "*.jpg", "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(window);
        return fileToImage(file);
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
