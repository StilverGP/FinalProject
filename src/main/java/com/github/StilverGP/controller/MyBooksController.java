package com.github.StilverGP.controller;

import com.github.StilverGP.App;
import com.github.StilverGP.model.Session;
import com.github.StilverGP.model.dao.BookDAO;
import com.github.StilverGP.model.entity.Book;
import com.github.StilverGP.utils.Alerts;
import com.github.StilverGP.view.Scenes;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class MyBooksController extends Controller implements Initializable {

    @FXML
    private TableView<Book> tableView;

    @FXML
    private TableColumn<Book, String> codBook;

    @FXML
    private TableColumn<Book, LocalDate> checkInDate;

    @FXML
    private TableColumn<Book, LocalDate> checkOutDate;

    @FXML
    private TableColumn<Book, Integer> roomNumber;

    private ObservableList<Book> books;

    private MainController controller;

    @Override
    public void onOpen(Object input) {
        this.controller = (MainController) input;
        reloadBooksFromDatabase();
    }

    /**
     * Reloads the list of books from the database for the logged-in user
     * and updates the tableView.
     */
    public void reloadBooksFromDatabase() {
        BookDAO bookDAO = new BookDAO();
        List<Book> books = bookDAO.findByUser(Session.getInstance().getLoggedInUser());
        this.books = FXCollections.observableArrayList(books);
        tableView.setItems(this.books);
    }

    /**
     * Opens the form modal window to delete a booked room.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void delBook() throws IOException {
        App.currentController.openModal(Scenes.FORMDELETEBOOK, "Cancelando reserva...",this, null);
    }


    /**
     * Deletes the specified book from the database and reloads the list of books for the logged-in user.
     *
     * @param book the booked room to be deleted.
     */
    public void deleteBook(Book book) {
        BookDAO bookDAO = new BookDAO();
        bookDAO.delete(book);
        reloadBooksFromDatabase();
        this.controller.reloadRoomsFromDataBase();
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.setEditable(true);

        codBook.setCellValueFactory(book -> new SimpleStringProperty(book.getValue().getCod_book()));
        codBook.setOnEditCommit(event -> {
            if (event.getNewValue() == event.getOldValue() || event.getNewValue().isEmpty()) return;
            if (!event.getNewValue().isEmpty()) {
                Book book = event.getRowValue();
                book.setCod_book(event.getNewValue());
                BookDAO bookDAO = new BookDAO();
                bookDAO.update(book);
                reloadBooksFromDatabase();
            } else {
                Alerts.showErrorAlert("Error de cambio de código de reserva",
                        "El valor introducido no puede estar vacío");
            }
        });
        checkInDate.setCellValueFactory(new PropertyValueFactory<>("checkIn_date"));
        checkInDate.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getDayOfMonth() + "/" + item.getMonthValue() + "/" + item.getYear());
                }
            }
        });
        checkInDate.setOnEditCommit(event -> {
            if (event.getNewValue() == event.getOldValue()) return;
            if (event.getNewValue().isAfter(LocalDate.now())) {
                Book book = event.getRowValue();
                book.setCheckIn_date(event.getNewValue());
                BookDAO bookDAO = new BookDAO();
                bookDAO.updateCheckIn(book);
                reloadBooksFromDatabase();
            } else {
                Alerts.showErrorAlert("Error de cambio de fecha de entrada",
                        "La fecha de entrada no puede ser anterior a la actual");
            }
        });
        checkOutDate.setCellValueFactory(new PropertyValueFactory<>("checkOut_date"));
        checkOutDate.setCellFactory(book -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getDayOfMonth() + "/" + item.getMonthValue() + "/" + item.getYear());
                }
            }
        });
        checkOutDate.setOnEditCommit(event -> {
            if (event.getNewValue() == event.getOldValue()) return;
            if (event.getNewValue().isAfter(checkInDate.getCellData(event.getRowValue()))) {
                Book book = event.getRowValue();
                book.setCheckOut_date(event.getNewValue());
                BookDAO bookDAO = new BookDAO();
                bookDAO.updateCheckOut(book);
                reloadBooksFromDatabase();
            } else {
                Alerts.showErrorAlert("Error de cambio de fecha de salida",
                        "La fecha de salida no puede ser anterior a la de entrada");
            }
        });
        roomNumber.setCellValueFactory(book -> new SimpleIntegerProperty(book.getValue().getRoom().getRoomNumber()).asObject());
    }
}
