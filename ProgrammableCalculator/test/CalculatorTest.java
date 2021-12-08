
import it.unisa.diem.Gruppo20.Model.Calculator;
import it.unisa.diem.Gruppo20.Model.Complex;
import java.util.NoSuchElementException;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Team 20
 */
public class CalculatorTest {

    private Calculator c;
    /**
     * Number 0.
     */
    private Complex zero;
    /**
     * Pure real number 4.
     */
    private Complex operand1Real;
    /**
     * Pure real number 3.
     */
    private Complex operand2Real;
    /**
     * Pure imaginary number -4.25.
     */
    private Complex operand1Imaginary;
    /**
     * Pure imaginary number -5.
     */
    private Complex operand2Imaginary;
    /**
     * Complex number 10 - 10.25j.
     */
    private Complex operand1;
    /**
     * Complex number -98 - 25j.
     */
    private Complex operand2;
    private Complex expected;

    public CalculatorTest() {
    }

    @Before
    public void setUp() throws Exception {
        c = new Calculator();
        zero = new Complex();
        operand1Real = new Complex(4.0, 0.0);
        operand2Real = new Complex(3.0, 0.0);
        operand1Imaginary = new Complex(0.0, -4.25);
        operand2Imaginary = new Complex(0.0, -5.0);
        operand1 = new Complex(10.0, -10.25);
        operand2 = new Complex(-98.0, -25.0);
        expected = new Complex();
    }

    @Test(expected = RuntimeException.class)
    public void testParseNumberException() throws Exception {
        c.parseNumber("clears");
    }

    @Test
    public void testParseNumber() throws Exception {
        c.parseNumber("0");
        c.parseNumber("0");
        c.multiply();

        assertEquals("0", c.getData().pop().toString());

        c.parseNumber("-5");
        c.parseNumber("4");
        c.subtract();

        assertEquals("-9", c.getData().pop().toString());

        c.parseNumber("-1j");
        c.parseNumber("4j");
        c.division();

        assertEquals("-0.25", c.getData().pop().toString());

        c.parseNumber("0.1+2.5j");
        c.parseNumber("1.0+2.5j");
        c.sum();

        assertEquals("1.1+5j", c.getData().pop().toString());
    }

    @Test(expected = NumberFormatException.class)
    public void testInsertException() throws Exception {
        c.insertNumber("-2.j5+0.1");
    }

    @Test
    public void testInsert() {
        c.insertNumber("-j4.0");
        assertEquals("-4j", c.getData().pop().toString());

        c.insertNumber("3.05");
        assertEquals("3.05", c.getData().pop().toString());

        c.insertNumber("0.1+2.5j");
        assertEquals("0.1+2.5j", c.getData().pop().toString());

        c.insertNumber("0.1-j2.50");
        assertEquals("0.1-2.5j", c.getData().pop().toString());

        c.insertNumber("2.5j+0.1");
        assertEquals("0.1+2.5j", c.getData().pop().toString());

        c.insertNumber("-j2.5+0.1");
        assertEquals("0.1-2.5j", c.getData().pop().toString());
    }

    @Test
    public void testSum() throws Exception {
        c.insertNumber(zero);
        c.insertNumber(zero);
        c.sum();
        expected = new Complex(0.0, 0.0);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Real);
        c.insertNumber(operand2Real);
        c.sum();
        expected.setReal(7.0);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Imaginary);
        c.insertNumber(operand2Imaginary);
        c.sum();
        expected.setReal(0.0);
        expected.setImaginary(-9.25);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Real);
        c.insertNumber(operand2Imaginary);
        c.sum();
        expected.setReal(4d);
        expected.setImaginary(-5d);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1);
        c.insertNumber(operand2);
        c.sum();
        expected.setReal(-88.0);
        expected.setImaginary(-35.25);
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testSumException() throws Exception {
        c.parseNumber("0.5+2.5j");
        c.sum();
    }

    @Test
    public void testSubtract() throws Exception {
        c.insertNumber(zero);
        c.insertNumber(zero);
        c.subtract();
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Real);
        c.insertNumber(operand2Real);
        c.subtract();
        expected.setReal(1.0);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Imaginary);
        c.insertNumber(operand2Imaginary);
        c.subtract();
        expected.setReal(0.0);
        expected.setImaginary(0.75);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Imaginary);
        c.insertNumber(operand2Real);
        c.subtract();
        expected.setReal(-3d);
        expected.setImaginary(-4.25);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1);
        c.insertNumber(operand2);
        c.subtract();
        expected.setReal(108.0);
        expected.setImaginary(14.75);
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testSubtractException() throws Exception {
        c.insertNumber(operand1);
        c.subtract();
    }

    @Test(expected = NoSuchElementException.class)
    public void testMultiplyException() throws Exception {
        c.insertNumber(operand1);
        c.multiply();
    }

    @Test
    public void testMultiply() throws Exception {
        c.insertNumber(zero);
        c.insertNumber(zero);
        c.multiply();
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Real);
        c.insertNumber(operand2Real);
        c.multiply();
        expected.setReal(12d);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Imaginary);
        c.insertNumber(operand2Imaginary);
        c.multiply();
        expected.setReal(-21.25);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand2Imaginary);
        c.insertNumber(operand1Real);
        c.multiply();
        expected.setReal(0d);
        expected.setImaginary(-20d);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1);
        c.insertNumber(operand2);
        c.multiply();
        expected.setReal(-1236.25);
        expected.setImaginary(754.5);
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test
    public void testSqrt() throws Exception {
        c.insertNumber(operand1Real);
        c.sqrt();
        expected.setReal(2d);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand2Imaginary);
        c.sqrt();
        expected.setReal(Math.sqrt(10) / 2);
        expected.setImaginary(-1 * Math.sqrt(10) / 2);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1);
        c.sqrt();
        Double r = Math.sqrt(Math.sqrt(205.0625));
        Double arg = Math.atan(-1.025);
        expected.setReal(r * Math.cos(arg / 2));
        expected.setImaginary(r * Math.sin(arg / 2));
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testSqrtException() throws Exception {
        c.sqrt();
    }

    @Test
    public void testInvertSign() throws Exception {
        c.insertNumber(zero);
        c.invertSign();
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Real);
        c.invertSign();
        expected.setReal(-4.0);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Imaginary);
        c.invertSign();
        expected.setReal(0.0);
        expected.setImaginary(4.25);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand2);
        c.invertSign();
        expected.setReal(98.0);
        expected.setImaginary(25.0);
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testinvertSignException() throws Exception {
        c.invertSign();
    }

    @Test
    public void testDivision() throws Exception {
        c.insertNumber(operand1Real);
        c.insertNumber(operand2Real);
        c.division();
        expected.setReal((double) 4 / 3);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Imaginary);
        c.insertNumber(operand2Imaginary);
        c.division();
        expected.setReal((double) 17 / 20);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Real);
        c.insertNumber(operand2Imaginary);
        c.division();
        expected.setReal(0d);
        expected.setImaginary((double) 4 / 5);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1);
        c.insertNumber(operand2);
        c.division();
        expected.setReal((double) -2895 / 40916);
        expected.setImaginary((double) 12545 / 102290);
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testDivisionException() throws Exception { //testing that we need at most 2 elements in the stack for division
        c.division();
    }

    @Test
    public void testClear() throws Exception {
        c.insertNumber(operand1);
        assertFalse(c.getData().isEmpty());

        c.clear();
        assertTrue(c.getData().isEmpty());
    }

    @Test
    public void testDrop() throws Exception {
        c.insertNumber(operand2);
        assertFalse(c.getData().isEmpty());

        c.drop();
        assertTrue(c.getData().isEmpty());

        c.insertNumber(operand1Real);
        c.insertNumber(operand2Imaginary);
        c.drop();

        expected.setReal(4d);
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testDropException() throws Exception {
        c.drop();
    }

    @Test
    public void testDup() throws Exception {
        c.insertNumber(zero);
        c.dup();
        assertComplexEquals(expected, c.getData().pop());
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Real);
        c.dup();
        expected.setReal(4d);
        assertComplexEquals(expected, c.getData().pop());
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand2Imaginary);
        c.dup();
        expected.setReal(0d);
        expected.setImaginary(-5d);
        assertComplexEquals(expected, c.getData().pop());
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand2);
        c.dup();
        expected.setReal(operand2.getReal());
        expected.setImaginary(operand2.getImaginary());
        assertComplexEquals(expected, c.getData().pop());
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testDupException() throws Exception { //testing that we need at most 1 elements in the stack
        c.dup();
    }

    @Test
    public void testOver() throws Exception {
        c.insertNumber(zero);
        c.insertNumber(zero);
        c.over();
        assertComplexEquals(expected, c.getData().pop());
        assertComplexEquals(expected, c.getData().pop());
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Real);
        c.insertNumber(operand2Real);
        c.over();
        expected.setReal(operand1Real.getReal());
        assertComplexEquals(expected, c.getData().pop());
        expected.setReal(operand2Real.getReal());
        assertComplexEquals(expected, c.getData().pop());
        expected.setReal(operand1Real.getReal());
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Imaginary);
        c.insertNumber(operand2Imaginary);
        c.over();
        expected.setReal(0d);
        expected.setImaginary(operand1Imaginary.getImaginary());
        assertComplexEquals(expected, c.getData().pop());
        expected.setImaginary(operand2Imaginary.getImaginary());
        assertComplexEquals(expected, c.getData().pop());
        expected.setImaginary(operand1Imaginary.getImaginary());
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1);
        c.insertNumber(operand2);
        c.over();
        expected.setReal(operand1.getReal());
        expected.setImaginary(operand1.getImaginary());
        assertComplexEquals(expected, c.getData().pop());
        expected.setReal(operand2.getReal());
        expected.setImaginary(operand2.getImaginary());
        assertComplexEquals(expected, c.getData().pop());
        expected.setReal(operand1.getReal());
        expected.setImaginary(operand1.getImaginary());
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testOverException() throws Exception { //testing that we need at most 2 elements in the stack
        c.insertNumber(operand1);
        c.over();
    }

    @Test
    public void testSwap() throws Exception {
        c.insertNumber(zero);
        c.insertNumber(zero);
        c.swap();
        assertComplexEquals(expected, c.getData().pop());
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Real);
        c.insertNumber(operand2Real);
        c.swap();
        expected.setReal(operand1Real.getReal());
        assertComplexEquals(expected, c.getData().pop());
        expected.setReal(operand2Real.getReal());
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Imaginary);
        c.insertNumber(operand2Imaginary);
        c.swap();
        expected.setReal(0d);
        expected.setImaginary(operand1Imaginary.getImaginary());
        assertComplexEquals(expected, c.getData().pop());
        expected.setImaginary(operand2Imaginary.getImaginary());
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1);
        c.insertNumber(operand2);
        c.swap();
        expected.setReal(operand1.getReal());
        expected.setImaginary(operand1.getImaginary());
        assertComplexEquals(expected, c.getData().pop());
        expected.setReal(operand2.getReal());
        expected.setImaginary(operand2.getImaginary());
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testSwapException() throws Exception { //testing that we need at most 2 elements in the stack
        c.insertNumber(operand1);
        c.swap();
    }

    @Test(expected = NoSuchElementException.class)
    public void testPushVariableException() {
        c.pushVariable('d');
    }

    @Test()
    public void testPushVariable() throws Exception {
        c.insertNumber(new Complex(2d, 1d));
        c.insertNumber(new Complex(0d, 1d));
        c.insertNumber(new Complex(1.23456789, 0d));
        c.pushVariable('z');
        c.pushVariable('j');
        c.pushVariable('a');

        expected.setReal(2d);
        expected.setImaginary(1d);
        assertComplexEquals(expected, c.getMap().getVariable('a'));

        expected.setReal(0.0);
        assertComplexEquals(expected, c.getMap().getVariable('j'));

        expected.setReal(1.23456789);
        expected.setImaginary(0.0);
        assertComplexEquals(expected, c.getMap().getVariable('z'));
    }

    @Test(expected = RuntimeException.class)
    public void testPullVariableException() {
        c.pullVariable('f');
    }

    @Test()
    public void testPullVariable() {
        Complex expectedA = new Complex(4.5, 5.5);
        Complex expectedJ = new Complex(0.0, 5.5);
        Complex expectedZ = new Complex(4.5, 0.0);

        c.getMap().setVariable('a', expectedA);
        c.getMap().setVariable('j', expectedJ);
        c.getMap().setVariable('z', expectedZ);
        c.pullVariable('a');
        c.pullVariable('j');
        c.pullVariable('z');

        assertComplexEquals(expectedZ, c.getData().pop());
        assertComplexEquals(expectedJ, c.getData().pop());
        assertComplexEquals(expectedA, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testSumVariableExceptionStack() {
        c.sumVariable('h');
    }

    @Test(expected = RuntimeException.class)
    public void testSumVariableException() throws Exception {
        c.insertNumber(new Complex(12.52, -1d));
        c.sumVariable('n');
    }

    @Test()
    public void testSumVariable() {
        expected.setReal(4.5);
        expected.setImaginary(5.5);
        Complex top = new Complex(0.0, 5.5);
        Complex value = new Complex(4.5, 0.0);

        c.insertNumber(top);
        c.insertNumber(value);
        c.pushVariable('t');
        c.sumVariable('t');
        c.pullVariable('t');

        assertComplexEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testSubtractVariableExceptionStack() {
        c.subtractVariable('p');
    }

    @Test(expected = RuntimeException.class)
    public void testSubtractVariableException() throws Exception {
        c.insertNumber(new Complex(-12d, -15d));
        c.subtractVariable('s');
    }

    @Test()
    public void testSubtractVariable() {
        Complex value = new Complex(4.5, 5.5);
        Complex top = new Complex(0.0, 5.5);
        expected.setReal(4.5);

        c.insertNumber(top);
        c.insertNumber(value);
        c.pushVariable('t');
        c.subtractVariable('t');
        c.pullVariable('t');

        assertComplexEquals(expected, c.getData().pop());
    }

    @Test()
    public void testSaveVariables() {
        expected.setReal(operand1.getReal());
        expected.setImaginary(operand1.getImaginary());

        c.insertNumber(operand1);
        c.pushVariable('j');
        c.saveVariables();
        assertEquals(1, c.getMap().getBackupsStack().size());
        assertNotNull(c.getMap().getBackupsStack().peek());
        assertComplexEquals(expected, c.getMap().getBackupsStack().element().get('j'));
    }

    @Test()
    public void testRestoreVariables() {
        c.insertNumber(operand2);
        c.pushVariable('j');
        c.saveVariables();

        c.insertNumber(operand1);
        c.pushVariable('j');
        expected.setReal(operand1.getReal());
        expected.setImaginary(operand1.getImaginary());
        assertComplexEquals(expected, c.getMap().getVariable('j'));

        c.restoreVariables();
        expected.setReal(operand2.getReal());
        expected.setImaginary(operand2.getImaginary());
        assertComplexEquals(expected, c.getMap().getVariable('j'));

    }

    @Test(expected = NoSuchElementException.class)
    public void testModException() {
        c.clear();
        c.mod();
    }

    @Test
    public void testMod() {
        c.insertNumber(zero);
        c.mod();
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Real);
        c.mod();
        expected.setReal(4d);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Imaginary);
        c.mod();
        expected.setReal(4.25);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand2Imaginary);
        c.mod();
        expected.setReal(5d);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1);
        c.mod();
        expected.setReal(14.32000349);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(new Complex(-12.000921, -0.00001));
        c.mod();
        expected.setReal(12.000921);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(new Complex(0.00000001, -0.00001));
        c.mod();
        expected.setReal(0.00001);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand2);
        c.mod();
        expected.setReal(101.13851887);
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testArgException() {
        c.clear();
        c.arg();
    }

    @Test(expected = ArithmeticException.class)
    public void testArg() {
        c.insertNumber(zero);
        c.arg();

        c.insertNumber(operand1);
        c.arg();
        expected.setReal(-0.797743);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Imaginary);
        c.arg();
        expected.setReal(-1.570796);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Real);
        c.arg();
        expected.setReal(0d);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand2);
        c.arg();
        expected.setReal(-2.891817864);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand2Imaginary);
        c.arg();
        expected.setReal(-1.570796327);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand2Real);
        c.arg();
        expected.setReal(0d);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(new Complex(0.00000001, -0.00001));
        c.arg();
        expected.setReal(-1.5698);
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test()
    public void testCosException() {

    }

    @Test
    public void testCos() {

    }

    @Test()
    public void testArcCosException() {

    }

    @Test
    public void testArcCos() {

    }

    @Test()
    public void testSinException() {

    }

    @Test
    public void testSin() {

    }

    @Test()
    public void testArcSinException() {

    }

    @Test
    public void testArcSin() {

    }

    @Test()
    public void testTanException() {

    }

    @Test
    public void testTan() {

    }

    @Test()
    public void testArcTanException() {

    }

    @Test
    public void testArcTan() {

    }

    @Test()
    public void testPowException() {

    }

    @Test
    public void testPow() {

    }

    @Test()
    public void testExpException() {

    }

    @Test
    public void testExp() {

    }

    @Test()
    public void testLogException() {

    }

    @Test
    public void testLog() {

    }

    private void assertComplexEquals(Complex expected, Complex actual) {
        assertEquals(expected.getReal(), actual.getReal(), 0.00000001);
        assertEquals(expected.getImaginary(), actual.getImaginary(), 0.00000001);
    }

}
