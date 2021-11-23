package it.unisa.diem.Gruppo20.Model;

/**
 *
 * @author Gruppo 20
 */
public class Complex {

    private Double real;
    private Double imaginary;

    public Complex() {
        this.real = Double.NaN;
        this.imaginary = Double.NaN;
    }

    public Complex(Double real, Double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public Double getReal() {
        return real;
    }

    public void setReal(Double real) {
        this.real = real;
    }

    public Double getImaginary() {
        return imaginary;
    }

    public void setImaginary(Double imaginary) {
        this.imaginary = imaginary;
    }

    @Override
    public String toString() {
        return real.toString() + imaginary.toString();
    }

    /**
     * This function implements the sum between two complex number.
     *
     * @param c is the operand we want to make the sum with.
     * @return the complex number resulting from the operation.
     */
    public Complex plus(Complex c) {
        Double cReal = c.getReal();
        Double cImg = c.getImaginary();

        Complex result = new Complex();
        result.setReal(cReal + real);
        result.setImaginary(cImg + imaginary);

        return result;
    }

    /**
     *
     * @param c
     * @return
     */
    public Complex minus(Complex c) {
        return null;
    }

    /**
     *
     * @param c
     * @return
     */
    public Complex multiply(Complex c) {
        return null;
    }

    /**
     *
     * @param c
     * @return
     */
    public Complex division(Complex c) {
        return null;
    }

    /**
     * This function implements the square root operation of a complex
     * number.The result value is a complex numbers pair where one is the
     * opposite of the other.
     *
     * @return a list made up of the two result of the square root.
     */
    public Complex squareRoot() {
        Double module = this.mod();
        Double phase = this.phase();

        Complex result = new Complex();

        Double r = Math.sqrt(module);

        result.setReal(r * Math.cos((phase / 2)));
        result.setImaginary(r * Math.sin((phase / 2)));

        return result;
    }

    /**
     *
     * @return
     */
    public Complex invert() {
        return null;
    }

    /**
     *
     * @return
     */
    public Double mod() {
        return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
    }

    /**
     *
     * @return
     */
    public Double phase() {
        if (real == 0 && imaginary > 0) {
            return Math.PI / 2;
        } else if (real == 0 && imaginary < 0) {
            return -Math.PI / 2;
        } else if (real > 0) {
            return Math.atan(imaginary / real);
        } else if (real < 0 && imaginary >= 0) {
            return Math.atan(imaginary / real) + Math.PI;
        } else if (real < 0 && imaginary < 0) {
            return Math.atan(imaginary / real) - Math.PI;
        }
        return Double.NaN;
    }

    /**
     *
     * @return
     */
    public Complex cos() {
        return null;
    }

    /**
     *
     * @return
     */
    public Complex acos() {
        return null;
    }

    /**
     *
     * @return
     */
    public Complex sin() {
        return null;
    }

    /**
     *
     * @return
     */
    public Complex asin() {
        return null;
    }

    /**
     *
     * @return
     */
    public Complex tan() {
        return null;
    }

    /**
     *
     * @return
     */
    public Complex atan() {
        return null;
    }

    /**
     *
     * @return
     */
    public Complex pow() {
        return null;
    }

    /**
     *
     * @return
     */
    public Complex exp() {
        return null;
    }

    /**
     *
     * @return
     */
    public Complex log() {
        return null;
    }

}
