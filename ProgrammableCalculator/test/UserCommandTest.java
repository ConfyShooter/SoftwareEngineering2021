import it.unisa.diem.Gruppo20.Model.Calculator;
import it.unisa.diem.Gruppo20.Model.Command;
import it.unisa.diem.Gruppo20.Model.Complex;
import it.unisa.diem.Gruppo20.Model.UserCommand;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Gruppo 20
 */
public class UserCommandTest {
    private Calculator c;
    private UserCommand userCom;
    private Command sum;
    private Command invert;
    private Complex num1;
    private Complex num2;
    
    public UserCommandTest() {
    }
    
    @Before
    public void setUp() {
        c = new Calculator();
        userCom = new UserCommand();
        sum = c::sum;
        invert = c::invertSign;
        num1 = new Complex(10.0, 10.0);
        num2 = new Complex(25.0, 45.0);
        
    }
    
    @Test
    public void testAdd() {
        userCom.add("+", sum);
        userCom.add("+-", invert);
        c.insertNumber(num1);
        c.insertNumber(num2);
        
        List<String> opNames = userCom.getCommandName();
        List<String> expectedOp = List.of("+", "+-");
        assertEquals(expectedOp.size(), opNames.size());
        for(int i = 0; i < expectedOp.size(); i++)
            assertEquals(expectedOp.get(i), opNames.get(i));
        
    }
    
    @Test(expected = NullPointerException.class)
    public void testAddException() {
        userCom.add(null, sum);
        userCom.add("+", null);
    }
    
    @Test
    public void testExecute() {
        userCom.add("+", sum);
        userCom.add("+-", invert);
        c.insertNumber(num1);
        c.insertNumber(num2);
        
        userCom.execute();
        
        Complex result = c.getData().peekFirst();
        Complex expected = new Complex(-35.0, -55.0);
        assertComplexEquals(expected, result); 
    }
    
    @Test(expected = RuntimeException.class)
    public void testExecuteException() {
        userCom.execute();
    }
    
    @Test(expected = RuntimeException.class)
    public void testReset() {
        testAdd();
        userCom.reset();
        userCom.execute();
    }
    
    private void assertComplexEquals(Complex expected, Complex actual) {
        Assert.assertEquals(expected.getReal(), actual.getReal(), 0.00000001);
        Assert.assertEquals(expected.getImaginary(), actual.getImaginary(), 0.00000001);
    }

}