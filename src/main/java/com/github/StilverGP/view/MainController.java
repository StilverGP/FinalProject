package com.github.StilverGP.view;

import com.github.StilverGP.model.dao.RoomDAO;
import com.github.StilverGP.model.entity.Room;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;

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
    public void onOpen(Object input) throws IOException {
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
        numberColumn.setCellValueFactory(room -> new SimpleIntegerProperty(room.getValue().getRoomNumber()).asObject());
        typeColumn.setCellValueFactory(room -> new SimpleStringProperty(room.getValue().getRoomTypeValue(room.getValue().getRoomType())));
        bedsColumn.setCellValueFactory(room -> new SimpleIntegerProperty(room.getValue().getNumberOfBeds()).asObject());
        priceColumn.setCellValueFactory(room -> new SimpleDoubleProperty(room.getValue().getPriceNight()).asObject());
        availabilityColumn.setCellValueFactory(room -> new SimpleStringProperty(room.getValue().getAvailabilityValue(room.getValue().isAvailable())));
    }
}
