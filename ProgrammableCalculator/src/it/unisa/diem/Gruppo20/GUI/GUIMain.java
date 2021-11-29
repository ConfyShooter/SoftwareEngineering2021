package it.unisa.diem.Gruppo20.GUI;

import java.io.IOException;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.fxml.*;

/**
 *
 * @author Gruppo 20
 */
public class GUIMain extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GUI_FXML.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}