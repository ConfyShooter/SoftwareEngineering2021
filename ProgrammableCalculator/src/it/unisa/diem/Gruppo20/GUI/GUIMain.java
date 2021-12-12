package it.unisa.diem.Gruppo20.GUI;

import java.io.IOException;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.fxml.*;

/**
 * Main class of the calculator's GUI.
 *
 * @author Team 20
 */
public class GUIMain extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GUI_FXML.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest((WindowEvent event) -> {
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
