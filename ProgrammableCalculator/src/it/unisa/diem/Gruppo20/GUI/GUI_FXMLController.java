package it.unisa.diem.Gruppo20.GUI;

import it.unisa.diem.Gruppo20.Model.Calculator;
import it.unisa.diem.Gruppo20.Model.Complex;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
        try {
            c.parsing(inputText.getText());
        } catch (NoSuchElementException ex) {
            showErrorAlert("NoSuchElementException", ex.getMessage());
        } catch (ArithmeticException ex) {
            showErrorAlert("ArithmeticException", ex.getMessage());
        } catch (NumberFormatException ex) {
            showErrorAlert("NumberFormatException", ex.getMessage());
        } catch (Exception ex) {
            showErrorAlert("Exception", "General error.");
        }

        inputText.clear();
        stack.setAll(c.getData());
    }

    @FXML
    private void onPlusPressed(ActionEvent event) {
        inputText.setText(inputText.getText() + "+");
    }

    @FXML
    private void onMinusPressed(ActionEvent event) {
        inputText.setText(inputText.getText() + "-");
    }

    @FXML
    private void onMulPressed(ActionEvent event) {
        inputText.setText(inputText.getText() + "*");
    }

    @FXML
    private void onDivPressed(ActionEvent event) {
        inputText.setText(inputText.getText() + "/");
    }

    @FXML
    private void onInvertPressed(ActionEvent event) {
        inputText.setText(inputText.getText() + "+-");
    }

    @FXML
    private void onSqrtPressed(ActionEvent event) {
        inputText.setText(inputText.getText() + "sqrt");
    }

    @FXML
    private void onCPressed(ActionEvent event) {
        inputText.clear();
    }

    @FXML
    private void onClearPressed(ActionEvent event) {
        inputText.setText(inputText.getText() + "clear");
    }

    @FXML
    private void onDropPressed(ActionEvent event) {
        inputText.setText(inputText.getText() + "drop");
    }

    @FXML
    private void onDupPressed(ActionEvent event) {
        inputText.setText(inputText.getText() + "dup");
    }

    @FXML
    private void onSwapPressed(ActionEvent event) {
        inputText.setText(inputText.getText() + "swap");
    }

    @FXML
    private void onOverPressed(ActionEvent event) {
        inputText.setText(inputText.getText() + "over");
    }

    private void showErrorAlert(String exception, String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(exception);
        a.setContentText(message);
        a.showAndWait();
    }

}
