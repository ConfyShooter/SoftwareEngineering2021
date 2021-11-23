
import it.unisa.diem.Gruppo20.Model.Calculator;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Gruppo20
 */
public class CalculatorTest {

    private Calculator c;

    public CalculatorTest() {
        c = new Calculator();
    }


    @Before
    public void setUp() {
    }

    @Test
    public void testParsing() {
        c.parsing("0.1+2.5j");
        assertEquals("0.1+2.5j", c.getData().peekFirst().toString());
        c.parsing("4.0j");
        assertEquals("0.5j", c.getData().peekFirst().toString());
        c.parsing("3.0");
        assertEquals("3.0", c.getData().peekFirst().toString());
        
    }
    
    @Test
    public void testSum() {
        c.sum();
        assertEquals("3.0+4.0j", c.getData().peekFirst().toString());
    }

    @Test
    public void testSqrt() {
        c.sqrt();
        assertEquals("2.0+1.0j", c.getData().peekFirst().toString());
    }
    
    @Test
    public void testDivision() {
        
    }

}
