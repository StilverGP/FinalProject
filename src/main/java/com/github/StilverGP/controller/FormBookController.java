package com.github.StilverGP.controller;

import com.github.StilverGP.model.Session;
import com.github.StilverGP.model.dao.RoomDAO;
import com.github.StilverGP.model.entity.Book;
import com.github.StilverGP.model.entity.Room;
import com.github.StilverGP.utils.Alerts;
import javafx.beans.value.ChangeListener;

import java.time.temporal.ChronoUnit;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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

    @FXML
    private Label totalPrice;

    private MainController controller;

    @Override
    public void onOpen(Object input) throws IOException {
        this.controller = (MainController) input;
    }

    /**
     * Adds a new book if the room is available and the dates are valid.
     *
     * @param event the event that triggered the method.
     */
    public void addBook(Event event) {
        RoomDAO roomDAO = new RoomDAO();
        Room room = roomDAO.findById(Integer.valueOf(roomNumber.getText()));
        if (room != null) {
            if (room.isAvailable()) {
                if (checkOutDate.getValue().isAfter(checkInDate.getValue())) {
                    Book book = new Book(generateBookCode(), checkInDate.getValue(), checkOutDate.getValue(), Session.getInstance().getLoggedInUser(), room);
                    room.setAvailable(false);
                    roomDAO.updateAvailability(room);
                    this.controller.saveBook(book);
                    ((Node) (event.getSource())).getScene().getWindow().hide();
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

    /**
     * Generates a code for a book.
     *
     * @return a code for the book.
     */
    public String generateBookCode() {
        int cod_bookLength = 5;
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cod_bookLength; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    @Override
    public void onClose(Object output) {

    }

    private void calculateTotalPrice() {
        if (checkInDate.getValue() != null && checkOutDate.getValue() != null && !roomNumber.getText().isEmpty()) {
            RoomDAO roomDAO = new RoomDAO();
            Room room = roomDAO.findById(Integer.valueOf(roomNumber.getText()));
            if (room != null) {
                long daysBetween = ChronoUnit.DAYS.between(checkInDate.getValue(), checkOutDate.getValue());
                if (daysBetween > 0) {
                    double price = daysBetween * room.getPriceNight();
                    totalPrice.setText(price + " €");
                } else if (daysBetween == 0) {
                    totalPrice.setText(room.getPriceNight() + " €");
                } else {
                    totalPrice.setText("0 €");
                }
            } else {
                totalPrice.setText("0 €");
            }
        } else {
            totalPrice.setText("0 €");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ChangeListener<Object> changeListener = (observable, oldValue, newValue) -> calculateTotalPrice();
        checkInDate.valueProperty().addListener(changeListener);
        checkOutDate.valueProperty().addListener(changeListener);
        roomNumber.textProperty().addListener(changeListener);
    }
}
