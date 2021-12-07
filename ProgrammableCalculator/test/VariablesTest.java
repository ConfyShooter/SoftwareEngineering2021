
import it.unisa.diem.Gruppo20.Model.Variables;

import it.unisa.diem.Gruppo20.Model.Complex;
import it.unisa.diem.Gruppo20.Model.Exception.VariableKeyException;
import java.util.NoSuchElementException;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Team 20
 */
public class VariablesTest {

    private Complex expected;
    private Variables v;
    private Complex number;

    public VariablesTest() {
    }

    @Before
    public void setUp() {
        v = new Variables();
        expected = new Complex();
        number = new Complex((double) 1, (double) 2);
        v.setVariable('j', number);
        v.setVariable('m', number.multiply(number));
        v.setVariable('p', number.plus(number));
    }

    @Test(expected = VariableKeyException.class)
    public void testGetVariableExceptionSx() {
        v.getVariable('_');
    }

    @Test(expected = VariableKeyException.class)
    public void testGetVariableExceptionDx() {
        v.getVariable('{');
    }

    @Test(expected = VariableKeyException.class)
    public void testGetVariableExceptionValue() {
        v.getVariable('a');
    }

    @Test
    public void testGetVariable() {
        assertComplexEquals(number, v.getVariable('j'));
        assertComplexEquals(number.multiply(number), v.getVariable('m'));
        assertComplexEquals(number.plus(number), v.getVariable('p'));
    }

    @Test(expected = VariableKeyException.class)
    public void testSetVariableExceptionSx() {
        v.setVariable('_', number);
    }

    @Test(expected = VariableKeyException.class)
    public void testSetVariableExceptionDx() {
        v.setVariable('{', number);
    }

    @Test
    public void testSetVariable() {
        v.setVariable('m', number.minus(number));
        assertComplexEquals(new Complex(0.0, 0.0), v.getVariable('m'));
    }

    @Test(expected = VariableKeyException.class)
    public void testSumVariableExceptionSx() {
        v.sumVariable('_', number);
    }

    @Test(expected = VariableKeyException.class)
    public void testSumVariableExceptionDx() {
        v.sumVariable('{', number);
    }

    @Test
    public void testSumVariable() {
        v.sumVariable('j', number);
        assertComplexEquals(v.getVariable('p'), v.getVariable('j'));
    }

    @Test(expected = VariableKeyException.class)
    public void testSubVariableExceptionSx() {
        v.subVariable('_', number);
    }

    @Test(expected = VariableKeyException.class)
    public void testSubVariableExceptionDx() {
        v.subVariable('{', number);
    }

    @Test
    public void testSubVariable() {
        v.subVariable('j', number);
        assertComplexEquals(new Complex(), v.getVariable('j'));
    }

    @Test
    public void testBackup() {
        v.backup();

        Complex result = v.getBackupsStack().peekFirst().get('j');
        expected.setReal(1.0);
        expected.setImaginary(2.0);

        assertComplexEquals(expected, result);
    }

    @Test(expected = NoSuchElementException.class)
    public void testBackupException() {
        v.getCurrentValues().clear();
        v.backup();
    }

    @Test
    public void testRestore() {
        v.backup();
        v.setVariable('j', new Complex(2.0, 3.0));
        Complex result = v.getVariable('j');

        expected.setReal(2.0);
        expected.setImaginary(3.0);
        assertComplexEquals(expected, result);

        v.restore();
        result = v.getVariable('j');
        expected.setReal(1.0);
        expected.setImaginary(2.0);
        assertComplexEquals(expected, result);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRestoreException() {
        v.restore();
    }

    private void assertComplexEquals(Complex expected, Complex actual) {
        assertEquals(expected.getReal(), actual.getReal(), 0.00000001);
        assertEquals(expected.getImaginary(), actual.getImaginary(), 0.00000001);
    }

}
