
import it.unisa.diem.Gruppo20.Model.Calculator;
import it.unisa.diem.Gruppo20.Model.Command;
import it.unisa.diem.Gruppo20.Model.Complex;
import it.unisa.diem.Gruppo20.Model.Exception.ParseException;
import it.unisa.diem.Gruppo20.Model.StandardOperations;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Team 20
 */
public class StandardOperationsTest {

    private Command actual;
    private Complex number;
    private StandardOperations standardOp;
    private Calculator c;

    public StandardOperationsTest() {
    }

    @Before
    public void setUp() throws Exception {
        c = new Calculator();
        number = new Complex();
        standardOp = new StandardOperations(c);
    }

    @Test
    public void testIsAStandardOperation() {
        assertTrue(standardOp.isAStandardOperation("+"));
        assertTrue(standardOp.isAStandardOperation("-"));
        assertTrue(standardOp.isAStandardOperation("*"));
        assertTrue(standardOp.isAStandardOperation("/"));
        assertTrue(standardOp.isAStandardOperation("+-"));
        assertTrue(standardOp.isAStandardOperation("sqrt"));
        assertTrue(standardOp.isAStandardOperation("clear"));
        assertTrue(standardOp.isAStandardOperation("dup"));
        assertTrue(standardOp.isAStandardOperation("drop"));
        assertTrue(standardOp.isAStandardOperation("over"));
        assertTrue(standardOp.isAStandardOperation("swap"));
        assertTrue(standardOp.isAStandardOperation("mod"));
        assertTrue(standardOp.isAStandardOperation("arg"));
        assertTrue(standardOp.isAStandardOperation("cos"));
        assertTrue(standardOp.isAStandardOperation("sin"));
        assertTrue(standardOp.isAStandardOperation("tan"));
        assertTrue(standardOp.isAStandardOperation("acos"));
        assertTrue(standardOp.isAStandardOperation("asin"));
        assertTrue(standardOp.isAStandardOperation("atan"));
        assertTrue(standardOp.isAStandardOperation("pow"));
        assertTrue(standardOp.isAStandardOperation("exp"));
        assertTrue(standardOp.isAStandardOperation("log"));
        assertTrue(standardOp.isAStandardOperation("save"));
        assertTrue(standardOp.isAStandardOperation("restore"));
        assertTrue(standardOp.isAStandardOperation("<a"));
        assertTrue(standardOp.isAStandardOperation(">a"));
        assertTrue(standardOp.isAStandardOperation("+a"));
        assertTrue(standardOp.isAStandardOperation("-a"));
        assertTrue(standardOp.isAStandardOperation("<j"));
        assertTrue(standardOp.isAStandardOperation(">j"));
        assertTrue(standardOp.isAStandardOperation("+j"));
        assertTrue(standardOp.isAStandardOperation("-j"));
        assertTrue(standardOp.isAStandardOperation("<z"));
        assertTrue(standardOp.isAStandardOperation(">z"));
        assertTrue(standardOp.isAStandardOperation("+z"));
        assertTrue(standardOp.isAStandardOperation("-z"));

        char ch = 'z' + 1;
        assertFalse(standardOp.isAStandardOperation("<" + String.valueOf(ch)));
        assertFalse(standardOp.isAStandardOperation(">" + String.valueOf(ch)));
        assertFalse(standardOp.isAStandardOperation("+" + String.valueOf(ch)));
        assertFalse(standardOp.isAStandardOperation("-" + String.valueOf(ch)));

        ch = 'a' - 1;
        assertFalse(standardOp.isAStandardOperation("<" + String.valueOf(ch)));
        assertFalse(standardOp.isAStandardOperation(">" + String.valueOf(ch)));
        assertFalse(standardOp.isAStandardOperation("+" + String.valueOf(ch)));
        assertFalse(standardOp.isAStandardOperation("-" + String.valueOf(ch)));
        assertFalse(standardOp.isAStandardOperation("test"));
        assertFalse(standardOp.isAStandardOperation("hyp"));
        assertFalse(standardOp.isAStandardOperation("prova"));
    }

    @Test
    public void testGetCommand() {
        number.setReal(1d);
        c.insertNumber(number);
        c.insertNumber(Complex.ImaginaryUnit);
        Complex expected = new Complex(1d, 1d);
        standardOp.getCommand("+").execute();
        assertComplexEquals(expected, c.getData().element());

        standardOp.getCommand(">j").execute();
        assertEquals(0, c.getData().size());//check if the stack is empty
        standardOp.getCommand("<j").execute();
        assertComplexEquals(expected, c.getData().element());

        standardOp.getCommand("-j").execute();
        assertEquals(0, c.getData().size());//check if the stack is empty
        standardOp.getCommand("<j").execute();
        assertComplexEquals(new Complex(), c.getData().pop());

        c.insertNumber(expected);
        standardOp.getCommand("+j").execute();
        assertEquals(0, c.getData().size());//check if the stack is empty
        standardOp.getCommand("<j").execute();
        assertComplexEquals(expected, c.getData().element());

        assertNull(standardOp.getCommand("test"));
    }

    @Test(expected = ParseException.class)
    public void testInsertNumberCommandException() {
        standardOp.insertNumberCommand("17-5.9j6");
    }

    @Test
    public void testInsertNumberCommand() {
        actual = standardOp.insertNumberCommand("0");
        actual.execute();
        assertComplexEquals(number, c.getData().pop());

        actual = standardOp.insertNumberCommand("178.35");
        actual.execute();
        number.setReal(178.35);
        assertComplexEquals(number, c.getData().pop());

        actual = standardOp.insertNumberCommand("-j25.225");
        actual.execute();
        number.setReal(0d);
        number.setImaginary(-25.225);
        assertComplexEquals(number, c.getData().pop());

        actual = standardOp.insertNumberCommand("+j18.36-0.735");
        actual.execute();
        number.setReal(-0.735);
        number.setImaginary(18.36);
        assertComplexEquals(number, c.getData().pop());
    }

    private void assertComplexEquals(Complex expected, Complex actual) {
        assertEquals(expected.getReal(), actual.getReal(), 0.00000001);
        assertEquals(expected.getImaginary(), actual.getImaginary(), 0.00000001);
    }

}
