package it.unisa.diem.Gruppo20.Model;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 *
 * @author Gruppo 20
 */
public class Calculator {

    private final Deque<Complex> data;
    private final Variables variable;
    private final UserDefinedOperations userOperation;

    /**
     *
     */
    public Calculator() {
        data = new ArrayDeque<>();
        variable = new Variables();
        userOperation = new UserDefinedOperations();
    }

    /**
     *
     * @param data
     * @param variable
     * @param userOperation
     */
    public Calculator(Deque<Complex> data, Variables variable, UserDefinedOperations userOperation) {
        this.data = data;
        this.variable = variable;
        this.userOperation = userOperation;
    }

    /**
     *
     * @return
     */
    public Deque<Complex> getData() {
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
     * This function execute the parsing of the string passed as param. String
     * that contains a command to be executed by Calculator.
     *
     * @param input String that contains a command to be executed by Calculator.
     * @return True if and only if the operation invoked has been performed,
     * otherwise return False.
     */
    public boolean parsing(String input) throws NumberFormatException {
        if (input.isBlank()) {
            return false;
        }
        char sequence[] = input.toCharArray();
        int length = input.length();

        for (int i = 0; i < length; i++) {
            if ((sequence[i] >= '0' && sequence[i] <= '9')) // in anycase in which the user want to insert a number
            {
                return insert(input);
            }
        }
        if (input.endsWith("j")) // in other cases like j, +j , -j
        {
            return insert(input);
        } else if (input.equals("+")) {
            return sum();
        } else if (input.equals("-")) {
            return subtract();
        } else if (input.equals("*")) {
            return multiply();
        } else if (input.equals("/")) {
            return division();
        } else if (input.equals("+-")) {
            return invertSign();
        } else if (input.equals("sqrt")) {
            return sqrt();
        } else if (input.equals("clear")) {
            return clear();
        } else if (input.equals("drop")) {
            return drop();
        } else if (input.equals("dup")) {
            return dup();
        } else if (input.equals("swap")) {
            return swap();
        } else if (input.equals("over")) {
            return over();
        } else if (input.equals("")) {//altri casi da aggiungere
            return false;
        }

        return false; // in case which any kind of operation not matched with name String
    }

    // to add into UML
    private double findImaginary(String s) throws NumberFormatException {
        switch (s) {
            case "j":
            case "+j":
                return 1.0;
            case "-j":
                return -1.0;
            default:
                int jIndex = s.indexOf("j");
                String number = new String();
                if (jIndex == 0) // imaginary part inserted in form jb
                {
                    number = s.substring(1, s.length());
                } else if (jIndex == 1 && (0 == s.indexOf("+") || 0 == s.indexOf("-"))) // imaginary part inserted in form +jb or -jb
                {
                    number = s.substring(0, jIndex).concat(s.substring(jIndex + 1, s.length()));
                } else if (jIndex == s.length() - 1) // imaginary part inserted in form bj
                {
                    number = s.substring(0, jIndex);
                } else {
                    number = s;
                }
                return Double.parseDouble(number);
        }
    }

    /**
     * This function extract the real and imaginary part from a complex number
     * passed as param. After Create a new Complex Object and add it into the
     * data structure.
     *
     * @param number String that contains a Complex number.
     * @return True if the Complex number was created and inserted into the data
     * structure, otherwise return False.
     */
    public boolean insert(String number) throws NumberFormatException {
        Double real = 0.0;
        Double imaginary = 0.0;
        //try {
        int jIndex = number.indexOf("j");
        if (jIndex == -1) { // the string represent a real pure number
            real = Double.parseDouble(number);
        } else if (number.indexOf("+", 1) == number.indexOf("-", 1)) { // the string passed is an imaginary pure number
            imaginary = findImaginary(number);
        } else { // the case in which both real and imaginary part are present inside the string
            int signIndex = number.indexOf("+", 1); //the first char of a string could be a sign +
            if (signIndex == -1) {
                signIndex = number.indexOf("-", 1); //the first char of a string could be a sign -
            }
            if (signIndex > jIndex) { // if there is the imaginary part first and real part later
                real = Double.parseDouble(number.substring(signIndex, number.length()));
                imaginary = findImaginary(number.substring(0, signIndex));
            } else if (signIndex < jIndex) { // the number is inserted in format a+bj
                real = Double.parseDouble(number.substring(0, signIndex));
                imaginary = findImaginary(number.substring(signIndex, number.length()));
            }
        }
        Complex c = new Complex(real, imaginary);
        data.add(c);
        //} catch (NumberFormatException ex) {
        //    return false;
        //}
        return true;

    }

    /**
     * Implement the sum of last two elements from the stack storing the result
     * onto it.
     *
     * @return true if it terminates successfully
     */
    public boolean sum() {
        Complex last = data.pop();
        Complex secondLast = data.pop();

        return data.offer(last.plus(secondLast));
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
        Complex last = data.pop();
        Complex result = last.squareRoot();

        return data.offer(result);
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
    public boolean swap() {
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
