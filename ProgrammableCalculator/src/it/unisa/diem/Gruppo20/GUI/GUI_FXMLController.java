package it.unisa.diem.Gruppo20.GUI;

import it.unisa.diem.Gruppo20.Model.Calculator;
import it.unisa.diem.Gruppo20.Model.Command;
import it.unisa.diem.Gruppo20.Model.Complex;
import it.unisa.diem.Gruppo20.Model.UserCommand;
import it.unisa.diem.Gruppo20.Model.Operations;
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
import javafx.scene.control.TabPane;
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

    private Calculator c;
    private Operations operations;
    private ObservableList<Complex> stack;
    private ObservableList<String> functions;
    private final File defaultFile = new File("media/functions.txt");
    @FXML
    private TabPane tabPane;
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
        c = new Calculator();
        operations = new Operations(c);
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
        tabPane.getStyleClass().add("floating");
    }

    @FXML
    private void onInsertPressed(ActionEvent event) {
        String input = inputText.getText().trim();
        try {
            UserCommand uc = (UserCommand) operations.getOperationsCommand(input.toLowerCase());
            if (functionBox.isSelected()) {
                operations.parseOperations(input);
                functions.setAll(operations.userOperationsNames());
            } else if (uc != null) {
                if(!uc.isExecutable())
                    showAlert("The implementation of function '" + input.toLowerCase() + "' has been deleted.");
                else
                    uc.execute();
                uc = null;
            } else {
                c.parsing(input);
            }
            inputText.clear();
        } catch (RuntimeException ex) {
            if (!functionBox.isSelected()) {
                inputText.clear();
            }
            showAlert(ex.getMessage());
        } catch (Exception ex) {
            showAlert("General error.");
        }

        insertBtn.disableProperty().bind(inputText.textProperty().isEmpty());
        stack.setAll(c.getData());
    }
    
    private void onInsert1Pressed(ActionEvent event) {
        String input = inputText.getText().trim();
        try {
            Command comm = operations.getOperationsCommand(input.toLowerCase());
            if (functionBox.isSelected()) {
                operations.parseOperations(input);
                functions.setAll(operations.userOperationsNames());
            } else if (comm != null) {
                comm.execute();
            } else {
                c.insertNumber(input);
            }
            inputText.clear();
        } catch (RuntimeException ex) {
            if (!functionBox.isSelected()) {
                inputText.clear();
            }
            showAlert(ex.getMessage());
        } catch (Exception ex) {
            showAlert("General error.");
        }

        insertBtn.disableProperty().bind(inputText.textProperty().isEmpty());
        stack.setAll(c.getData());
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
            inputText.clear();
        }
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
        //functionBox.setSelected(false);
        //onFunctionBoxPressed(event);
        onButtonPressed(event, functionsList.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void editFunction(ActionEvent event) {
        functionBox.setSelected(true);
        String name = functionsList.getSelectionModel().getSelectedItem();
        inputText.setText(operations.operationToString(name));
    }

    @FXML
    private void deleteFunction(ActionEvent event) {
        String name = functionsList.getSelectionModel().getSelectedItem();
        operations.removeOperations(name);
        functions.setAll(operations.userOperationsNames());
    }

    @FXML
    private void saveFunctionToFile(ActionEvent event) {
        try {
            operations.saveOnFile(defaultFile);
        } catch (IOException ex) {
            showAlert("General I/O error (while saving). Retry!");
        }
    }

    @FXML
    private void restoreFunctionFromFile(ActionEvent event) {
        try {
            operations.loadFromFile(defaultFile);
        } catch (IOException ex) {
            showAlert("General I/O error (while loading). Retry!");
        }
        functions.setAll(operations.userOperationsNames());
    }

    private void showAlert(String message) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Warning");
        a.setHeaderText(message);
        a.showAndWait();
    }

}
