package it.unisa.diem.Gruppo20.GUI;

import it.unisa.diem.Gruppo20.Model.Calculator;
import it.unisa.diem.Gruppo20.Model.Command;
import it.unisa.diem.Gruppo20.Model.Complex;
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
import javafx.scene.control.TextInputDialog;

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
    private TabPane tabPane;
    @FXML
    private Button cancBtn1;
    @FXML
    private Button cancBtn2;

    private Calculator c;
    private Operations operations;
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
        operations = new Operations(c);
        stack = FXCollections.observableArrayList();
        functions = FXCollections.observableArrayList();

        stack.setAll(c.getData());
        historyList.setItems(stack);
        functionsList.setItems(functions);
        SimpleListProperty functionsProperty = new SimpleListProperty(functions);

        insertBtn.disableProperty().bind(inputText.textProperty().isEmpty());
        cancBtn.disableProperty().bind(inputText.textProperty().isEmpty());
        cancBtn1.disableProperty().bind(inputText.textProperty().isEmpty());
        cancBtn2.disableProperty().bind(inputText.textProperty().isEmpty());

        useMenu.disableProperty().bind(functionsProperty.emptyProperty());
        editMenu.disableProperty().bind(functionsProperty.emptyProperty());
        deleteMenu.disableProperty().bind(functionsProperty.emptyProperty());
        saveMenu.disableProperty().bind(functionsProperty.emptyProperty());
        tabPane.getStyleClass().add("floating");
    }

    @FXML
    private void onInsertPressed(ActionEvent event) {
        String input = inputText.getText().trim().toLowerCase(); //get input from textField

        try {
            Command comm = operations.getOperationsCommand(input); //search for an user or standard operation Command
            if (functionBox.isSelected()) { //if check box is selected
                operations.parseOperations(input);
                functions.setAll(operations.userOperationsNames());
            } else if (comm != null) {
                comm.execute();
            } else {
                c.insertNumber(input);
            }

            inputText.clear();
        } catch (NumberFormatException ex) {
            showAlert("Can't parse '" + inputText.getText() + "'.");
        } catch (RuntimeException ex) {
            if (!functionBox.isSelected()) {
                inputText.clear();
            }
            showAlert(ex.getMessage());
        } catch (Exception ex) {
            showAlert("General error.");
        }

        //DISABILITIAMO IL FUNCTION BOX???
        insertBtn.disableProperty().bind(inputText.textProperty().isEmpty());
        stack.setAll(c.getData());
    }

    @FXML
    private void onPlusPressed(ActionEvent event) {
        onButtonPressed(event, "+");
    }

    @FXML
    private void onMinusPressed(ActionEvent event) {
        onButtonPressed(event, "-");
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
        Character c = askForChar("Pull Variable");
        if (c != null) {
            onButtonPressed(event, "<" + c);
        }
    }

    @FXML
    private void onMajorPressed(ActionEvent event) {
        Character c = askForChar("Push Variable");
        if (c != null) {
            onButtonPressed(event, ">" + c);
        }
    }

    @FXML
    private void onPlusVariablePressed(ActionEvent event) {
        Character c = askForChar("Sum Variable");
        if (c != null) {
            onButtonPressed(event, "+" + c);
        }
    }

    @FXML
    private void onMinusVariablePressed(ActionEvent event) {
        Character c = askForChar("Subtract Variable");
        if (c != null) {
            onButtonPressed(event, "-" + c);
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

    @FXML
    private void onSinPressed(ActionEvent event) {
        onButtonPressed(event, "sin");
    }

    @FXML
    private void onCosPressed(ActionEvent event) {
        onButtonPressed(event, "cos");
    }

    @FXML
    private void onTanPressed(ActionEvent event) {
        onButtonPressed(event, "tan");
    }

    @FXML
    private void onAsinPressed(ActionEvent event) {
        onButtonPressed(event, "asin");
    }

    @FXML
    private void onModPressed(ActionEvent event) {
        onButtonPressed(event, "mod");
    }

    @FXML
    private void onArgPressed(ActionEvent event) {
        onButtonPressed(event, "arg");
    }

    @FXML
    private void onAcosPressed(ActionEvent event) {
        onButtonPressed(event, "acos");
    }

    @FXML
    private void onAtanPressed(ActionEvent event) {
        onButtonPressed(event, "atan");
    }

    @FXML
    private void onLogPressed(ActionEvent event) {
        onButtonPressed(event, "log");
    }

    @FXML
    private void onPowPressed(ActionEvent event) {
        onButtonPressed(event, "pow");
    }

    @FXML
    private void onExpPressed(ActionEvent event) {
        onButtonPressed(event, "exp");
    }

    private Character askForChar(String title) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText("Insert variable here.");
        dialog.setContentText("You can insert letter from a to z.");
        dialog.showAndWait();
        String s = dialog.getResult();

        while (s != null && !s.matches("[a-z]{1}|[A-Z]{1}")) {
            showAlert("You must insert only 1 letter.");
            dialog.showAndWait();
            s = dialog.getResult();
        }

        return (s != null) ? s.charAt(0) : null;
    }

}
