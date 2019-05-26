package MyLibrary;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class Main extends Application {

    private static RMIInterface look_up;

    @Override
    public void start(Stage primaryStage) throws Exception {
        look_up = (RMIInterface) Naming.lookup("//localhost/MyServer");

        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        primaryStage.setTitle("MyLibrary");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    look_up.serverStopAndSerialize();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
