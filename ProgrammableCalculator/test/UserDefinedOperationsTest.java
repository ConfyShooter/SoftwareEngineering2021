import it.unisa.diem.Gruppo20.Model.Calculator;
import it.unisa.diem.Gruppo20.Model.Complex;
import it.unisa.diem.Gruppo20.Model.UserDefinedOperations;
import java.util.List;
import java.util.Set;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Gruppo20
 */
public class UserDefinedOperationsTest {
    private UserDefinedOperations userOp;
    private Calculator c;
    
    public UserDefinedOperationsTest() {
    }
    
    
    @Before
    public void setUp() {
        c = new Calculator();
        userOp = new UserDefinedOperations(c);
    }
    
    @Test
    public void testParseOperations() {
        userOp.parseOperations("   test :  clear  4 8 + ");
        assertNotNull(userOp.getOperationsCommand("test")); //check if user operation named test exist
        
        List<String> result = userOp.getOperationsNames("test"); //get the macro defined by user
        List<String> expected = List.of("clear", "4", "8", "+"); //expected macro
        
        assertEquals(expected.size(), result.size()); //checking if they have same size
        for(int i=0; i < expected.size(); i++)
            assertEquals(expected.get(i), result.get(i)); //check if macro's operations are equals
    }
    
    @Test
    public void testGetOperationsCommand() {
        userOp.parseOperations("   test :  clear  4 8 + ");
        assertNotNull(userOp.getOperationsCommand("test")); //check if user operation named test exist
        
        assertNull(userOp.getOperationsCommand("notincluded"));
    }
    
    @Test
    public void testGetOperationsNames() {
        userOp.parseOperations("   test :  clear  4 8 + ");
        assertNotNull(userOp.getOperationsNames("test")); //check if user operation named test exist
    }
    
    @Test
    public void testExecuteOperation() {
        userOp.parseOperations("   test :  clear  4 8 + ");
        userOp.executeOperation("test");
        assertComplexEquals(new Complex(12.0, 0.0), c.getData().peekFirst());
    }
    
    @Test
    public void testUserOperationsNames() {
        userOp.parseOperations("   test :  clear  4 8 + ");
        userOp.parseOperations(" test1 :  clear  4 8 + ");
        Set<String> result = userOp.userOperationsNames();
        Set<String> expected = Set.of("test", "test1");
        
        assertNotNull(result);
        assertEquals(expected.size(), result.size());
        
        for(String s: expected)
            assertTrue(result.contains(s));
    }
    
    @Test
    public void testRemoveOperation() {
        userOp.parseOperations("   test :  clear  4 8 + ");
        userOp.removeOperations("test");
        assertNull(userOp.getOperationsCommand("test"));
    }
    
    private void assertComplexEquals(Complex expected, Complex actual) {
        Assert.assertEquals(expected.getReal(), actual.getReal(), 0.00000001);
        Assert.assertEquals(expected.getImaginary(), actual.getImaginary(), 0.00000001);
    }

}
