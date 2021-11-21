package it.unisa.diem.SE2021.GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
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
    private Button calculateBtn;
    @FXML
    private ListView<?> listView;
    @FXML
    private Button cancBtn;
    @FXML
    private TextField inputText;
    
    private ObservableList<?> stack;

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
    private void onCalculatePressed(ActionEvent event) {
    }

    @FXML
    private void onPlusPressed(ActionEvent event) {
    }

    @FXML
    private void onMinusPressed(ActionEvent event) {
    }

    @FXML
    private void onMulPressed(ActionEvent event) {
    }

    @FXML
    private void onDivPressed(ActionEvent event) {
    }

    @FXML
    private void onInvertPressed(ActionEvent event) {
    }

    @FXML
    private void onSqrtPressed(ActionEvent event) {
    }

    @FXML
    private void onCPressed(ActionEvent event) {
    }

}
