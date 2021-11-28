package it.unisa.diem.Gruppo20.GUI;

import it.unisa.diem.Gruppo20.Model.Calculator;
import it.unisa.diem.Gruppo20.Model.Complex;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
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
    private ObservableList<?> functions;
    @FXML
    private ListView<?> functionsList;
    @FXML
    private MenuItem editMenu;
    @FXML
    private MenuItem deleteMenu;
    @FXML
    private MenuItem saveMenu;
    @FXML
    private MenuItem restoreMenu;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SimpleListProperty functionsProperty = new SimpleListProperty(functions);
        c = new Calculator();
        stack = FXCollections.observableArrayList();
        functions = FXCollections.observableArrayList();
        
        calculateBtn.disableProperty().bind(inputText.textProperty().isEmpty());
        cancBtn.disableProperty().bind(inputText.textProperty().isEmpty());
        
        editMenu.disableProperty().bind(functionsProperty.emptyProperty());
        deleteMenu.disableProperty().bind(functionsProperty.emptyProperty());
        saveMenu.disableProperty().bind(functionsProperty.emptyProperty());
        
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
        calculateBtn.disableProperty().bind(inputText.textProperty().isEmpty());
        stack.setAll(c.getData());
    }

    @FXML
    private void onPlusPressed(ActionEvent event) {
        inputText.setText("+");
        calculateBtn.disableProperty().bind(inputText.textProperty().length().greaterThan(2));
    }

    @FXML
    private void onMinusPressed(ActionEvent event) {
        inputText.setText("-");
        calculateBtn.disableProperty().bind(inputText.textProperty().length().greaterThan(2));
    }

    @FXML
    private void onMulPressed(ActionEvent event) {
        inputText.setText("*");
        onCalculatePressed(event);
    }

    @FXML
    private void onDivPressed(ActionEvent event) {
        inputText.setText("/");
        onCalculatePressed(event);
    }

    @FXML
    private void onInvertPressed(ActionEvent event) {
        inputText.setText("+-");
        onCalculatePressed(event);
    }

    @FXML
    private void onSqrtPressed(ActionEvent event) {
        inputText.setText("sqrt");
        onCalculatePressed(event);
    }

    @FXML
    private void onCPressed(ActionEvent event) {
        inputText.clear();
    }

    @FXML
    private void onClearPressed(ActionEvent event) {
        inputText.setText("clear");
        onCalculatePressed(event);
    }

    @FXML
    private void onDropPressed(ActionEvent event) {
        inputText.setText("drop");
        onCalculatePressed(event);
    }

    @FXML
    private void onDupPressed(ActionEvent event) {
        inputText.setText("dup");
        onCalculatePressed(event);
    }

    @FXML
    private void onSwapPressed(ActionEvent event) {
        inputText.setText("swap");
        onCalculatePressed(event);
    }

    @FXML
    private void onOverPressed(ActionEvent event) {
        inputText.setText("over");
        onCalculatePressed(event);
    }

    @FXML
    private void onMinorPressed(ActionEvent event) {
        inputText.setText("<");
        calculateBtn.disableProperty().bind(inputText.textProperty().length().isNotEqualTo(2));
    }

    @FXML
    private void onMajorPressed(ActionEvent event) {
        inputText.setText(">");
        calculateBtn.disableProperty().bind(inputText.textProperty().length().isNotEqualTo(2));
    }

    @FXML
    private void onSavePressed(ActionEvent event) {
    }

    @FXML
    private void onRestorePressed(ActionEvent event) {
    }

    @FXML
    private void addFunction(ActionEvent event) {
    }

    @FXML
    private void editFunction(ActionEvent event) {
    }

    @FXML
    private void deleteFunction(ActionEvent event) {
    }

    @FXML
    private void saveFunctionToFile(ActionEvent event) {
    }

    @FXML
    private void restoreFunctionFromFile(ActionEvent event) {
    }
    
    private void showErrorAlert(String exception, String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(exception);
        a.setContentText(message);
        a.showAndWait();
    }

}
