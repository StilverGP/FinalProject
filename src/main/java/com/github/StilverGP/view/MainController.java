package com.github.StilverGP.view;

import com.github.StilverGP.model.Session;
import com.github.StilverGP.model.dao.RoomDAO;
import com.github.StilverGP.model.entity.Room;
import com.github.StilverGP.utils.Alerts;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {

    @FXML
    private TableView<Room> tableView;

    @FXML
    private TableColumn<Room, Image> imageColumn;

    @FXML
    private TableColumn<Room, Integer> numberColumn;

    @FXML
    private TableColumn<Room, String> typeColumn;

    @FXML
    private TableColumn<Room, Integer> bedsColumn;

    @FXML
    private TableColumn<Room, Double> priceColumn;

    @FXML
    private TableColumn<Room, String> availabilityColumn;

    private ObservableList<Room> rooms;

    @Override
    public void onOpen(Object input) {
        RoomDAO rDAO = new RoomDAO();
        List<Room> rooms = rDAO.findAll();
        this.rooms = FXCollections.observableArrayList(rooms);
        tableView.setItems(this.rooms);
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Session.getInstance().isAdmin()) {
            numberColumn.setEditable(true);
            priceColumn.setEditable(true);
            availabilityColumn.setEditable(true);
        }
        imageColumn.setCellValueFactory(room -> new ImageView(convertToJavaFXImage(room.getValue().getImage())).imageProperty());
        imageColumn.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();
            @Override
            protected void updateItem(Image image, boolean empty) {
                super.updateItem(image, empty);
                if (empty || image == null) {
                    imageView.setImage(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(image);
                    imageView.setFitHeight(85);
                    imageView.setFitWidth(85);
                    setGraphic(imageView);
                }
            }
        });
        numberColumn.setCellValueFactory(room -> new SimpleIntegerProperty(room.getValue().getRoomNumber()).asObject());
        numberColumn.setOnEditCommit(event -> {
            if (event.getNewValue() == event.getOldValue()) return;
            if (event.getNewValue() < 1000) {
                Room room = event.getRowValue();
                room.setRoomNumber(event.getNewValue());
                RoomDAO roomDAO = new RoomDAO();
                roomDAO.update(room);
            } else {
                Alerts.showErrorAlert("Error de cambio de numero de habitación",
                        "El numero introducido es mayor de lo permitido");
            }
        });
        typeColumn.setCellValueFactory(room -> new SimpleStringProperty(room.getValue().getRoomTypeValue(room.getValue().getRoomType())));
        bedsColumn.setCellValueFactory(room -> new SimpleIntegerProperty(room.getValue().getNumberOfBeds()).asObject());
        priceColumn.setCellValueFactory(room -> new SimpleDoubleProperty(room.getValue().getPriceNight()).asObject());
        priceColumn.setOnEditCommit(event -> {
            if (event.getNewValue() == event.getOldValue()) return;
            if (event.getNewValue() < 100.00) {
                Room room = event.getRowValue();
                room.setPriceNight(event.getNewValue());
                RoomDAO roomDAO = new RoomDAO();
                roomDAO.updatePrice(room);
            } else {
                Alerts.showErrorAlert("Error de cambio de precio de habitación",
                        "El precio introducido es mayor al precio permitido");
            }
        });
        availabilityColumn.setCellValueFactory(room -> new SimpleStringProperty(room.getValue().getAvailabilityValue(room.getValue().isAvailable())));
        availabilityColumn.setOnEditCommit(event -> {
            if (event.getNewValue() == event.getOldValue()) return;
            if (event.getNewValue().matches("ocupada") || event.getNewValue().matches("disponible")) {
                Room room = event.getRowValue();
                room.setAvailable(room.setAvailabilityValue(event.getNewValue()));
                RoomDAO roomDAO = new RoomDAO();
                roomDAO.updateAvailability(room);
            } else {
                Alerts.showErrorAlert("Error de cambio de disponibilidad",
                        "El tipo de disponibilidad es invalido, " +
                                "por favor introduce un valor correcto (disponible, ocupada)");
            }
        });
    }

    public Image convertToJavaFXImage(BufferedImage roomImage) {
        Image image = null;
        if (roomImage != null) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(roomImage, "png", baos);
                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                image = new Image(bais);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return image;
    }
  
}
