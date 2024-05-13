package com.github.StilverGP.view;

import com.github.StilverGP.model.Session;
import com.github.StilverGP.model.dao.RoomDAO;
import com.github.StilverGP.model.entity.Book;
import com.github.StilverGP.model.entity.Room;
import com.github.StilverGP.utils.Alerts;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class FormBookController extends Controller implements Initializable {

    @FXML
    private DatePicker checkInDate;

    @FXML
    private DatePicker checkOutDate;

    @FXML
    private TextField roomNumber;

    private MainController controller;
    @Override
    public void onOpen(Object input) throws IOException {
        this.controller = (MainController) input;
    }

    public void addBook(Event event) {
        RoomDAO roomDAO = new RoomDAO();
        Room room = roomDAO.findById(Integer.valueOf(roomNumber.getText()));
        if (room != null) {
            if (room.isAvailable()) {
                if (checkOutDate.getValue().isAfter(checkInDate.getValue())) {
                    Book book = new Book(generateBookCode(), checkInDate.getValue(), checkOutDate.getValue(), Session.getInstance().getLoggedInUser(), room);
                    this.controller.saveBook(book);
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                } else {
                    Alerts.showErrorAlert("Error al crear la reserva", "La fecha de salida no puede ser anterior a la fecha de entrada");
                }
            } else {
                Alerts.showErrorAlert("Error al crear la reserva", "La habitación introducida no está disponible");
            }
        } else {
            Alerts.showErrorAlert("Error al crear la reserva", "No hay ninguna habitación con este numero de habitación");
        }
    }

    public String generateBookCode() {
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
