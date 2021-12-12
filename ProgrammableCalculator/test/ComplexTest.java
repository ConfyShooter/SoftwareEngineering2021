
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
    public void testArg() {
        operand1.setReal(0.0);
        operand1.setImaginary(1.0);
        assertEquals(Math.PI / 2, operand1.arg(), 0.00000001);

        operand1.setImaginary(-1.0);
        assertEquals(-Math.PI / 2, operand1.arg(), 0.00000001);

        operand1.setReal(1.0);
        assertEquals(Math.atan(operand1.getImaginary() / operand1.getReal()), operand1.arg(), 0.00000001);

        operand1.setReal(-1.0);
        operand1.setImaginary(0.0);
        assertEquals(Math.atan(operand1.getImaginary() / operand1.getReal()) + Math.PI, operand1.arg(), 0.00000001);

        operand1.setImaginary(-1.0);
        assertEquals(Math.atan(operand1.getImaginary() / operand1.getReal()) - Math.PI, operand1.arg(), 0.00000001);
    }

    @Test(expected = ArithmeticException.class)
    public void testArgException() {
        expected.arg();
    }

    @Test
    public void testCos() {
        assertComplexEquals(expected, new Complex(Math.PI / 2, 0d));
        
        expected.setReal(1d);
        assertComplexEquals(expected,zero.cos());
        
        operand1Real.setReal(Math.PI);
        expected.setReal(-1d);
        assertComplexEquals(expected, operand1Real.cos());
        
        expected.setReal(Math.cos(operand1Imaginary.getReal()) * Math.cosh(operand1Imaginary.getImaginary()));
        expected.setImaginary(-Math.sin(operand1Imaginary.getReal()) * Math.sinh(operand1Imaginary.getImaginary()));
        assertComplexEquals(expected, operand1Imaginary.cos());
        
        operand1.setReal(-3d);
        operand1.setImaginary(-45d);
        expected.setReal(Math.cos(operand1.getReal()) * Math.cosh(operand1.getImaginary()));
        expected.setImaginary(-Math.sin(operand1.getReal()) * Math.sinh(operand1.getImaginary()));
        assertComplexEquals(expected, operand1.cos());
    }

    @Test
    public void testAcos() {
        Complex halfPi = new Complex(Math.PI / 2, 0d);

        expected.setReal(halfPi.getReal());
        assertComplexEquals(expected, zero.acos());

        operand2Real.setReal(Math.sqrt(99) + 10);
        Complex asin = operand2Real.log().plus(Complex.ImaginaryUnit.log()).multiply(Complex.ImaginaryUnit);
        expected = halfPi.minus(asin);
        assertComplexEquals(expected, operand1Real.acos());

        operand2Real.setReal(Math.sqrt(2501) - 50);
        asin = operand2Real.log().multiply(Complex.ImaginaryUnit);
        expected = halfPi.minus(asin);
        assertComplexEquals(expected, operand1Imaginary.acos());

        operand2 = new Complex(8d, -24d).squareRoot().minus(new Complex(-4d, 3d));
        expected = halfPi.minus(Complex.ImaginaryUnit.multiply(operand2.log()));
        assertComplexEquals(expected, operand1.acos());

    }

    @Test
    public void testSin() {
        assertComplexEquals(expected, zero.sin());
        
        operand1Real.setReal(Math.PI * 2);
        assertComplexEquals(expected, operand1Real.sin());
        

        expected.setReal(0d);
        expected.setImaginary(Math.cos(operand1Imaginary.getReal()) * Math.sinh(operand1Imaginary.getImaginary()));
        assertComplexEquals(expected, operand1Imaginary.sin());
        
        operand1.setReal(-3d);
        operand1.setImaginary(-45d);
        expected.setReal(Math.sin(operand1.getReal()) * Math.cosh(operand1.getImaginary()));
        expected.setImaginary(Math.cos(operand1.getReal()) * Math.sinh(operand1.getImaginary()));
        assertComplexEquals(expected, operand1.sin());
    }

    @Test
    public void testAsin() {
        assertComplexEquals(expected, zero.asin());

        operand2Real.setReal(Math.sqrt(99) + 10);
        expected = operand2Real.log().plus(Complex.ImaginaryUnit.log()).multiply(Complex.ImaginaryUnit);
        assertComplexEquals(expected, operand1Real.asin());

        operand2Real.setReal(Math.sqrt(2501) - 50);
        expected = operand2Real.log().multiply(Complex.ImaginaryUnit);
        assertComplexEquals(expected, operand1Imaginary.asin());

        operand2 = new Complex(8d, -24d).squareRoot().minus(new Complex(-4d, 3d));
        expected = Complex.ImaginaryUnit.multiply(operand2.log());
        assertComplexEquals(expected, operand1.asin());

    }

    @Test()
    public void testTanException() {
        new Complex(Math.PI / 2, 0d).tan();
    }

    @Test
    public void testTan() {
        assertComplexEquals(expected, zero.tan());
        
        operand1Real.setReal(Math.PI);
        expected = operand1Real.sin().division(operand1Real.cos());
        assertComplexEquals(expected, operand1Real.tan());
        
        expected = operand1Imaginary.sin().division(operand1Imaginary.cos());
        assertComplexEquals(expected, operand1Imaginary.tan());
        
        operand1.setReal(-3d);
        operand1.setImaginary(-45d);
        expected = operand1.sin().division(operand1.cos());
        assertComplexEquals(expected, operand1.tan());
    }

    @Test
    public void testAtan() {
        Complex halfImg = new Complex(0d, -0.5);
        assertComplexEquals(expected, zero.atan());

        operand2Real.setReal(101d);
        operand2.setReal(-99d);
        operand2.setImaginary(-20d);
        Complex log = operand2.division(operand2Real).log();
        expected = halfImg.multiply(log);
        assertComplexEquals(expected, operand1Real.atan());

        operand1Real.setReal((double) -51 / 49);
        expected = halfImg.multiply(operand1Real.log());
        assertComplexEquals(expected, operand1Imaginary.atan());

        operand2.setReal(-24d);
        operand2.setImaginary(6d);
        operand1Real.setReal(34d);
        log = operand2.division(operand1Real).log();
        expected = halfImg.multiply(log);
        assertComplexEquals(expected, operand1.atan());

    }

    @Test(expected = ArithmeticException.class)
    public void testPowException() {
        zero.pow(0);
    }

    @Test
    public void testPow() {
        assertComplexEquals(operand1, operand1.pow(1));

        assertComplexEquals(expected, new Complex(0d, 0d).pow(2));

        expected.setReal(1d);
        assertComplexEquals(expected, operand1.pow(0));

        expected.setImaginary(24d);
        expected.setReal(-7d);
        assertComplexEquals(expected, operand1.pow(2));

        expected.setImaginary(44d);
        expected.setReal(-117d);
        assertComplexEquals(expected, operand1.pow(3));

        expected.setReal(0d);
        expected.setImaginary(125000d);
        assertComplexEquals(expected, operand1Imaginary.pow(3));

        expected.setReal(-2500d);
        expected.setImaginary(0d);
        assertComplexEquals(expected, operand1Imaginary.pow(2));

        expected.setReal(100d);
        expected.setImaginary(0d);
        assertComplexEquals(expected, operand1Real.pow(2));

        expected.setReal(8.99);
        expected.setImaginary(0.6);
        assertComplexEquals(expected, operand2.pow(2));

        expected.setReal(26.91);
        expected.setImaginary(2.699);
        assertComplexEquals(expected, operand2.pow(3));

        expected.setReal(-3600d);
        expected.setImaginary(0d);
        assertComplexEquals(expected, operand2Imaginary.pow(2));

        expected.setReal(216d);
        expected.setImaginary(0d);
        assertComplexEquals(expected, operand2Real.pow(3));
    }

    @Test
    public void testExp() {
        expected.setReal(1d);
        assertComplexEquals(expected, zero.exp());

        expected.setReal(0.00004539);
        assertComplexEquals(expected, operand1Real.exp());

        expected.setReal(403.42879349);
        assertComplexEquals(expected, operand2Real.exp());

        expected.setReal(0.96496603);
        expected.setImaginary(0.26237485);
        assertComplexEquals(expected, operand1Imaginary.exp());

        expected.setReal(-0.95241298);
        expected.setImaginary(-0.30481062);
        assertComplexEquals(expected, operand2Imaginary.exp());

        expected.setReal(-13.12878308);
        expected.setImaginary(-15.20078446);
        assertComplexEquals(expected, operand1.exp());

        expected.setReal(0.04953834);
        expected.setImaginary(-0.00497041);
        assertComplexEquals(expected, operand2.invert().exp());
    }

    @Test(expected = ArithmeticException.class)
    public void testLogException() {
        zero.log();
    }

    @Test
    public void testLog() {
        expected.setReal(2.30258509);
        expected.setImaginary(3.14159265);
        assertComplexEquals(expected, operand1Real.log());

        expected.setReal(3.9120230054);
        expected.setImaginary(-1.57079632679);
        assertComplexEquals(expected, operand1Imaginary.log());

        expected.setReal(1.60943791243);
        expected.setImaginary(0.927295218);
        assertComplexEquals(expected, operand1.log());

        expected.setReal(1.0991675358);
        expected.setImaginary(0.033320995878);
        assertComplexEquals(expected, operand2.log());

        expected.setReal(4.094344562222);
        expected.setImaginary(1.57079632679);
        assertComplexEquals(expected, operand2Imaginary.log());

        expected.setReal(1.7917594692);
        expected.setImaginary(0d);
        assertComplexEquals(expected, operand2Real.log());
    }

    private void assertComplexEquals(Complex expected, Complex actual) {
        assertEquals(expected.getReal(), actual.getReal(), 0.00000001);
        assertEquals(expected.getImaginary(), actual.getImaginary(), 0.00000001);
    }

}
