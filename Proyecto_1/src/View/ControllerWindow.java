package View;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControllerWindow {
    private static final ControllerWindow instance = new ControllerWindow();
    private Stage window;

    public static ControllerWindow getInstance() {
        return instance;
    }

    public void showWindow(Stage window, Parent root, int width, int high, String title) {
        this.window = window;

        window.setScene(new Scene(root, width, high));
        window.setTitle(title);
        window.centerOnScreen();
        window.show();
    }

    public Stage getWindow() {
        return window;
    }
}
