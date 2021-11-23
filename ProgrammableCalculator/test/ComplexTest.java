
import it.unisa.diem.Gruppo20.Model.Complex;

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
public class ComplexTest {

    private Complex complex;

    public ComplexTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.complex = new Complex();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPlus() {
        Complex expexted = new Complex(5.0, 10.1);
        Complex actual = new Complex(2.0, 10.0);

        complex.setReal(3.0);
        complex.setImaginary(0.1);

        assertEquals("Test if the sum() works correctly",
                expexted, complex.plus(actual));
    }

    @Test
    public void testSquareRoot() {
        Complex expexted = new Complex(2.0, 1.0);

        Complex actual = new Complex(3.0, 4.0);

        assertEquals("Test if the squareRoot() works correctly",
                expexted, actual.squareRoot());
    }

}
