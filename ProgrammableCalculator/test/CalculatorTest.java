
import it.unisa.diem.Gruppo20.Model.Calculator;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Gruppo20
 */
public class CalculatorTest {

    private Calculator calculator;

    public CalculatorTest() {
    }


    @Before
    public void setUp() {
    }


    @Test
    public void testSum() {
        assertTrue("Regular sum of the last two element", calculator.sum());
    }

    @Test
    public void testSqrt() {
        assertTrue("Square root on the last element", calculator.sqrt());
    }

}
