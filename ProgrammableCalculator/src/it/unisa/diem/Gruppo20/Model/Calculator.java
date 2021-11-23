package it.unisa.diem.Gruppo20.Model;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Gruppo 20
 */
public class Calculator {

    private final ObservableList<Complex> data;
    private final Variables variable;
    private final UserDefinedOperations userOperation;

    /**
     *
     */
    public Calculator() {
        data = FXCollections.observableArrayList();
        variable = new Variables();
        userOperation = new UserDefinedOperations();
    }

    /**
     *
     * @param data
     * @param variable
     * @param userOperation
     */
    public Calculator(ObservableList<Complex> data, Variables variable, UserDefinedOperations userOperation) {
        this.data = data;
        this.variable = variable;
        this.userOperation = userOperation;
    }

    /**
     *
     * @return
     */
    public ObservableList<Complex> getData() {
        return data;
    }

    /**
     *
     * @return
     */
    public Variables getVariable() {
        return variable;
    }

    /**
     *
     * @return
     */
    public UserDefinedOperations getUserOperation() {
        return userOperation;
    }

    /**
     *
     * @param number
     * @return
     */
    public boolean insert(String number) {
        return false;
    }

    /**
     * Implement the sum of last two elements from the stack storing the result
     * onto it.
     *
     * @return true if it terminates successfully
     */
    public boolean sum() {
        Complex last = data.get(data.size() - 1);
        Complex secondLast = data.get(data.size() - 2);

        return data.add(last.plus(secondLast));
    }

    /**
     *
     * @return
     */
    public boolean subtract() {
        return false;
    }

    /**
     *
     * @return
     */
    public boolean multiply() {
        return false;
    }

    /**
     *
     * @return
     */
    public boolean division() {
        return false;
    }

    /**
     * Implement the square root of last element from the stack storing the
     * result onto it.
     *
     * @return true if it terminates successfully
     */
    public boolean sqrt() {
        Complex last = data.get(data.size() - 1);
        Complex result = last.squareRoot();

        return data.add(result);
    }

    /**
     *
     * @return
     */
    public boolean invertSign() {
        return false;
    }

    /**
     *
     * @return
     */
    public boolean clear() {
        return false;
    }

    /**
     *
     * @return
     */
    public boolean drop() {
        return false;
    }

    /**
     *
     * @return
     */
    public boolean dup() {
        return false;
    }

    /**
     *
     * @return
     */
    public boolean over() {
        return false;
    }

    /**
     *
     * @param c
     * @return
     */
    public boolean saveIntoVariable(char c) {
        return false;
    }

    /**
     *
     * @param c
     * @return
     */
    public boolean saveFromVariable(char c) {
        return false;
    }

    /**
     *
     * @param c
     * @return
     */
    public boolean sumWithVariable(char c) {
        return false;
    }

    /**
     *
     * @param c
     * @return
     */
    public boolean subtractWithVariable(char c) {
        return false;
    }

    /**
     *
     * @return
     */
    public boolean saveVariables() {
        return false;
    }

    /**
     *
     * @return
     */
    public boolean restoreVariables() {
        return false;
    }

    /**
     *
     * @param name
     * @return
     */
    public boolean executeOperation(String name) {
        return false;
    }

    /**
     *
     * @param name
     * @param op
     * @return
     */
    public boolean addOperation(String name, String[] op) {
        return false;
    }

}
