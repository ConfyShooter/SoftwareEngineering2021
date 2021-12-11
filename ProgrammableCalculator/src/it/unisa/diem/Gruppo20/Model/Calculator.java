package it.unisa.diem.Gruppo20.Model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

/**
 * This class allows operations on Complex number using a stack data structure 
 * for taking the operands and storing the results. This class allows also to 
 * perform operations on variables using a reference of Variables object.
 *
 * @author Team 20
 */
public class Calculator {

    private final Deque<Complex> data;
    private final Variables map;

    /**
     * Initialize the Calculator with an empty stack and new Variables object.
     */
    public Calculator() {
        data = new ArrayDeque<>();
        map = new Variables();
    }

    /**
     * Initialize the Calculator using params as new attributes.
     *
     * @param data
     * @param map
     */
    public Calculator(Deque<Complex> data, Variables map) {
        this.data = data;
        this.map = map;
    }

    public Deque<Complex> getData() {
        return data;
    }

    public Variables getMap() {
        return map;
    }

    private double findImaginary(String s) {
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

                if (jIndex == 0) { // imaginary part inserted in form jb
                    number = s.substring(1, s.length());
                } else if (jIndex == 1 && (0 == s.indexOf("+") || 0 == s.indexOf("-"))) { // imaginary part inserted in form +jb or -jb
                    number = s.substring(0, jIndex).concat(s.substring(jIndex + 1, s.length()));
                } else if (jIndex == s.length() - 1) { // imaginary part inserted in form bj
                    number = s.substring(0, jIndex);
                } else {
                    number = s;
                }
                return Double.parseDouble(number);
            }
        }
    }

    /**
     * This method extracts the real and imaginary part from a complex number
     * passed as param analysing all combinations of them.
     *
     * @param number String that contains a Complex number.
     * @return Complex number parsed from the string.
     * @throws NumberFormatException if in the string not contains a valid number.
     */
    public Complex parseNumber(String number) {
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
        return c;
    }

    /**
     * This method pushes the complex number onto the stack.
     *
     * @param number The complex number that must be pushed onto the stack.
     */
    public void insertNumber(Complex number) {
        data.push(number);
    }

    /**
     * This method pushes the complex c onto the stack.
     *
     * @param input The complex number that must be pushed onto the stack.
     */
    public void insertNumber(String input) {
        data.push(parseNumber(input.replaceAll("\\s", "").toLowerCase()));
    }

    /**
     * Implements the sum of last two elements from the stack storing the result
     * onto it.
     *
     * @throws java.util.NoSuchElementException if the stack has less than 2
     * elements.
     */
    public void sum() {
        checkStackSize(2);

        Complex last = data.pop();
        Complex secondLast = data.pop();

        data.push(secondLast.plus(last));
    }

    /**
     * This method implements the subtract between the second last and the last
     * elements in the stack. Finally store the result onto the stack.
     *
     * @throws java.util.NoSuchElementException if the stack has less than 2
     * elements.
     */
    public void subtract() {
        checkStackSize(2);

        Complex last = data.pop();
        Complex secondLast = data.pop();

        data.push(secondLast.minus(last));
    }

    /**
     * Implements the multiplication a*b of two elements from the top of the
     * stack, a is the second last element, while b is is the last element,
     * removing them from the stack and storing the result onto it.
     */
    public void multiply() {
        checkStackSize(2);

        Complex last = data.pop();
        Complex secondLast = data.pop();

        data.push(secondLast.multiply(last));
    }

    /**
     * Implements the division a/b of last element from the stack with the
     * second last element from the stack b storing the result onto it.
     */
    public void division() {
        checkStackSize(2);

        Complex last = data.pop();
        Complex secondLast = data.pop();

        data.push(secondLast.division(last));
    }

    /**
     * Implements the square root of last element from the stack storing the
     * result onto it.
     */
    public void sqrt() {
        checkStackSize(1);

        Complex last = data.pop();

        data.push(last.squareRoot());
    }

    /**
     * This method takes the last elements from the stack and reverses its sign.
     */
    public void invertSign() {
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
     * This method removes the last element from the stack.
     */
    public void drop() {
        checkStackSize(1);
        data.pop();
    }

    /**
     * This method pushes a copy of the last element from the stack and onto it.
     */
    public void dup() {
        checkStackSize(1);
        data.push(data.element());
    }

    /**
     * This method swaps the last and second last elements from the stack.
     */
    public void swap() {
        checkStackSize(2);

        Complex last = data.pop();
        Complex secondLast = data.pop();

        data.push(last);
        data.push(secondLast);
    }

    /**
     * This method store onto the stack a copy of the second last element.
     */
    public void over() {
        checkStackSize(2);

        Complex last = data.pop();
        Complex secondLast = data.element();

        data.push(last);
        data.push(secondLast);
    }

    /**
     * This method removes the top element of the stack and inserts it as value
     * of key c into variables map.
     *
     * @param c The variable that we want store value to.
     */
    public void pushVariable(char c) {
        checkStackSize(1);
        map.setVariable(c, data.pop());
    }

    /**
     * This method reads the Complex number associated with the variable c and
     * pushes it onto the stack.
     *
     * @param c The variable that we want read value from.
     */
    public void pullVariable(char c) {
        Complex value = map.getVariable(c);
        insertNumber(value);
    }

    /**
     * This method removes the top element of the stack and sums it at value of
     * key c into variables map.
     *
     * @param c The variable that we want sum top element of the stack to.
     */
    public void sumVariable(char c) {
        checkStackSize(1);
        map.sumVariable(c, data.pop());
    }

    /**
     * This method removes the top element of the stack and subtracts it at
     * value of key c into variables map.
     *
     * @param c The variable that we want subtract top element of the stack to.
     */
    public void subtractVariable(char c) {
        checkStackSize(1);
        map.subVariable(c, data.pop());
    }

    /**
     * This method saves the map of variables in the auxiliary stack.
     */
    public void saveVariables() {
        map.backup();
    }

    /**
     * This method restores the map of variables stored in the auxiliary stack.
     */
    public void restoreVariables() {
        map.restore();
    }

    /**
     * Takes the last element inserted on the stack, then performs the modulus
     * of that number and store the result value on top of the stack.
     */
    public void mod() {
        checkStackSize(1);
        data.push(new Complex(data.pop().mod(), 0.0));
    }

    /**
     * Takes the last element inserted on the stack, then performs the phase
     * of that number and store the result value on top of the stack.
     */
    public void arg() {
        checkStackSize(1);
        data.push(new Complex(data.pop().arg(), 0.0));
    }

    /**
     * Takes the last element inserted on the stack, then performs the cosine
     * of that number and store the result value on top of the stack.
     */
    public void cos() {
        checkStackSize(1);
        data.push(data.pop().cos());
    }

    /**
     * Takes the last element inserted on the stack, then performs the arccosine
     * of that number and store the result value on top of the stack.
     */
    public void arcCos() {
        checkStackSize(1);
        data.push(data.pop().acos());
    }

    /**
     * Takes the last element inserted on the stack, then performs the cosine
     * of that number and store the result value on top of the stack.
     */
    public void sin() {
        checkStackSize(1);
        data.push(data.pop().sin());     
    }

    /**
     * Takes the last element inserted on the stack, then performs the arcsine
     * of that number and store the result value on top of the stack.
     */
    public void arcSin() {
        checkStackSize(1);
        data.push(data.pop().asin());
    }

    /**
     * Takes the last element inserted on the stack, then performs the tangent
     * of that number and store the result value on top of the stack.
     */
    public void tan() {
        checkStackSize(1);
        data.push(data.pop().tan());  
    }

    /**
     * Takes the last element inserted on the stack, then performs the arctangent
     * of that number and store the result value on top of the stack.
     */
    public void arcTan() {
        checkStackSize(1);
        data.push(data.pop().atan());
    }

    /**
     * Takes the last element inserted on the stack, then performs the 2nd degree
     * power of that number and store the result value on top of the stack.
     */
    public void pow() {
        checkStackSize(1);
        data.push(data.pop().pow(2));
    }

    /**
     * Takes the last element inserted on the stack, then performs the exponential
     * of that number and store the result value on top of the stack.
     */
    public void exp() {
        checkStackSize(1);
        data.push(data.pop().exp());
    }

    /**
     * Takes the last element inserted on the stack, then performs the natural 
     * logarithm of that number and store the result value on top of the stack.
     */
    public void log() {
        checkStackSize(1);
        data.push(data.pop().log());
    }

    /**
     * Private method that check if there are at least k element in the stack.
     * @param k number of operands required.
     * @throws NoSuchElementException if there aren't enough elements.
     */
    private void checkStackSize(int k) throws NoSuchElementException {
        if (data.size() < k) {
            throw new NoSuchElementException("To perform this operation you must have at least " + k + " numbers.");
        }
    }
}