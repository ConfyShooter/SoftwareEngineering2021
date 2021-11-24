
import it.unisa.diem.Gruppo20.Model.Complex;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Gruppo20
 */
public class ComplexTest {

    private Complex complex;

    public ComplexTest() {
    }

    @Before
    public void setUp() {
        complex = new Complex();
    }


    @Test
    public void testPlus() {
        Complex expexted = new Complex(5.0, 10.1);
        Complex actual = new Complex(2.0, 10.0);

        complex.setReal(3.0);
        complex.setImaginary(0.1);

        assertEquals("Test if the sum works correctly",
                expexted, complex.plus(actual));
    }
    
    @Test
    public void testMultiply() {
        Complex expexted = new Complex((double) 39/4, (double) 13/4);
        Complex actual = new Complex(2.0, (double) -7/2);

        complex.setReal(0.5);
        complex.setImaginary((double) 5/2);

        assertEquals("Test if the multiplication works correctly",
                expexted, complex.multiply(actual));
    }

    @Test
    public void testSquareRoot() {
        Complex expexted = new Complex(2.0, 1.0);
        complex.setReal(3.0);
        complex.setImaginary(4.0);

        assertEquals("Test if the squareRoot works correctly",
                expexted, complex.squareRoot());
    }
    
    @Test
    public void testDivision() {
        Complex expected = new Complex((double) 45/29, (double) -40/29);
        Complex div = new Complex(2.0, 5.0);
        complex.setReal(10.0);
        complex.setImaginary(5.0);

        assertEquals("Test if the division works correctly",
                expected, complex.division(div)); // trying 10+5j/2+5j
    }
    
    @Test(expected = ArithmeticException.class)
    public void testDivisionException() {
        Complex div = new Complex(0.0, 0.0);
        complex.setReal(10.0);
        complex.setImaginary(5.0);

        complex.division(div);
    }
    
}
