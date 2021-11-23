
import it.unisa.diem.Gruppo20.Model.Calculator;
import it.unisa.diem.Gruppo20.Model.Complex;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Gruppo20
 */
public class CalculatorTest {

    private Calculator c;

    public CalculatorTest() {
        c = new Calculator();
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testParsing() {
        try {
            c.parsing("0.1+2.5j");
            assertEquals("0.1+2.5j", c.getData().peekFirst().toString());
            c.parsing("4.0j");
            assertEquals("+4.0j", c.getData().peekFirst().toString());
            c.parsing("3.0");
            assertEquals("3.0", c.getData().peekFirst().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Test
    public void testSum() {
        try {
            c.sum();
            assertEquals("3.0+4.0j", c.getData().peekFirst().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Test
    public void testSqrt() {
        try {
            c.sqrt();
            assertEquals("2.0+1.0j", c.getData().peekFirst().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Test
    public void testDivision() {
        try {
            c.division();
            Complex com = new Complex((double) 10/25, (double) -3/25);
            assertEquals(com.toString(), c.getData().peekFirst().toString());
            //assertTrue("Square root on the last element", calculator.sqrt());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
