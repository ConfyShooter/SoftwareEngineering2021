package it.unisa.diem.Gruppo20.Model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

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
     */
    public void parsing(String input) throws Exception {
        if (input.isBlank()) {
            throw new RuntimeException ("Input strink is blank");
        }
        char sequence[] = input.toCharArray();
        int length = input.length();

        for (int i = 0; i < length; i++) {
            if ((sequence[i] >= '0' && sequence[i] <= '9')) // in anycase in which the user want to insert a number
            {
                insert(input);
                return;
            }
        }
        if (input.endsWith("j")) // in other cases like j, +j , -j
        {
            insert(input);
        } else if (input.equals("+")) {
            sum();
        } else if (input.equals("-")) {
            subtract();
        } else if (input.equals("*")) {
            multiply();
        } else if (input.equals("/")) {
            division();
        } else if (input.equals("+-")) {
            invertSign();
        } else if (input.equals("sqrt")) {
            sqrt();
        } else if (input.equals("clear")) {
            clear();
        } else if (input.equals("drop")) {
            drop();
        } else if (input.equals("dup")) {
            dup();
        } else if (input.equals("swap")) {
            swap();
        } else if (input.equals("over")) {
            over();
        } else if (input.equals("")) {
            return;
        } else {
            throw new RuntimeException ("Input strink not matched with one of possible command");
        }
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
     */
    public void insert(String number) throws NumberFormatException {
        Double real = 0.0;
        Double imaginary = 0.0;
        
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
        data.push(c);

    }

    /**
     * Implement the sum of last two elements from the stack storing the result
     * onto it.
     *
     */
    public void sum() throws Exception {
        Complex last = data.pop();
        Complex secondLast = data.pop();

        data.push(last.plus(secondLast));
    }

    /**
     *
     */
    public void subtract() {
        
    }

    /**
     *
     */
    public void multiply() {
        
    }

    /**
     * Implement the division a/b of last element from the stack with the last
     * but one from the stack b storing the result onto it.
     */
    public void division() throws Exception {
        Complex a = data.pop();
        Complex b = data.pop();
        data.push(a.division(b));
    }

    /**
     * Implement the square root of last element from the stack storing the
     * result onto it.
     *
     * @throws java.lang.Exception
     */
    public void sqrt() throws Exception {
        Complex last = data.pop();
        Complex result = last.squareRoot();

        data.push(result);
    }

    /**
     *
     
     */
    public void invertSign() {
        
    }

    /**
     *
     
     */
    public void clear() {
        
    }

    /**
     *
     
     */
    public void drop() {
        
    }

    /**
     *
     
     */
    public void dup() {
        
    }

    /**
     *
     
     */
    public void swap() {
        
    }

    /**
     *
     
     */
    public void over() {
        
    }

    /**
     * 
     * @param c
     */
    public void saveIntoVariable(char c) {
        return;
    }

    /**
     *
     * @param c
     */
    public void saveFromVariable(char c) {
        return;
    }

    /**
     *
     * @param c
     */
    public void sumWithVariable(char c) {
        return;
    }

    /**
     *
     * @param c
     */
    public void subtractWithVariable(char c) {
        return;
    }

    /**
     *
     */
    public void saveVariables() {
        return;
    }

    /**
     *
     */
    public void restoreVariables() {
        return;
    }

    /**
     *
     * @param name
     */
    public void executeOperation(String name) {
        return;
    }

    /**
     *
     * @param name
     * @param op
     */
    public void addOperation(String name, String[] op) {
        return;
    }

}
