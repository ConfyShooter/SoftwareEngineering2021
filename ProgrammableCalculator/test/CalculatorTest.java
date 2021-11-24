
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

    
    @Test(expected=RuntimeException.class)
    public void testParsingException() throws Exception {
        c.parsing("clears");
    }
    
    @Test(timeout=1000)
    public void testParsing() throws Exception {
        c.parsing("0.1+2.5j");
        c.parsing("1.0+2.5j");
        c.parsing("+");
        assertEquals("1,10+5,00j", c.getData().peekFirst().toString());
    }
    @Test(expected=NumberFormatException.class)
    public void testInsertException() throws Exception {
        c.insert("-2.j5+0.1");
    }
    
    @Test(timeout=1000)
    public void testInsert() {
        try {
            c.insert("-j4.0");
            assertEquals("-4,00j", c.getData().peekFirst().toString());
            c.insert("3.05");
            assertEquals("3,05", c.getData().peekFirst().toString());
            c.insert("0.1+2.5j");
            assertEquals("0,10+2,50j", c.getData().peekFirst().toString());
            c.insert("0.1-j2.50");
            assertEquals("0,10-2,50j", c.getData().peekFirst().toString());
            c.insert("2.5j+0.1");
            assertEquals("0,10+2,50j", c.getData().peekFirst().toString());
            c.insert("-j2.5+0.1");
            assertEquals("0,10-2,50j", c.getData().peekFirst().toString());
            
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
    
    @Test(expected=NoSuchElementException.class)
    public void testMultiplyException() throws Exception {
        c.parsing("0.5+2.5j");
        c.multiply();
    }
    
    @Test
    public void testMultiply() throws Exception {
        c.parsing("0.5+2.5j");
        c.parsing("2-3.5j");
        c.multiply();
        assertEquals(new Complex((double) 39/4, (double)13/4).toString(), c.getData().peekFirst().toString());
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

}
