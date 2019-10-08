package mylibrary.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import mylibrary.Book;
import mylibrary.Library;
import mylibrary.Person;
import mylibrary.launchers.RMIInterface;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserMenuController implements Initializable {

    private MainMenuController mainMenu;
    private Person myUser;
    private Library library;
    private static RMIInterface look_up;

    private ObservableList<Book> userBooksObservableList = FXCollections.observableArrayList();
    private ObservableList<Book> libraryBooksObservableList = FXCollections.observableArrayList();

    @FXML
    private ListView<Book> userBooksListView;

    @FXML
    private ListView<Book> libraryBooksListView;

    public UserMenuController() throws Exception {

    }

    public void showMyBooks() {
//        Platform.runLater(() -> {
        userBooksListView.setItems(userBooksObservableList);
        userBooksObservableList.addAll(myUser.getPersonBooks());
        userBooksListView.setCellFactory(param -> new ListCell<Book>() {
            @Override
            protected void updateItem(Book item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getBookTitle() == null) {
                    setText(null);
                } else {
                    setText("\"" + item.getBookTitle() + "\"");
                }
            }
        });
//        });
    }

    public void showLibraryBooks() throws MalformedURLException, RemoteException, NotBoundException {

        libraryBooksListView.setItems(libraryBooksObservableList);
        libraryBooksObservableList.addAll(look_up.getAvailableBooks());
        libraryBooksListView.setCellFactory(param -> new ListCell<Book>() {
            @Override
            protected void updateItem(Book item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getBookTitle() == null) {
                    setText(null);
                } else {
                    setText("\"" + item.getBookTitle() + "\"");
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//
//        try {
//            System.out.println("UserMenuController.java: initialize() called");
//            showMyBooks();
//            showLibraryBooks();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void returnBook() throws RemoteException {

        Book selectedItem = userBooksListView.getSelectionModel().getSelectedItem();
        myUser.returnBook(selectedItem, library);
        List<Book> movingItems = new ArrayList<>(userBooksListView.getSelectionModel().getSelectedItems());
        userBooksObservableList.removeAll(movingItems);
        libraryBooksObservableList.addAll(movingItems);

        userBooksListView.getSelectionModel().clearSelection();
    }

    public void borrowBook() throws RemoteException {

        Book selectedItem = libraryBooksListView.getSelectionModel().getSelectedItem();
        myUser.borrowBook(selectedItem, library);
        List<Book> movingItems = new ArrayList<>(libraryBooksListView.getSelectionModel().getSelectedItems());
        libraryBooksObservableList.removeAll(movingItems);
        userBooksObservableList.addAll(movingItems);

        libraryBooksListView.getSelectionModel().clearSelection();
    }

    public void setMainMenu(MainMenuController ctrl) {
        mainMenu = ctrl;
    }

    public void setMyUser(Person p) {
        myUser = p;
    }

    public void setLibrary(Library l) {
        library = l;
    }

    public void setLook_up(RMIInterface parent_look_up) {
        look_up = parent_look_up;
    }

    public void updateDatabaseAndClose() throws RemoteException {
        look_up.updateDatabase(myUser, library);
    }
}
