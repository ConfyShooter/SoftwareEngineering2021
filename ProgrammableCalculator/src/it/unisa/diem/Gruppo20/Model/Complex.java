package it.unisa.diem.Gruppo20.Model;

import java.util.Objects;

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
        String s = "";
        if(real==0 && imaginary==0)
            return "0";
        
        if(real!=0) {
            s = String.format("%.2f", real);
            if(imaginary < 0)
                s +=" - ";
            else if (imaginary > 0)
                s += " + ";
        }
        
        if(imaginary!=0)
            s = s + String.format("%.2f", Math.abs(imaginary)) + "j";
        
        return s;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.real);
        hash = 59 * hash + Objects.hashCode(this.imaginary);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Complex other = (Complex) obj;
        if (!Objects.equals(this.real, other.real)) {
            return false;
        }
        if (!Objects.equals(this.imaginary, other.imaginary)) {
            return false;
        }
        return true;
    }

    /**
     * This function return the ratio between this complex number and the param
     * c.
     *
     * @param c the dividend of the operation
     * @return Complex value
     */
    public Complex division(Complex c) throws ArithmeticException {
        Double a = this.real;
        Double b = this.imaginary;
        Double c1 = c.real;
        Double d = c.imaginary;
        if (c1 == 0 && d == 0) {
            throw new ArithmeticException("Complex number can't be 0.");
        }
        Double div = c1 * c1 + d * d;
        Double real = (a * c1 + b * d) / div;
        Double img = (b * c1 - a * d) / div;
        return new Complex(real, img);
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
