package com.github.StilverGP.controller;

import com.github.StilverGP.model.dao.RoomDAO;
import com.github.StilverGP.model.entity.Room;
import com.github.StilverGP.utils.Alerts;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FormDelRoomController extends Controller implements Initializable {
    @FXML
    private TextField roomNumber;

    private MainController controller;


    @Override
    public void onOpen(Object input) {
        this.controller = (MainController) input;
    }

    /**
     * Deletes a room if it exists and the action is confirmed by the user.
     *
     * @param event the event that triggered the method.
     */
    public void deleteRoom(Event event) {
        RoomDAO roomDAO = new RoomDAO();
        Room roomExists = roomDAO.findById(Integer.valueOf(roomNumber.getText()));
        if (roomExists != null) {
            Alerts.showConfirmationAlert("Eliminación de habitación",
                    "Esta a punto de borrar esta habitación, " +
                            "¿Está totalmente seguro de esta acción?").showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    deleteAndCloseWindow(roomExists, event);
                }
            });
        } else {
            Alerts.showErrorAlert("Error al borrar la habitación", "No existe ninguna habitación con ese numero");
        }
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Deletes the room and closes the window.
     *
     * @param room the room to be deleted.
     * @param event the event that triggered the deletion.
     */
    public void deleteAndCloseWindow(Room room, Event event) {
        this.controller.deleteRoom(room);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
