import View.ControllerWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class App extends Application{
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/Home.fxml")); // Hacer Vista
        ControllerWindow.getInstance().showWindow(primaryStage, root, 764, 591, "Pseudo-Parser");
    }
}
