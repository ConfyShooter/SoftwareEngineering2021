package it.unisa.diem.Gruppo20.GUI;

import it.unisa.diem.Gruppo20.Model.Calculator;
import it.unisa.diem.Gruppo20.Model.Complex;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
    private ListView<Complex> historyList;
    @FXML
    private Button cancBtn;
    @FXML
    private TextField inputText;
    
    private Calculator c;
    private ObservableList<Complex> stack;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        calculateBtn.disableProperty().bind(inputText.textProperty().isEmpty());
        cancBtn.disableProperty().bind(inputText.textProperty().isEmpty());
        c = new Calculator();
        stack = FXCollections.observableArrayList();
        stack.setAll(c.getData());
        historyList.setItems(stack);
    }

    @FXML
    private void onCalculatePressed(ActionEvent event) {
        try{
            c.parsing(inputText.getText());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        inputText.setText("");
        stack.setAll(c.getData());
    }

    @FXML
    private void onPlusPressed(ActionEvent event) {
        inputText.setText(inputText.getText()+"+");
    }

    @FXML
    private void onMinusPressed(ActionEvent event) {
        inputText.setText(inputText.getText()+"-");
    }

    @FXML
    private void onMulPressed(ActionEvent event) {
        inputText.setText(inputText.getText()+"*");
    }

    @FXML
    private void onDivPressed(ActionEvent event) {
        inputText.setText(inputText.getText()+"/");
    }

    @FXML
    private void onInvertPressed(ActionEvent event) {
        inputText.setText(inputText.getText()+"+-");
    }

    @FXML
    private void onSqrtPressed(ActionEvent event) {
        inputText.setText(inputText.getText()+"sqrt");
    }

    @FXML
    private void onCPressed(ActionEvent event) {
        inputText.setText("");
    }

    @FXML
    private void onClearPressed(ActionEvent event) {
        inputText.setText(inputText.getText()+"clear");
    }

    @FXML
    private void onDropPressed(ActionEvent event) {
        inputText.setText(inputText.getText()+"drop");
    }

    @FXML
    private void onDupPressed(ActionEvent event) {
        inputText.setText(inputText.getText()+"dup");
    }
    
    @FXML
    private void onSwapPressed(ActionEvent event) {
        inputText.setText(inputText.getText()+"swap");
    }

    @FXML
    private void onOverPressed(ActionEvent event) {
        inputText.setText(inputText.getText()+"over");
    }

}