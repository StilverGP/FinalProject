package com.github.StilverGP.controller;

import com.github.StilverGP.model.Session;
import com.github.StilverGP.model.dao.BookDAO;
import com.github.StilverGP.model.dao.RoomDAO;
import com.github.StilverGP.model.entity.Book;
import com.github.StilverGP.model.entity.Room;
import com.github.StilverGP.utils.Alerts;
import com.github.StilverGP.utils.Security;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeleteBookController extends Controller implements Initializable {

    @FXML
    private TextField codBook;

    @FXML
    private PasswordField password;

    private MyBooksController controller;

    @Override
    public void onOpen(Object input) throws IOException {
        this.controller = (MyBooksController) input;
    }

    /**
     * Deletes a book if the user's password is correct and the action is confirmed.
     *
     * @param event the event that triggered the method.
     */
    public void deleteBook(Event event) {
        if (Session.getInstance().getLoggedInUser().isMyPassword(Security.hashPassword(password.getText()))) {
            BookDAO bookDAO = new BookDAO();
            Book book = bookDAO.findById(codBook.getText());
            RoomDAO roomDAO = new RoomDAO();
            Room room = book.getRoom();
            room.setAvailable(true);
            roomDAO.updateAvailability(room);
            Alerts.showConfirmationAlert("Cancelación de reserva",
                    "Esta a punto de cancelar la reserva, " +
                            "¿Está totalmente seguro de esta acción?").showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) deleteAndCloseWindow(book, event);
            });
        } else {
            Alerts.showErrorAlert("Error de cancelación de reserva", "Contraseña incorrecta");
        }
    }

    /**
     * Deletes the book and closes the window.
     *
     * @param book the book (reservation) to be deleted.
     * @param event the event that triggered the deletion.
     */
    public void deleteAndCloseWindow(Book book, Event event) {
        this.controller.deleteBook(book);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
