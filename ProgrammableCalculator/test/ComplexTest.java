
import it.unisa.diem.Gruppo20.Model.Complex;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Team 20
 */
public class ComplexTest {
    /**
     * Number 0.
     */
    private Complex zero;
    private Complex expected;
    /**
     * Real number -10.
     */
    private Complex operand1Real;
    /**
     * Imaginary number -50j.
     */
    private Complex operand1Imaginary;
    /**
     * Real number 6.
     */
    private Complex operand2Real;
    /**
     * Imaginary number 60j.
     */
    private Complex operand2Imaginary;
    /**
     * Complex number 3+4j.
     */
    private Complex operand1;
    /**
     * Complex number 3+0.1j.
     */
    private Complex operand2;

    public ComplexTest() {
    }

    @Before
    public void setUp() {
        zero = new Complex();
        expected = new Complex();
        operand1Real = new Complex(-10.0, 0.0);
        operand2Real = new Complex(6.0, 0.0);
        operand1Imaginary = new Complex(0.0, -50.0);
        operand2Imaginary = new Complex(0.0, 60.0);
        operand1 = new Complex(3.0, 4.0);
        operand2 = new Complex(3.0, 0.1);
    }

    @Test
    public void testPlus() {
        assertComplexEquals(expected, zero.plus(zero)); //test 0+0 = 0
        
        expected.setReal(-4d);
        assertComplexEquals(expected, operand1Real.plus(operand2Real));
        
        expected.setReal(0d);
        expected.setImaginary(10d);
        assertComplexEquals(expected, operand1Imaginary.plus(operand2Imaginary));
        
        expected.setReal(-10d);
        expected.setImaginary(60d);
        assertComplexEquals(expected, operand1Real.plus(operand2Imaginary));
        
        expected.setReal(6d);
        expected.setImaginary(4.1);
        assertComplexEquals(expected, operand1.plus(operand2));
        
    }

    @Test
    public void testMinus() {
        assertComplexEquals(expected, zero.minus(zero));
        
        expected.setReal(-16.0);
        assertComplexEquals(expected, operand1Real.minus(operand2Real));

        expected.setImaginary(-110.0);
        expected.setReal(0.0);
        assertComplexEquals(expected, operand1Imaginary.minus(operand2Imaginary));
        
        expected.setImaginary(-50d);
        expected.setReal(-6d);
        assertComplexEquals(expected, operand1Imaginary.minus(operand2Real));
        
        expected.setImaginary(3.9);
        expected.setReal(0.0);
        assertComplexEquals(expected, operand1.minus(operand2));
    }

    @Test
    public void testMultiply() {
        assertComplexEquals(expected, zero.multiply(zero));
        
        expected.setReal(-60.0);
        assertComplexEquals(expected, operand1Real.multiply(operand2Real));
        
        expected.setReal(3000.0);
        expected.setImaginary(0.0);
        assertComplexEquals(expected, operand1Imaginary.multiply(operand2Imaginary));
        
        expected.setReal(0d);
        expected.setImaginary(-600d);
        assertComplexEquals(expected, operand1Real.multiply(operand2Imaginary));

        expected.setReal((double) 39 / 4);
        expected.setImaginary((double) 13 / 4);
        operand1.setReal(0.5);
        operand1.setImaginary((double) 5 / 2);
        operand2.setReal(2.0);
        operand2.setImaginary((double) -7 / 2);

        assertComplexEquals(expected, operand1.multiply(operand2));

    }

    @Test
    public void testSquareRoot() {
        expected.setImaginary(Math.sqrt(10));
        assertComplexEquals(expected, operand1Real.squareRoot());
        
        expected.setImaginary(-5.0);
        expected.setReal(5.0);
        assertComplexEquals(expected, operand1Imaginary.squareRoot());

        expected.setReal(2.0);
        expected.setImaginary(1.0);
        assertComplexEquals(expected, operand1.squareRoot());
    }

    @Test
    public void testMod() {
        Double expected = 0.0;
        assertEquals(expected, zero.mod(), 0.00000001);
        
        expected = 10.0;
        assertEquals(expected, operand1Real.mod(), 0.0000001);
        
        expected = 60.0;
        assertEquals(expected, operand2Imaginary.mod(), 0.0000001);
        
        
        operand1.setReal(-10.64);
        operand1.setImaginary(30.12);
        expected = 31.94407613;

        assertEquals(expected, operand1.mod(), 0.00000001);
    }

    @Test
    public void testPhase() {
        operand1.setReal(0.0);
        operand1.setImaginary(1.0);
        assertEquals(Math.PI / 2, operand1.phase(), 0.00000001);

        operand1.setImaginary(-1.0);
        assertEquals(-Math.PI / 2, operand1.phase(), 0.00000001);

        operand1.setReal(1.0);
        assertEquals(Math.atan(operand1.getImaginary() / operand1.getReal()), operand1.phase(), 0.00000001);

        operand1.setReal(-1.0);
        operand1.setImaginary(0.0);
        assertEquals(Math.atan(operand1.getImaginary() / operand1.getReal()) + Math.PI, operand1.phase(), 0.00000001);

        operand1.setImaginary(-1.0);
        assertEquals(Math.atan(operand1.getImaginary() / operand1.getReal()) - Math.PI, operand1.phase(), 0.00000001);
    }

    @Test(expected = ArithmeticException.class)
    public void testPhaseException() {
        expected.squareRoot();
    }

    @Test
    public void testDivision() {
        expected.setReal((double) -1 * 5 / 3);
        assertComplexEquals(expected, operand1Real.division(operand2Real));
        
        expected.setReal((double) -1 * 5 / 6);
        assertComplexEquals(expected, operand1Imaginary.division(operand2Imaginary));
        
        expected.setReal(0d);
        expected.setImaginary((double) 1 / 6);
        assertComplexEquals(expected, operand1Real.division(operand2Imaginary));
        
        expected.setReal((double) 45 / 29);
        expected.setImaginary((double) -40 / 29);
        operand1.setReal(10.0);
        operand1.setImaginary(5.0);
        operand2.setReal(2.0);
        operand2.setImaginary(5.0);
        assertComplexEquals(expected, operand1.division(operand2));
    }

    @Test(expected = ArithmeticException.class)
    public void testDivisionException() {
        operand1.division(expected);
    }

    @Test
    public void testInvert() {
        assertComplexEquals(expected, expected.invert());
        
        expected.setReal(10.0);
        assertComplexEquals(expected, operand1Real.invert());
        
        expected.setReal(-6.0);
        assertComplexEquals(expected, operand2Real.invert());
        
        expected.setReal(0.0);
        expected.setImaginary(50.0);
        assertComplexEquals(expected, operand1Imaginary.invert());
        
        expected.setImaginary(-60.0);
        assertComplexEquals(expected, operand2Imaginary.invert());
        
        expected.setReal(-3.0);
        expected.setImaginary(-4.0);
        assertComplexEquals(expected, operand1.invert());
        
    }

    private void assertComplexEquals(Complex expected, Complex actual) {
        Assert.assertEquals(expected.getReal(), actual.getReal(), 0.00000001);
        Assert.assertEquals(expected.getImaginary(), actual.getImaginary(), 0.00000001);
    }

}
