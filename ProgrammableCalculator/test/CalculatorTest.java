
import it.unisa.diem.Gruppo20.Model.Calculator;
import it.unisa.diem.Gruppo20.Model.Complex;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Gruppo20
 */
public class CalculatorTest {

    private Calculator c = new Calculator();

    public CalculatorTest() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testParsing() {
        try {
            c.parsing("0.1+2.5j");
            assertEquals("0,10+2,50j", c.getData().peekFirst().toString());
            c.parsing("4.0j");
            assertEquals("4,00j", c.getData().peekFirst().toString());
            c.parsing("3.0");
            assertEquals("3,00", c.getData().peekFirst().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Test
    public void testSum() {
        try {
            c.parsing("4.0j");
            c.parsing("3.0");
            c.sum();
            assertEquals(new Complex(3.0, 4.0).toString(), c.getData().peekFirst().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Test
    public void testSqrt() {
        try {
            c.parsing("3.0+4.0j");
            c.sqrt();
            assertEquals(new Complex(2.0, 1.0).toString(), c.getData().peekFirst().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Test
    public void testDivision() {
        try {
            c.parsing("0.1+2.5j");
            c.parsing("2.0+1.0j");
            c.division();
            Complex com = new Complex((double) 27/50, (double) 49/50);
            assertEquals(com.toString(), c.getData().peekFirst().toString());
            //assertTrue("Square root on the last element", calculator.sqrt());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
            assertEquals("1,00", c.getData().element().toString());
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testDropException() throws Exception { //testing that we need at most 1 elements in the stack
        c.drop();
    }
    
    @Test
    public void testDup() throws Exception {
            c.parsing("1");
            c.dup();
            assertEquals("1,00", c.getData().pop().toString());
            assertEquals("1,00", c.getData().pop().toString());
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
            assertEquals("1,00", c.getData().pop().toString());
            assertEquals("2,00", c.getData().pop().toString());
            assertEquals("1,00", c.getData().pop().toString());
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
            assertEquals("1,00", c.getData().pop().toString());
            assertEquals("2,00", c.getData().pop().toString());
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testSwapException() throws Exception { //testing that we need at most 2 elements in the stack
        c.parsing("2");
        c.swap();
    }
    
    @Test
    public void testSubtract() {
        try {
            c.parsing("6.0j");
            c.parsing("5.0");
            c.subtract();
            assertEquals(new Complex(-5.0, 6.0).toString(), c.getData().pop().toString());
            c.parsing("-6.0");
            c.parsing("4.0");
            c.subtract();
            assertEquals(new Complex(-10.0, 0.0).toString(), c.getData().pop().toString());           
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Test
    public void testInvertSign() {
        try {
            c.parsing("1.0+0.0j");
            c.invertSign();
            assertEquals(new Complex(-1.0,0.0).toString(), c.getData().pop().toString());
            c.parsing("3.0+4.0j");
            c.invertSign();
            assertEquals(new Complex(3.0,-4.0).toString(), c.getData().pop().toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }    
    

}
