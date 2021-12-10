package it.unisa.diem.Gruppo20.Model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;

/**
 * This class is an abstraction of the Complex set numbers. It supports
 * arithmetic and trigonometic operations.
 *
 * @author Team 20
 */
public class Complex {
    public static Complex ImaginaryUnit = new Complex(0d, 1d);
    private Double real;
    private Double imaginary;

    /**
     * This method returns a new Complex object with 0 as real and imaginary
     * values.
     */
    public Complex() {
        this.real = 0.0;
        this.imaginary = 0.0;
    }

    /**
     * This method return a new Complex object using param real and imaginary as
     * values.
     *
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
        DecimalFormat format = new DecimalFormat("0.########");
        format.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));

        if (real == 0 && imaginary == 0) {
            return "0";
        }
        if (real != 0) {
            s = format.format(real);
        }
        if (imaginary != 0) {
            if (imaginary > 0) {
                s += "+";
            }
            s = s + format.format(imaginary) + "j";
        }
        return s;
    }

    /**
     * This method implements the sum between two complex numbers.
     *
     * @param c Is the operand we want to make the sum with.
     * @return The complex number resulting from the operation.
     */
    public Complex plus(Complex c) {
        double a = c.getReal();
        double b = c.getImaginary();

        return new Complex(a + real, b + imaginary);
    }

    /**
     * This method implements the subtraction between two numbers.
     *
     * @param c The operand we want to subtract.
     * @return The complex number resulting from the operation.
     */
    public Complex minus(Complex c) {
        double a = c.getReal();
        double b = c.getImaginary();

        return new Complex(real - a, imaginary - b);
    }

    /**
     * This method implements the moltiplication between two complex numbers.
     *
     * @param c Is the operand we want to make the moltiplication with.
     * @return The complex number resulting from the operation.
     */
    public Complex multiply(Complex c) {
        double a = real;
        double b = imaginary;
        double c1 = c.real;
        double d = c.imaginary;

        double real = (a * c1 - b * d);
        double img = (a * d + b * c1);

        return new Complex(real, img);
    }

    /**
     * This method returns the ratio between this complex number and the param
     * c.
     *
     * @param c The dividend of the operation.
     * @return Complex value.
     */
    public Complex division(Complex c) {
        double a = real;
        double b = imaginary;
        double c1 = c.real;
        double d = c.imaginary;

        if (c1 == 0 && d == 0) {
            throw new ArithmeticException("Divider can't be 0.");
        }

        double div = c1 * c1 + d * d;
        double real = (a * c1 + b * d) / div;
        double img = (b * c1 - a * d) / div;

        return new Complex(real, img);
    }

    /**
     * This method implements the square root operation of a complex number. The
     * result value is a complex number.
     *
     * @return A complex number.
     */
    public Complex squareRoot() {
        DecimalFormat f = new DecimalFormat("0.##############E0");
        f.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));

        double r = Math.sqrt(mod());
        double phase = arg();

        return new Complex(r * cosApproximation(phase / 2), r * sinApproximation(phase / 2));
    }

    /**
     * This method returns the reverse of this Complex number.
     *
     * @return The complex number changed in sign.
     */
    public Complex invert() {
        return new Complex(-1 * real, -1 * imaginary);
    }

    /**
     * This method calculates the module of a complex number.
     *
     * @return A double value that representing the module.
     */
    public Double mod() {
        return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
    }

    /**
     * This method returns the phase of a complex number in (-pi, pi]. The value
     * is Undefined if the real and imaginary part are both equals to 0.
     *
     * @return A double value that representing the phase.
     */
    public Double arg() {
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
        } else {
            throw new ArithmeticException("The phase of 0 is undefined.");
        }
    }
    
    /**
     * Performs the sinh of this Complex number.
     * @return A Complex number represent the sinh of this Complex number.
     */
    private double sinh(Double x) {
       return (Math.exp(x) - Math.exp(-x))/2;
    }

    /**
     * Performs the cosh of this Complex number.
     * @return A Complex number represent the cosh of this Complex number.
     */
    private double cosh(Double x) {
       return (Math.exp(x) + Math.exp(-x))/2;
    }
    /**
     * Performs the cos of this Complex number.
     * @return A Complex number represent the cos of this Complex number.
     */
    public Complex cos() {
        if(this.imaginary==0)
            return new Complex(Math.cos(this.real),0d);
        else{
            Complex cos = new Complex();
            cos.setReal(Math.cos(this.real) * cosh(this.imaginary));
            cos.setImaginary(-Math.sin(this.real)* sinh(this.imaginary));
            return cos;
        }         
    }

    /**
     * Performs the arccos of this Complex number.
     * @return A Complex number represent the acos of this Complex number.
     */
    public Complex acos() {
        //acos(z) = pi/2 - asin(z)
        Complex halfPi = new Complex(Math.PI / 2, 0d);        
        return halfPi.minus(asin());
    }

    /**
     * Performs the sin of this Complex number.
     * @return A Complex number represent the sin of this Complex number.
     */
    public Complex sin() {
        if(this.imaginary==0)
            return new Complex(Math.sin(this.real),0.0);    
        else{
            Complex sin = new Complex();
            sin.setReal(Math.sin(this.real) * cosh(this.imaginary));
            sin.setImaginary(Math.cos(this.real)* sinh(this.imaginary));
            return sin;
        }  
    }

    /**
     * Performs the arcsin of this Complex number.
     * @return A Complex number represent the asin of this Complex number.
     */
    public Complex asin() {
        //asin(z) = j*ln(sqrt(1-z^2) - j*z)
        Complex one = new Complex(1d, 0d);
        Complex square = (one.minus(pow(2))).squareRoot();
        Complex log = (square.minus(ImaginaryUnit.multiply(this))).log();
        return ImaginaryUnit.multiply(log);
    }

    /**
     * Performs the tan of this Complex number.
     * @return A Complex number represent the tan of this Complex number.
     */
    public Complex tan() {
        return new Complex(this.real,this.imaginary).sin().division(new Complex(this.real,this.imaginary).cos());
    }

    /**
     * Performs the arctan of this Complex number.
     * @return A Complex number represent the atan of this Complex number.
     */
    public Complex atan() {
        //atan(z) = -j/2*ln((1+jz)/(1-jz))
        Complex one = new Complex(1d, 0d);
        Complex halfImg = new Complex(0d, -0.5);
        Complex log = multiply(ImaginaryUnit).plus(one).division(one.minus(multiply(ImaginaryUnit))).log();
        return halfImg.multiply(log);
    }

    /**
     * Calculates the power of a specific degree 'n' of the complex number.
     *
     * @param grade The degree of the exponent of the power.
     * @return A complex number.
     */
    public Complex pow(double grade) {        
        if (real == 0 && imaginary == 0) {
            if(grade == 0)
                throw new ArithmeticException("Indefinite value. Unable to execute.");
            return this;
        }
        
        if (grade == 0) {
            return new Complex(1d, 0d);
        }
        if (grade == 1) {
            return this;
        }
        if (imaginary == 0) {
            return new Complex(Math.pow(real, grade), 0d);
        }
        double r = Math.pow(mod(), grade);
        double arg = grade * arg();
        
        return new Complex(r * cosApproximation(arg), r * sinApproximation(arg));
    }

    /**
     *
     * @return
     */
    public Complex exp() {
        return null;
    }

    /**
     * Performs the natural logarithm of a complex number.
     *
     * @return A complex number.
     * @throws ArithmeticException if the imaginary is 0 and the real part
     * negative or if real and imaginary part are 0.
     */
    public Complex log() {
        if (real == 0 && imaginary == 0) {
            throw new ArithmeticException("Impossible to perform the log on this complex number.");
        }
        return new Complex(Math.log(mod()), arg());
    }
    
    private Double cosApproximation(double phase) {
        DecimalFormat f = new DecimalFormat("0.##############E0");
        f.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));
        return (Double.valueOf(f.format((Math.cos(phase) + 1))) - 1);
    }
    
    private Double sinApproximation(double phase) {
        DecimalFormat f = new DecimalFormat("0.##############E0");
        f.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));
        return (Double.valueOf(f.format((Math.sin(phase) + 1))) - 1);
    }

}
