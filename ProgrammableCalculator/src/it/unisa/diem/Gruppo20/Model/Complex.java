package it.unisa.diem.Gruppo20.Model;

import java.util.Objects;

/**
 * This class is an abstraction of the Complex set numbers.
 * It supports arithmetic and trigonometic operations.
 * @author Gruppo 20
 */
public class Complex {

    private Double real;
    private Double imaginary;

    /**
     * This method return a new Complex object with default real and imaginary values.
     */
    public Complex() {
        this.real = 0.0;
        this.imaginary = 0.0;
    }

    /**
     * This method return a new Complex object using param real and imaginary as values.
     * @param real The real value of this new Complex.
     * @param imaginary The imaginary of this new Complex.
     */
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
        if (!this.real.equals(other.real)) {
            return false;
        }
        if (!this.imaginary.equals(other.imaginary)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String s = "";
        if (real == 0.0 && imaginary == 0.0) {
            return "0";
        }

        if (real != 0) {
            s = String.format("%.2f", real);
        }

        if (imaginary != 0) {
            if (imaginary > 0) {
                s += "+";
            }
            s = s + String.format("%.2f", imaginary) + "j";
        }

        return s;
    }

    /**
     * This function implements the sum between two complex number.
     *
     * @param c is the operand we want to make the sum with.
     * @return the complex number resulting from the operation.
     */
    public Complex plus(Complex c) {
        Double a = c.getReal();
        Double b = c.getImaginary();

        return new Complex(a + real, b + imaginary);
    }

    /**
     * This function implements the the subtraction between two numbers
     * @param c the operand we want to subtract. 
     * @return the complex number resulting from the operation.
     */
    public Complex minus(Complex c) {
        Double a = c.getReal();
        Double b = c.getImaginary();
        
        return new Complex(real - a, imaginary - b);
    }

    /**
     * This function implements the moltiplication between two complex number.
     *
     * @param c is the operand we want to make the moltiplication with.
     * @return the complex number resulting from the operation.
     */
    public Complex multiply(Complex c) {
        Double a = real;
        Double b = imaginary;
        Double c1 = c.real;
        Double d = c.imaginary;
        
        Double real = (a * c1 - b * d);
        Double img = (a * d + b * c1);
        
        return new Complex(real, img);
    }

    /**
     * This function return the ratio between this complex number and the param
     * c.
     *
     * @param c the dividend of the operation
     * @return Complex value
     */
    public Complex division(Complex c) throws ArithmeticException {
        Double a = real;
        Double b = imaginary;
        Double c1 = c.real;
        Double d = c.imaginary;
        
        if (c1 == 0 && d == 0)
            throw new ArithmeticException("Divider can't be 0.");
        
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
        Double module = mod();
        Double phase = phase();

        Double r = Math.sqrt(module);
        Double real = r * Math.cos((phase / 2));
        Double img = r * Math.sin((phase / 2));

        return new Complex(real, img);
    }

    /**
     *This function returns the reverse of this Complex number.
     * @return the complex changed in sign.
     */
    public Complex invert() {
        return new Complex(- real, - imaginary);  
    }

    /**
     * This method calculate the module of a complex number.
     *
     * @return the module of this Complex.
     */
    public Double mod() {
        return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
    }

    /**
     * This method return the phase of a complex number in (-pi, pi].
     * The value is Undefined if the real and imaginary part are both equals to 0.
     *
     * @return the phase.
     */
    public Double phase() {
        if (real == 0 && imaginary > 0)
            return Math.PI / 2;
        else if (real == 0 && imaginary < 0)
            return - Math.PI / 2;
        else if (real > 0)
            return Math.atan(imaginary / real);
        else if (real < 0 && imaginary >= 0)
            return Math.atan(imaginary / real) + Math.PI;
        else if (real < 0 && imaginary < 0)
            return Math.atan(imaginary / real) - Math.PI;
        else
            throw new ArithmeticException("The phase of 0 is undefined.");
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
