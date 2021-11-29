package it.unisa.diem.Gruppo20.GUI;

import it.unisa.diem.Gruppo20.Model.Calculator;
import it.unisa.diem.Gruppo20.Model.Complex;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    private Button insertBtn;
    @FXML
    private ListView<Complex> historyList;
    @FXML
    private Button cancBtn;
    @FXML
    private TextField inputText;
    @FXML
    private CheckBox functionBox;
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

    private Calculator c;
    private ObservableList<Complex> stack;
    private ObservableList<?> functions;
    

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

        insertBtn.disableProperty().bind(inputText.textProperty().isEmpty());
        cancBtn.disableProperty().bind(inputText.textProperty().isEmpty());

        editMenu.disableProperty().bind(functionsProperty.emptyProperty());
        deleteMenu.disableProperty().bind(functionsProperty.emptyProperty());
        saveMenu.disableProperty().bind(functionsProperty.emptyProperty());
        
        stack.setAll(c.getData());
        historyList.setItems(stack);
    }

    @FXML
    private void onInsertPressed(ActionEvent event) {
        try {
            c.parsing(inputText.getText());
        } catch (RuntimeException ex) {
            showAlert(ex.getMessage());
        } catch (Exception ex) {
            showAlert("General error.");
        }

        inputText.clear();
        insertBtn.disableProperty().bind(inputText.textProperty().isEmpty());
        stack.setAll(c.getData());
    }

    @FXML
    private void onPlusPressed(ActionEvent event) {
        if(functionBox.isSelected())
            inputText.setText(inputText.getText()+" +");
        else{
            inputText.setText("+");
            insertBtn.disableProperty().bind(inputText.textProperty().length().greaterThan(2).
                and(inputText.textProperty().lessThan("+z")));
        }
            
    }

    @FXML
    private void onMinusPressed(ActionEvent event) {
        if(functionBox.isSelected())
            inputText.setText(inputText.getText()+" -");
        else{
            inputText.setText("-");
            insertBtn.disableProperty().bind(inputText.textProperty().length().greaterThan(2).
                and(inputText.textProperty().lessThan("-z")));
        }
    }

    @FXML
    private void onMulPressed(ActionEvent event) {
        onButtonPressed(event,"*");
    }

    @FXML
    private void onDivPressed(ActionEvent event) {
        onButtonPressed(event,"/");
    }

    @FXML
    private void onInvertPressed(ActionEvent event) {
        onButtonPressed(event,"+-");
    }

    @FXML
    private void onSqrtPressed(ActionEvent event) {
        onButtonPressed(event,"sqrt");
    }

    @FXML
    private void onCPressed(ActionEvent event) {
        inputText.clear();
        insertBtn.disableProperty().bind(inputText.textProperty().isEmpty());
    }

    @FXML
    private void onClearPressed(ActionEvent event) {
        onButtonPressed(event,"clear");
    }

    @FXML
    private void onDropPressed(ActionEvent event) {
        onButtonPressed(event,"drop");
    }

    @FXML
    private void onDupPressed(ActionEvent event) {
        onButtonPressed(event,"dup");
    }

    @FXML
    private void onSwapPressed(ActionEvent event) {
        onButtonPressed(event,"swap");
    }

    @FXML
    private void onOverPressed(ActionEvent event) {
        onButtonPressed(event,"over");
    }

    @FXML
    private void onMinorPressed(ActionEvent event) {
        if(functionBox.isSelected())
            inputText.setText(inputText.getText()+" <");
        else
            inputText.setText("<");   
    }

    @FXML
    private void onMajorPressed(ActionEvent event) {
        if(functionBox.isSelected())
            inputText.setText(inputText.getText()+" >");
        else
            inputText.setText(">");
    }

    @FXML
    private void onSavePressed(ActionEvent event) {
    }

    @FXML
    private void onRestorePressed(ActionEvent event) {
    }
    
    @FXML
    private void onFunctionBoxPressed(ActionEvent event) {
        if(functionBox.isSelected())
            inputText.setPromptText("name: fun1 fun2 a+bj fun3...");
        else
            inputText.setPromptText("a+bj");
    }
    
    private void onButtonPressed(ActionEvent event, String text){
        if(functionBox.isSelected())
            inputText.setText(inputText.getText()+" "+text);
        else{
            inputText.setText(text);
            onInsertPressed(event);
        }
            
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

    private void showAlert(String message) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Warning");
        a.setHeaderText(message);
        a.showAndWait();
    }

    

}
