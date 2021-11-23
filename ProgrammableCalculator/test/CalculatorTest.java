
import it.unisa.diem.Gruppo20.Model.Calculator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gruppo20
 */
public class CalculatorTest {

    private Calculator calculator;

    public CalculatorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
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
