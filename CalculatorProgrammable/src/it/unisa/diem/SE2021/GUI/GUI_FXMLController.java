package it.unisa.diem.SE2021.GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Gruppo 20
 */
public class GUI_FXMLController implements Initializable {

    @FXML
    private TextField textField;
    @FXML
    private Button calculateBtn;
    @FXML
    private ListView<?> listView;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        calculateBtn.setDisable(true);
        listView.setEditable(false);
    }

    @FXML
    private void CalculateBtnOnAction(ActionEvent event) {
    }

}
