package it.unisa.diem.Gruppo20.GUI;

import it.unisa.diem.Gruppo20.Model.Calculator;
import it.unisa.diem.Gruppo20.Model.Complex;
import it.unisa.diem.Gruppo20.Model.UserDefinedOperations;
import java.io.File;
import java.io.IOException;
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
 * @author Team 20
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
    private ListView<String> functionsList;
    @FXML
    private MenuItem useMenu;
    @FXML
    private MenuItem editMenu;
    @FXML
    private MenuItem deleteMenu;
    @FXML
    private MenuItem saveMenu;
    @FXML
    private MenuItem restoreMenu;

    private Calculator c;
    private UserDefinedOperations userOp;
    private ObservableList<Complex> stack;
    private ObservableList<String> functions;
    private final File defaultFile = new File("media/functions.txt");

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        c = new Calculator();
        userOp = new UserDefinedOperations(c);
        stack = FXCollections.observableArrayList();
        functions = FXCollections.observableArrayList();

        stack.setAll(c.getData());
        historyList.setItems(stack);
        functionsList.setItems(functions);
        SimpleListProperty functionsProperty = new SimpleListProperty(functions);

        insertBtn.disableProperty().bind(inputText.textProperty().isEmpty());
        cancBtn.disableProperty().bind(inputText.textProperty().isEmpty());

        useMenu.disableProperty().bind(functionsProperty.emptyProperty());
        editMenu.disableProperty().bind(functionsProperty.emptyProperty());
        deleteMenu.disableProperty().bind(functionsProperty.emptyProperty());
        saveMenu.disableProperty().bind(functionsProperty.emptyProperty());
    }

    @FXML
    private void onInsertPressed(ActionEvent event) {
        String input = inputText.getText().trim();
        try {
            if (functionBox.isSelected()) {
                userOp.parseOperations(input);
            } else if (userOp.getOperationsCommand(input.toLowerCase()) != null) {
                userOp.executeOperation(input.toLowerCase());
            } else {
                c.parsing(input);
            }
        } catch (RuntimeException ex) {
            showAlert(ex.getMessage());
        } catch (Exception ex) {
            showAlert("General error.");
        }

        inputText.clear();
        insertBtn.disableProperty().bind(inputText.textProperty().isEmpty());
        stack.setAll(c.getData());
        functions.setAll(userOp.userOperationsNames());
    }

    @FXML
    private void onPlusPressed(ActionEvent event) {
        if (functionBox.isSelected()) {
            inputText.setText(inputText.getText() + " +");
        } else {
            inputText.setText("+");
        }
    }

    @FXML
    private void onMinusPressed(ActionEvent event) {
        if (functionBox.isSelected()) {
            inputText.setText(inputText.getText() + " -");
        } else {
            inputText.setText("-");
        }
    }

    @FXML
    private void onMulPressed(ActionEvent event) {
        onButtonPressed(event, "*");
    }

    @FXML
    private void onDivPressed(ActionEvent event) {
        onButtonPressed(event, "/");
    }

    @FXML
    private void onInvertPressed(ActionEvent event) {
        onButtonPressed(event, "+-");
    }

    @FXML
    private void onSqrtPressed(ActionEvent event) {
        onButtonPressed(event, "sqrt");
    }

    @FXML
    private void onCPressed(ActionEvent event) {
        inputText.clear();
        insertBtn.disableProperty().bind(inputText.textProperty().isEmpty());
    }

    @FXML
    private void onClearPressed(ActionEvent event) {
        onButtonPressed(event, "clear");
    }

    @FXML
    private void onDropPressed(ActionEvent event) {
        onButtonPressed(event, "drop");
    }

    @FXML
    private void onDupPressed(ActionEvent event) {
        onButtonPressed(event, "dup");
    }

    @FXML
    private void onSwapPressed(ActionEvent event) {
        onButtonPressed(event, "swap");
    }

    @FXML
    private void onOverPressed(ActionEvent event) {
        onButtonPressed(event, "over");
    }

    @FXML
    private void onMinorPressed(ActionEvent event) {
        if (functionBox.isSelected()) {
            inputText.setText(inputText.getText() + " <");
        } else {
            inputText.setText("<");
        }
    }

    @FXML
    private void onMajorPressed(ActionEvent event) {
        if (functionBox.isSelected()) {
            inputText.setText(inputText.getText() + " >");
        } else {
            inputText.setText(">");
        }
    }

    @FXML
    private void onSavePressed(ActionEvent event) {
        onButtonPressed(event, "save");
    }

    @FXML
    private void onRestorePressed(ActionEvent event) {
        onButtonPressed(event, "restore");
    }

    @FXML
    private void onFunctionBoxPressed(ActionEvent event) {
        if (functionBox.isSelected()) {
            inputText.setPromptText("name: fun1 fun2 a+bj fun3...");
        } else {
            inputText.setPromptText("a+bj");
        }
        inputText.clear();
    }

    private void onButtonPressed(ActionEvent event, String text) {
        if (functionBox.isSelected()) {
            inputText.setText(inputText.getText() + " " + text);
        } else {
            inputText.setText(text);
            onInsertPressed(event);
        }
    }

    @FXML
    private void useFunction(ActionEvent event) {
        onButtonPressed(event, functionsList.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void editFunction(ActionEvent event) {
        functionBox.setSelected(true);
        String name = functionsList.getSelectionModel().getSelectedItem();
        String s = name + ":";
        for (String x : userOp.getOperationsNames(name)) {
            s += " " + x;
        }
        inputText.setText(s);
    }

    @FXML
    private void deleteFunction(ActionEvent event) {
        String name = functionsList.getSelectionModel().getSelectedItem();
        userOp.removeOperations(name);
        functions.setAll(userOp.userOperationsNames());
    }

    @FXML
    private void saveFunctionToFile(ActionEvent event) {
        try {
            userOp.saveOnFile(defaultFile);
        } catch (IOException ex) {
            showAlert("General I/O error (while saving). Retry!");
        }
    }

    @FXML
    private void restoreFunctionFromFile(ActionEvent event) {
        try {
            userOp.loadFromFile(defaultFile);
        } catch (IOException ex) {
            showAlert("General I/O error (while loading). Retry!");
        }
        functions.setAll(userOp.userOperationsNames());
    }

    private void showAlert(String message) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Warning");
        a.setHeaderText(message);
        a.showAndWait();
    }

}
