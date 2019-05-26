package MyLibrary;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    private Button loginTabSignIn;
    @FXML
    private Button registerTabSignUp;
    @FXML
    private PasswordField loginTabPassword;
    @FXML
    private PasswordField registerTabPassword;
    @FXML
    private TextField loginTabEmail;
    @FXML
    private TextField registerTabName;
    @FXML
    private TextField registerTabSurname;
    @FXML
    private TextField registerTabEmail;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab loginTab;
    @FXML
    private Label badLoginLabel;
    @FXML
    private Label badRegisterLabel;

    private static RMIInterface look_up;

    public MainMenuController() throws MalformedURLException, RemoteException, NotBoundException {
        look_up = (RMIInterface) Naming.lookup("//localhost/MyServer");
    }

    public void loginPerson(ActionEvent e) throws NotBoundException{

        badLoginLabel.setText("");

        try {
            Person p = look_up.accessUser(loginTabEmail.getText(), loginTabPassword.getText());

            if (p == null) {
                System.err.println("MainMenuController.java: loginPerson() - BAD LOGIN");
                badLoginLabel.setText("Wrong username or password");
            } else {
                System.out.println("User logged in! " + loginTabEmail.getText() + " : " + loginTabPassword.getText());
                loginTabEmail.clear();
                loginTabPassword.clear();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("userMenu.fxml"));
                SplitPane newWindow = (SplitPane) loader.load();

                UserMenuController controller = loader.getController();
                controller.setMainMenu(this);
                controller.setMyUser(p);
                controller.setLibrary(look_up.accessLibrary());
                controller.showMyBooks();
                controller.showLibraryBooks();

                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(registerTabSignUp.getScene().getWindow());
                stage.setTitle("MyLibrary");
                Scene scene = new Scene(newWindow);
                stage.setScene(scene);
                stage.show();

                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        try {
                            controller.updateDatabaseAndClose();

                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        } catch (RemoteException rem) {
            rem.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void registerPerson(ActionEvent e) {

        badRegisterLabel.setText("");

        try {

            String name = registerTabName.getText();
            String surname = registerTabSurname.getText();
            String email = registerTabEmail.getText();
            String password = registerTabPassword.getText();

            if (look_up.accessUser(email, password) != null) {
                badRegisterLabel.setText("User already exists");
            } else {
                look_up.saveUser(new Person(name, surname, email, password));

                System.out.println("User registered! " + name + " " + surname + " " +
                        email + " " + password);

                registerTabName.clear();
                registerTabSurname.clear();
                registerTabEmail.clear();
                registerTabPassword.clear();
                setTabInPanel(0);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("userRegistered.fxml"));
                AnchorPane newWindow = (AnchorPane) loader.load();
                UserRegisteredController controller = loader.getController();
                controller.setMainMenu(this);
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(registerTabSignUp.getScene().getWindow());
                stage.setTitle("");
                Scene scene = new Scene(newWindow);
                stage.setScene(scene);
                stage.show();
            }

        } catch (RemoteException rem) {
            rem.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void setTabInPanel(int idx) {
        tabPane.getSelectionModel().select(idx);
        badLoginLabel.setText("");
        badRegisterLabel.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
