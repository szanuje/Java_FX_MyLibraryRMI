package mylibrary.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserRegisteredController implements Initializable {

    private MainMenuController mainMenu;

    @FXML
    private Button okButton;

    public UserRegisteredController() {
    }

    public void closeWindow(ActionEvent e) {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setMainMenu(MainMenuController ctrl) {
        mainMenu = ctrl;
    }
}
