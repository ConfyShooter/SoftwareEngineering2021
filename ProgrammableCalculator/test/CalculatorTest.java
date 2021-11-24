
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

}
