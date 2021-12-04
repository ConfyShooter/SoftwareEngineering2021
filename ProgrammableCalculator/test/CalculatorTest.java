
import it.unisa.diem.Gruppo20.Model.Calculator;
import it.unisa.diem.Gruppo20.Model.Complex;
import java.util.NoSuchElementException;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Gruppo20
 */
public class CalculatorTest {

    private Calculator c;

    public CalculatorTest() {
    }

    @Before
    public void setUp() throws Exception {
        c = new Calculator();
    }

    @Test(expected = RuntimeException.class)
    public void testParsingException() throws Exception {
        c.parsing("clears");
    }

    @Test
    public void testParsing() throws Exception {
        c.parsing("0.1+2.5j");
        c.parsing("1.0+2.5j");
        c.parsing("+");

        assertEquals("1.1+5j", c.getData().peekFirst().toString());
    }

    @Test(expected = NumberFormatException.class)
    public void testInsertException() throws Exception {
        c.insertNumber(c.parseNumber("-2.j5+0.1"));
    }

    @Test
    public void testInsert() {
        c.insertNumber(c.parseNumber("-j4.0"));
        assertEquals("-4j", c.getData().peekFirst().toString());

        c.insertNumber(c.parseNumber("3.05"));
        assertEquals("3.05", c.getData().peekFirst().toString());

        c.insertNumber(c.parseNumber("0.1+2.5j"));
        assertEquals("0.1+2.5j", c.getData().peekFirst().toString());

        c.insertNumber(c.parseNumber("0.1-j2.50"));
        assertEquals("0.1-2.5j", c.getData().peekFirst().toString());

        c.insertNumber(c.parseNumber("2.5j+0.1"));
        assertEquals("0.1+2.5j", c.getData().peekFirst().toString());

        c.insertNumber(c.parseNumber("-j2.5+0.1"));
        assertEquals("0.1-2.5j", c.getData().peekFirst().toString());
    }

    @Test
    public void testSum() throws Exception {
        c.parsing("4.0j");
        c.parsing("3.0");
        c.sum();

        Complex expected = new Complex(3.0, 4.0);
        assertComplexEquals(expected, c.getData().peekFirst());
    }

    @Test(expected = NoSuchElementException.class)
    public void testSumException() throws Exception {
        c.parsing("0.5+2.5j");
        c.sum();
    }

    @Test
    public void testSubtract() throws Exception {
        c.parsing("6.0j");
        c.parsing("5.0");
        c.subtract();

        Complex expected = new Complex(-5.0, 6.0);
        assertComplexEquals(expected, c.getData().peekFirst());

        c.parsing("-6.0");
        c.parsing("4.0");
        c.subtract();

        expected.setReal(-10.0);
        expected.setImaginary(0.0);
        assertComplexEquals(expected, c.getData().peekFirst());
    }

    @Test(expected = NoSuchElementException.class)
    public void testSubtractException() throws Exception {
        c.parsing("0.5+2.5j");
        c.subtract();
    }

    @Test(expected = NoSuchElementException.class)
    public void testMultiplyException() throws Exception {
        c.parsing("0.5+2.5j");
        c.multiply();
    }

    @Test
    public void testMultiply() throws Exception {
        c.parsing("0.5+2.5j");
        c.parsing("2-3.5j");
        c.multiply();

        Complex expected = new Complex((double) 39 / 4, (double) 13 / 4);
        assertComplexEquals(expected, c.getData().peekFirst());
    }

    @Test
    public void testSqrt() throws Exception {
        c.parsing("3.0+4.0j");
        c.sqrt();

        Complex expected = new Complex(2.0, 1.0);
        assertComplexEquals(expected, c.getData().peekFirst());
    }

    @Test(expected = NoSuchElementException.class)
    public void testSqrtException() throws Exception {
        c.sqrt();
    }

    @Test
    public void testInvertSign() throws Exception {
        c.parsing("1.0+0.0j");
        c.invertSign();

        Complex expected = new Complex(-1.0, 0.0);
        assertComplexEquals(expected, c.getData().pop());

        c.parsing("3.0+4.0j");
        c.invertSign();

        expected.setReal(-3.0);
        expected.setImaginary(-4.0);
        assertEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testinvertSignException() throws Exception {
        c.invertSign();
    }

    @Test
    public void testDivision() throws Exception {
        c.parsing("0.1+2.5j");
        c.parsing("2.0+1.0j");
        c.division();

        Complex expected = new Complex((double) 27 / 50, (double) 49 / 50);
        assertComplexEquals(expected, c.getData().peekFirst());
    }

    @Test(expected = NoSuchElementException.class)
    public void testDivisionException() throws Exception { //testing that we need at most 2 elements in the stack for division
        c.division();
    }

    @Test
    public void testClear() throws Exception {
        c.parsing("0.1+2.5j");
        assertFalse(c.getData().isEmpty());

        c.clear();
        assertTrue(c.getData().isEmpty());
    }

    @Test
    public void testDrop() throws Exception {
        c.parsing("0.1+2.5j");
        assertFalse(c.getData().isEmpty());

        c.drop();
        assertTrue(c.getData().isEmpty());

        c.parsing("1");
        c.parsing("0.1+2.5j");
        c.drop();

        Complex expected = new Complex(1.0, 0.0);
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testDropException() throws Exception { //testing that we need at most 2 elements in the stack for division
        c.drop();
    }

    @Test
    public void testDup() throws Exception {
        c.parsing("1");
        c.dup();

        Complex expected = new Complex(1.0, 0.0);

        assertComplexEquals(expected, c.getData().pop());
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testDupException() throws Exception { //testing that we need at most 1 elements in the stack
        c.dup();
    }

    @Test
    public void testOver() throws Exception {
        c.parsing("1");
        c.parsing("2");
        c.over();

        Complex expected = new Complex(1.0, 0.0);

        assertComplexEquals(expected, c.getData().pop());

        expected.setReal(2.0);
        assertComplexEquals(expected, c.getData().pop());

        expected.setReal(1.0);
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testOverException() throws Exception { //testing that we need at most 2 elements in the stack
        c.parsing("2");
        c.over();
    }

    @Test
    public void testSwap() throws Exception {
        c.parsing("1");
        c.parsing("2");
        c.swap();

        Complex expected = new Complex(1.0, 0.0);

        assertComplexEquals(expected, c.getData().pop());

        expected.setReal(2.0);
        assertComplexEquals(expected, c.getData().pop());
    }

    @Test(expected = NoSuchElementException.class)
    public void testSwapException() throws Exception { //testing that we need at most 2 elements in the stack
        c.parsing("2");
        c.swap();
    }
    
    @Test(expected=NoSuchElementException.class)
    public void testPushVariableException(){
        c.pushVariable('d');
    }
    
    @Test()
    public void testPushVariable() throws Exception{
        c.parsing("2+j");
        c.parsing("j");
        c.parsing("1.23456789");
        c.pushVariable('z');
        c.pushVariable('j');
        c.pushVariable('a');
        
        Complex expected = new Complex(2.0, 1.0);
        
        assertComplexEquals(expected, c.getMap().getVariable('a'));
        
        expected.setReal(0.0);
        assertComplexEquals(expected, c.getMap().getVariable('j'));
        
        expected.setReal(1.23456789);
        expected.setImaginary(0.0);
        assertComplexEquals(expected, c.getMap().getVariable('z'));
        
    }
    
    @Test(expected=RuntimeException.class)
    public void testPullVariableException(){
        c.pullVariable('f');
    }
    
    @Test()
    public void testPullVariable(){
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
    
    @Test(expected=NoSuchElementException.class)
    public void testSumVariableExceptionStack(){
        c.sumVariable('h');
    }
    
    @Test(expected=RuntimeException.class)
    public void testSumVariableException() throws Exception{
        c.parsing("-j+12.52");
        c.sumVariable('n');
    }
    
    @Test()
    public void testSumVariable(){
        Complex expected = new Complex(4.5, 5.5);
        Complex top = new Complex(0.0, 5.5);
        Complex value = new Complex(4.5, 0.0);
        
        c.insertNumber(top);
        c.insertNumber(value);
        c.pushVariable('t');
        c.sumVariable('t');
        c.pullVariable('t');
        
        assertComplexEquals(expected, c.getData().pop());
        
    }
    
    @Test(expected=NoSuchElementException.class)
    public void testSubtractVariableExceptionStack(){
        c.subtractVariable('p');
    }
    
    @Test(expected=RuntimeException.class)
    public void testSubtractVariableException() throws Exception{
        c.parsing("-j15-12");
        c.subtractVariable('s');
    }
    
    @Test()
    public void testSubtractVariable(){
        Complex value = new Complex(4.5, 5.5);
        Complex top = new Complex(0.0, 5.5);
        Complex expected = new Complex(4.5, 0.0);
        
        c.insertNumber(top);
        c.insertNumber(value);
        c.pushVariable('t');
        c.subtractVariable('t');
        c.pullVariable('t');
        
        assertComplexEquals(expected, c.getData().pop());

    }
    
//    @Test()
//    public void testSaveVariables(){
//        Complex value = new Complex(1.0, 2.0);
//        Complex expected = value;
//        
//        c.getMap().setVariable('a', value);
//        c.saveVariables();
//        assertEquals(expected, value);
//    }
//    
    @Test()
    public void testRestoreVariables(){
         Complex value = new Complex(1.0, 2.0);
        Complex expected = value;
        
        c.getMap().setVariable('a', value);
        c.saveVariables();
        assertEquals(expected, value);

    }    
    

    private void assertComplexEquals(Complex expected, Complex actual) {
        Assert.assertEquals(expected.getReal(), actual.getReal(), 0.00000001);
        Assert.assertEquals(expected.getImaginary(), actual.getImaginary(), 0.00000001);
    }

}
