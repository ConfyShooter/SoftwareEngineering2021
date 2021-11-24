
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
        this.complex = new Complex();
    }

    @Test
    public void testPlus() {
        Complex expexted = new Complex(5.0, 10.1);
        Complex actual = new Complex(2.0, 10.0);

        complex.setReal(3.0);
        complex.setImaginary(0.1);

        actual = complex.plus(actual);

        check(expexted, actual);
    }

    @Test(expected = ArithmeticException.class)
    public void testSquareRootException() {
        complex.setReal(Double.POSITIVE_INFINITY);
        complex.setImaginary(-20.0);
        complex.squareRoot();

        complex.setReal(1.0);
        complex.setImaginary(Double.NaN);
        complex.squareRoot();
    }

    @Test
    public void testSquareRoot() {
        Complex expexted = new Complex(2.0, 1.0);
        Complex actual = new Complex(3.0, 4.0);

        complex = actual.squareRoot();

        check(expexted, complex);
    }

    private void check(Complex expexted, Complex actual) {
        assertEquals(expexted.getReal(), actual.getReal(), 0.000001);
        assertEquals(expexted.getImaginary(), actual.getImaginary(), 0.000001);
    }

    @Test
    public void testMod() {
        complex.setReal(-10.64);
        complex.setImaginary(30.12);
        Double expected = 31.94407613;

        assertEquals(expected, complex.mod(), 0.00000001);
    }

    @Test
    public void testPhase() {
        complex.setReal(0.0);
        complex.setImaginary(0.0);
        assertEquals(Double.NaN, complex.phase(), 0.0000001);

        complex.setImaginary(1.0);
        assertEquals(Math.PI / 2, complex.phase(), 0.0000001);

        complex.setImaginary(-1.0);
        assertEquals(-Math.PI / 2, complex.phase(), 0.0000001);

        complex.setReal(1.0);
        assertEquals(Math.atan(complex.getImaginary() / complex.getReal()), complex.phase(), 0.0000001);

        complex.setReal(-1.0);
        complex.setImaginary(0.0);
        assertEquals(Math.atan(complex.getImaginary() / complex.getReal()) + Math.PI, complex.phase(), 0.0000001);

        complex.setImaginary(-1.0);
        assertEquals(Math.atan(complex.getImaginary() / complex.getReal()) - Math.PI, complex.phase(), 0.0000001);
    }

    @Test
    public void testDivision() {
        Complex expected = new Complex((double) 45 / 29, (double) -40 / 29);
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

    @Test
    public void testMinus() {
        Complex expexted = new Complex(10.0, 0.0);
        Complex min = new Complex(4.0, 5.0);
        complex.setReal(14.0);
        complex.setImaginary(5.0);
        min = complex.minus(min);
        assertEquals(expexted.toString(), min.toString());

    }
    
    @Test
    public void testInvert() {
        Complex expexted = new Complex(5.0, 10.0);
        Complex inv = new Complex(5.0, -10.0);
        inv = inv.invert();
        assertEquals(expexted.toString(), inv.toString());
    }
    
}
