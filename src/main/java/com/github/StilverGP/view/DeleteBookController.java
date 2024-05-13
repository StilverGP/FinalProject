package com.github.StilverGP.view;

import com.github.StilverGP.model.Session;
import com.github.StilverGP.model.dao.BookDAO;
import com.github.StilverGP.model.entity.Book;
import com.github.StilverGP.utils.Alerts;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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

    public void deleteBook(Event event) {
        if (Session.getInstance().getLoggedInUser().isMyPassword(password.getText())) {
            BookDAO bookDAO = new BookDAO();
            Book book = bookDAO.findById(codBook.getText());
            List<Book> UserBooks = bookDAO.findByUser(Session.getInstance().getLoggedInUser());
            for (Book userBook : UserBooks) {
                if (userBook == book){
                    Alerts.showConfirmationAlert("Cancelación de reserva",
                            "Esta a punto de cancelar la reserva, " +
                                    "¿Está totalmente seguro de esta acción?").showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) deleteAndCloseWindow(book, event);
                    });
                } else {
                    Alerts.showErrorAlert("Error de cancelación de reserva","No tienes ninguna reserva que contenga este código");
                }
            }
        } else {
            Alerts.showErrorAlert("Error de cancelación de reserva", "Contraseña incorrecta");
        }
    }

    public void deleteAndCloseWindow(Book book, Event event) {
        this.controller.deleteBook(book);
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
