package mylibrary.launchers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientOperation extends Application {

    public static RMIInterface look_up;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1090);
        look_up = (RMIInterface) registry.lookup("//localhost/MyServer");

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainMenu.fxml"));
        primaryStage.setTitle("MyLibrary");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
