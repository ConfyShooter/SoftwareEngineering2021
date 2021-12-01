package it.unisa.diem.Gruppo20.Model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

/**
 * This class allows arithmetic operations on Complex number using a LIFO data
 * structure for storing.
 *
 * @author Gruppo 20
 */
public class Calculator {

    private final Deque<Complex> data;
    private final Variables var;

    /**
     * Initialize the Calculator with an empty stack.
     */
    public Calculator() {
        data = new ArrayDeque<>();
        var = new Variables();
    }

    /**
     * Initialize the calculator using params as new attributes.
     *
     * @param data
     * @param variable
     */
    public Calculator(Deque<Complex> data, Variables var) {
        this.data = data;
        this.var = var;
    }

    public Deque<Complex> getData() {
        return data;
    }

    public Variables getVariable() {
        return var;
    }

    /**
     * This function execute the parsing of the string passed as param.String
     * that contains a command to be executed by Calculator.
     *
     * @param input String that contains a command to be executed by Calculator.
     * @throws java.lang.NumberFormatException if it fails to insert a number on
     * the stack.
     * @throws java.lang.ArithmeticException if it fails to do an operation on
     * complex numbers.
     * @throws java.util.NoSuchElementException if the stack not contains enough
     * elements to perform a specific operation.
     * @throws RuntimeException if input is blank or there is a unknown error.
     */
    public void parsing(String input) throws Exception {
        input = input.replaceAll("\\s+", "");

        if (input.isBlank()) {
            throw new RuntimeException("Input string is empty!");
        }

        char sequence[] = input.toCharArray();
        int length = input.length();

        for (int i = 0; i < length; i++) {
            if ((sequence[i] >= '0' && sequence[i] <= '9')) {// in anycase in which the user want to insert a number
                insertNumber(parseNumber(input));
                return;
            }
        }
        switch(input) {
            case "j": insertNumber(new Complex(0.0,1.0)); return;
            case "+": sum(); return;
            case "-": subtract(); return;
            case "*": multiply(); return;
            case "/": division(); return;
            case "+-": invertSign(); return;
            case "sqrt": sqrt(); return;
            case "clear": clear(); return;
            case "drop": drop(); return;
            case "dup": dup(); return;
            case "swap": swap(); return;
            case "over": over(); return;
            case "save": saveVariables(); return;
            case "restore": restoreVariables(); return;
        } if (input.matches(">[a-z]{1}")) {
            pushVariable(input.charAt(1));
        } else if (input.matches("<[a-z]{1}")) {
            pullVariable(input.charAt(1));
        } else if (input.matches("\\+[a-z]{1}")) {
            sumVariable(input.charAt(1));
        } else if (input.matches("\\-[a-z]{1}")) {
            subtractVariable(input.charAt(1));
        } else {
            throw new RuntimeException("Can't parse \"" + input + "\", try to reinsert it.");
        }
    }

    private double findImaginary(String s) throws NumberFormatException {
        switch (s) {
            case "j", "+j" -> {
                return 1.0;
            }
            case "-j" -> {
                return -1.0;
            }
            default -> {
                int jIndex = s.indexOf("j");
                String number = new String();
                if (jIndex == 0) // imaginary part inserted in form jb
                {
                    number = s.substring(1, s.length());
                } else if (jIndex == 1 && (0 == s.indexOf("+") || 0 == s.indexOf("-")))// imaginary part inserted in form +jb or -jb
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
    }

    /**
     * This function extract the real and imaginary part from a complex number
     * passed as param. After Create a new Complex Object and add it into the
     * data structure.
     *
     * @param number String that contains a Complex number.
     */
    public Complex parseNumber(String number) throws NumberFormatException {
        Double real = 0.0;
        Double imaginary = 0.0;
        int jIndex = number.indexOf("j");

        if (jIndex == -1) { // the string represent a real pure number
            real = Double.parseDouble(number);
        } else if (number.indexOf("+", 1) == number.indexOf("-", 1)) {// the string passed is an imaginary pure number
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
        //data.push(c);
        return c;
    }
    /**
     * Push the complex c onto the stack.
     * @param c the complex number that must be pushed onto the stack;
     */
    public void insertNumber(Complex c) {
        data.push(c);
    }

    /**
     * Implement the sum of last two elements from the stack storing the result
     * onto it.
     *
     * @throws java.lang.NoSuchElementException
     */
    public void sum() throws NoSuchElementException {
        checkStackSize(2);

        Complex last = data.pop();
        Complex secondLast = data.pop();

        data.push(secondLast.plus(last));
    }

    /**
     * This functions implements the subtract between the secondlast and the
     * last elements in the stack. Finally store the result onto the stack
     *
     * @throws java.lang.NoSuchElementException
     */
    public void subtract() throws NoSuchElementException {
        checkStackSize(2);

        Complex last = data.pop();
        Complex secondLast = data.pop();

        data.push(secondLast.minus(last));
    }

    /**
     * Implement the moltiplication a*b of two element from the top of the stack
     * a is the second last element, while b is is the last element.removing
     * them from the stack and storing the result onto it.
     *
     * @throws java.lang.Exception
     */
    public void multiply() throws RuntimeException {
        checkStackSize(2);

        Complex last = data.pop();
        Complex secondLast = data.pop();

        data.push(secondLast.multiply(last));
    }

    /**
     * Implement the division a/b of last element from the stack with the last
     * but one from the stack b storing the result onto it.
     *
     * @throws java.lang.Exception
     */
    public void division() throws RuntimeException {
        checkStackSize(2);

        Complex last = data.pop();
        Complex secondLast = data.pop();

        data.push(secondLast.division(last));
    }

    /**
     * Implement the square root of last element from the stack storing the
     * result onto it.
     *
     * @throws java.lang.Exception
     */
    public void sqrt() throws RuntimeException {
        checkStackSize(1);

        Complex last = data.pop();

        data.push(last.squareRoot());
    }

    /**
     * This functions take the last elements from the stack and reverses its
     * sign
     *
     * @throws java.lang.NoSuchElementException
     */
    public void invertSign() throws NoSuchElementException {
        checkStackSize(1);

        Complex last = data.pop();

        data.push(last.invert());
    }

    /**
     * This method removes all elements from the stack.
     */
    public void clear() {
        data.clear();
    }

    /**
     * This method remove the last element from the stack.
     *
     * @throws NoSuchElementException if the stack is empty.
     */
    public void drop() throws NoSuchElementException {
        checkStackSize(1);
        data.pop();
    }

    /**
     * This method duplicate the last element from the stack and add the copy
     * onto it
     *
     * @throws NoSuchElementException if the stack is empty.
     */
    public void dup() throws NoSuchElementException {
        checkStackSize(1);
        data.push(data.element());
    }

    /**
     * This method swap the last and last but one element from the stack.
     *
     * @throws NoSuchElementException if the stack has less then two elements.
     */
    public void swap() throws NoSuchElementException {
        checkStackSize(2);

        Complex last = data.pop();
        Complex secondLast = data.pop();

        data.push(last);
        data.push(secondLast);
    }

    /**
     * This method duplicate the last but one element from the stack and add the
     * copy onto it.
     *
     * @throws NoSuchElementException if the stack has less than two elements.
     */
    public void over() throws NoSuchElementException {
        checkStackSize(2);

        Complex last = data.pop();
        Complex secondLast = data.element();

        data.push(last);
        data.push(secondLast);
    }

    /**
     * This function takes(removing it) the top element of the stack and
     * insert the couple (c,element) into HashMap var corresponding. 
     * @param c the key of var at which corresponds the top element of the stack.
     * @throws NoSuchElementException if the stack is empty
     */
    public void pushVariable(char c) throws NoSuchElementException {
        checkStackSize(1);
        var.setVariable(c, data.pop());
    }

    /**
     * 
     * @param c
     */
    public void pullVariable(char c) throws RuntimeException {
        Complex value = var.getVariable(c);
        data.push(value);
        
    }

    /**
     *
     * @param c
     */
    public void sumVariable(char c) throws RuntimeException {
        checkStackSize(1);
        var.sumVariable(c, data.element());
    }

    /**
     * 
     * @param c
     */
    public void subtractVariable(char c) throws RuntimeException {
        checkStackSize(1);
        var.sumVariable(c, data.element());
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

    private void checkStackSize(int k) {
        if (data.size() < k) {
            throw new NoSuchElementException("To perform this operation you must have at least " + k + " numbers.");
        }
    }

}
