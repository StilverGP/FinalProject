package com.github.StilverGP.view;

import com.github.StilverGP.App;
import com.github.StilverGP.model.Session;
import com.github.StilverGP.model.dao.BookDAO;
import com.github.StilverGP.model.entity.Book;
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

    @Override
    public void onOpen(Object input) throws IOException {
        BookDAO bookDAO = new BookDAO();
        List<Book> books = bookDAO.findByUser(Session.getInstance().getLoggedInUser());
        this.books = FXCollections.observableArrayList(books);
        tableView.setItems(this.books);
    }

    public void delBook() throws IOException {
        App.currentController.openModal(Scenes.FORMDELETEBOOK, "Cancelando reserva...", this, null);
    }

    public void deleteBook(Book book) {
        BookDAO bookDAO = new BookDAO();
        bookDAO.delete(book);
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        codBook.setCellValueFactory(book -> new SimpleStringProperty(book.getValue().getCod_book()));
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
        roomNumber.setCellValueFactory(book -> new SimpleIntegerProperty(book.getValue().getRoom().getRoomNumber()).asObject());
    }
}
