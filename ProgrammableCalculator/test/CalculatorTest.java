
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
        assertComplexEquals(expected, c.parseNumber("0"));

        expected.setReal(-100d);
        assertComplexEquals(expected, c.parseNumber("-100"));
        
        expected.setReal(0d);
        expected.setImaginary(-1d);
        assertComplexEquals(expected, c.parseNumber("-1j"));
        
        expected.setReal(0d);
        expected.setImaginary(54d);
        assertComplexEquals(expected, c.parseNumber("54j"));

        expected.setReal(0.1);
        expected.setImaginary(2.5);
        assertComplexEquals(expected, c.parseNumber("0.1+2.5j"));
    }

    @Test(expected = NumberFormatException.class)
    public void testInsertException() throws Exception {
        c.insertNumber("-2.j5+0.1");
    }

    @Test
    public void testInsertNumber() {
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

    @Test(expected = NoSuchElementException.class)
    public void testCosException() {
        c.cos();
    }

    @Test
    public void testCos() {
        c.insertNumber(zero);
        c.cos();
        expected.setReal(1d);
        assertComplexEquals(expected, c.getData().pop());
        
        operand1Real.setReal(Math.PI);
        c.insertNumber(operand1Real);
        c.cos();
        expected.setReal(Math.cos(operand1Real.getReal())*operand1Real.cosh(operand1Real.getImaginary()));
        assertComplexEquals(expected, c.getData().pop());
        
        c.insertNumber(operand1Imaginary);
        c.cos();
        expected.setReal(Math.cos(operand1Imaginary.getReal()) * operand1Imaginary.cosh(operand1Imaginary.getImaginary()));
        expected.setImaginary(-Math.sin(operand1Imaginary.getReal()) * operand1Imaginary.sinh(operand1Imaginary.getImaginary()));
        assertComplexEquals(expected, c.getData().pop());
        
        operand1.setReal(-3d);
        operand1.setImaginary(-45d);
        c.insertNumber(operand1);
        c.cos();
        expected.setReal(Math.cos(operand1.getReal())*operand1.cosh(operand1.getImaginary()));
        expected.setImaginary(-Math.sin(operand1.getReal()) * operand1.sinh(operand1.getImaginary()));
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testArcCosException() {
        c.arcCos();
    }

    @Test
    public void testArcCos() {
        Complex halfPi = new Complex(Math.PI / 2, 0d);
        
        c.insertNumber(zero);
        c.arcCos();
        expected.setReal(halfPi.getReal());
        assertComplexEquals(expected, c.getData().pop());
        
        c.insertNumber(operand1Real);
        c.arcCos();
        operand1Imaginary.setImaginary(Math.sqrt(15) - 4);
        expected = halfPi.minus(operand1Imaginary.log().multiply(Complex.ImaginaryUnit));
        assertComplexEquals(expected, c.getData().pop());
        
        c.insertNumber(operand2Imaginary);
        c.arcCos();
        operand2Real.setReal(Math.sqrt(26) - 5);
        expected = halfPi.minus(operand2Real.log().multiply(Complex.ImaginaryUnit));
        assertComplexEquals(expected, c.getData().pop());
        
        c.insertNumber(operand2);
        c.arcCos();
        operand1.setReal(-8978d);
        operand1.setImaginary(-4900d);
        operand1Real.setReal(-25d);
        operand1Real.setImaginary(98d);
        Complex log = operand1.squareRoot().plus(operand1Real).log();
        expected = halfPi.minus(log.multiply(Complex.ImaginaryUnit));
        assertComplexEquals(expected, c.getData().pop());

    }

    @Test(expected = NoSuchElementException.class)
    public void testSinException() {
        c.sin();

    }

    @Test
    public void testSin() {
        c.insertNumber(zero);
        c.sin();
        assertComplexEquals(expected, c.getData().pop());
        
        operand1Real.setReal(Math.PI);
        c.insertNumber(operand1Real);
        c.sin();
        expected.setReal(Math.sin(operand1Real.getReal())*operand1Real.cosh(operand1Real.getImaginary()));
        assertComplexEquals(expected, c.getData().pop());
        
        c.insertNumber(operand1Imaginary);
        c.sin();
        expected.setReal(0d);
        expected.setImaginary(Math.cos(operand1Imaginary.getReal()) * operand1Imaginary.sinh(operand1Imaginary.getImaginary()));
        assertComplexEquals(expected, c.getData().pop());
        
        operand1.setReal(-3d);
        operand1.setImaginary(-45d);
        c.insertNumber(operand1);
        c.sin();
        expected.setReal(Math.sin(operand1.getReal())*operand1.cosh(operand1.getImaginary()));
        expected.setImaginary(Math.cos(operand1.getReal()) * operand1.sinh(operand1.getImaginary()));
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testArcSinException() {
        c.arcSin();
    }

    @Test
    public void testArcSin() {
        c.insertNumber(zero);
        c.arcSin();
        assertComplexEquals(expected, c.getData().pop());
        
        c.insertNumber(operand1Real);
        c.arcSin();
        operand1Imaginary.setImaginary(Math.sqrt(15) - 4);
        expected = operand1Imaginary.log().multiply(Complex.ImaginaryUnit);
        assertComplexEquals(expected, c.getData().pop());
        
        c.insertNumber(operand2Imaginary);
        c.arcSin();
        operand2Real.setReal(Math.sqrt(26) - 5);
        expected = operand2Real.log().multiply(Complex.ImaginaryUnit);
        assertComplexEquals(expected, c.getData().pop());
        
        c.insertNumber(operand2);
        c.arcSin();
        operand1.setReal(-8978d);
        operand1.setImaginary(-4900d);
        operand1Real.setReal(-25d);
        operand1Real.setImaginary(98d);
        Complex log = operand1.squareRoot().plus(operand1Real).log();
        expected = log.multiply(Complex.ImaginaryUnit);
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testTanException() {
        c.tan();
    }

    @Test
    public void testTan() {
        c.insertNumber(zero);
        c.tan();
        assertComplexEquals(expected, c.getData().pop());

        operand1Real.setReal(Math.PI);
        c.insertNumber(operand1Real);
        c.tan();        
        expected = operand1Real.sin().division(operand1Real.cos());
        assertComplexEquals(expected, c.getData().pop());
        
        c.insertNumber(operand1Imaginary);
        c.tan();  
        expected = operand1Imaginary.sin().division(operand1Imaginary.cos());
        assertComplexEquals(expected, c.getData().pop());
        
        c.insertNumber(operand1);
        c.tan();  
        expected = operand1.sin().division(operand1.cos());;
        assertComplexEquals(expected, c.getData().pop());

    }

    @Test(expected = NoSuchElementException.class)
    public void testArcTanException() {
        c.arcTan();
    }

    @Test
    public void testArcTan() {
        Complex halfImg = new Complex(0d, -0.5);
        
        c.insertNumber(zero);
        c.arcTan();
        assertComplexEquals(expected, c.getData().pop());
        
        c.insertNumber(operand1Real);
        c.arcTan();
        operand1.setImaginary((double) 8 / 17);
        operand1.setReal((double) -15 / 17);
        expected = operand1.log().multiply(halfImg);
        assertComplexEquals(expected, c.getData().pop());
        
        c.insertNumber(operand2Imaginary);
        c.arcTan();
        operand2Real.setReal(-1.5);
        expected = operand2Real.log().multiply(halfImg);
        assertComplexEquals(expected, c.getData().pop());
        
        c.insertNumber(operand2);
        c.arcTan();
        operand1.setReal(26d);
        operand1.setImaginary(-98d);
        operand1Real.setReal(-24d);
        operand1Real.setImaginary(98d);
        Complex log = operand1.division(operand1Real).log();
        expected = halfImg.multiply(log);
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testPowException() {
        c.pow();
    }

    @Test
    public void testPow() {
        c.insertNumber(zero);
        c.pow();
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1);
        c.pow();
        expected.setReal(-5.0625);
        expected.setImaginary(-205d);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Imaginary);
        c.pow();
        expected.setReal(-18.0625);
        expected.setImaginary(0d);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Real);
        c.pow();
        expected.setReal(16d);
        expected.setImaginary(0d);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand2Real);
        c.pow();
        expected.setReal(9d);
        expected.setImaginary(0d);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand2);
        c.pow();
        expected.setReal(8979d);
        expected.setImaginary(4900d);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand2Imaginary);
        c.pow();
        expected.setReal(-25d);
        expected.setImaginary(0d);
        assertComplexEquals(expected, c.getData().pop());
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testExpException() {
        c.exp();
    }

    @Test
    public void testExp() {
        c.insertNumber(zero);
        c.exp();
        expected.setReal(1d);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Real);
        c.exp();
        expected.setReal(54.59815003);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand2Real.invert());
        c.exp();
        expected.setReal(0.04978706);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1Imaginary);
        c.exp();
        expected.setReal(-0.44608748);
        expected.setImaginary(0.89498935);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand2Imaginary.invert());
        c.exp();
        expected.setReal(0.28366218);
        expected.setImaginary(-0.95892427);
        assertComplexEquals(expected, c.getData().pop());
        
        c.insertNumber(operand1);
        c.exp();
        expected.setReal(-14942.61894286);
        expected.setImaginary(16182.80984680);
        assertComplexEquals(expected, c.getData().pop());
        
        operand2 = operand2.division(operand1);
        
        c.insertNumber(operand2);
        c.exp();
        expected.setReal(0.02892131);
        expected.setImaginary(0.00483180);
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testLogExceptionStack() {
        c.log();
    }
    
    @Test(expected = ArithmeticException.class)
    public void testLogException() {
        c.insertNumber(zero);
        c.log();
    }

    @Test
    public void testLog() {
        c.insertNumber(operand1Imaginary);
        c.log();
        expected.setReal(1.4469189829);
        expected.setImaginary(-1.57079632679);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand1);
        c.log();
        expected.setReal(2.6616574053);
        expected.setImaginary(-0.797743215);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand2);
        c.log();
        expected.setReal(4.616491051);
        expected.setImaginary(-2.891817864);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand2Imaginary);
        c.log();
        expected.setReal(1.60943791);
        expected.setImaginary(-1.570796327);
        assertComplexEquals(expected, c.getData().pop());

        c.insertNumber(operand2Real);
        c.log();
        expected.setReal(1.0986122886681);
        expected.setImaginary(0d);
        assertComplexEquals(expected, c.getData().pop());
    }

    private void assertComplexEquals(Complex expected, Complex actual) {
        assertEquals(expected.getReal(), actual.getReal(), 0.00000001);
        assertEquals(expected.getImaginary(), actual.getImaginary(), 0.00000001);
    }

}
