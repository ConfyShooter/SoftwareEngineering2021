import it.unisa.diem.Gruppo20.Model.Variables;

import it.unisa.diem.Gruppo20.Model.Complex;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Gruppo20
 */
public class VariablesTest {
    
    private Variables v;
    private Complex number;
    
    public VariablesTest() {
    }
    
    @Before
    public void setUp() {
        v = new Variables() ;
        number = new Complex((double)1,(double)2);
        v.setVariable('j', number);
        v.setVariable('m', number.multiply(number));
        v.setVariable('p', number.plus(number));
    }
    
    @Test(expected=RuntimeException.class)
    public void testGetVariableExceptionSx (){
        v.getVariable('_');
    }
    
    @Test(expected=RuntimeException.class)
    public void testGetVariableExceptionDx (){
        v.getVariable('{');
    }
    
    @Test(expected=RuntimeException.class)
    public void testGetVariableExceptionValue (){
        v.getVariable('a');
    }
    
    @Test
    public void testGetVariable (){
        assertComplexEquals(number, v.getVariable('j'));
        assertComplexEquals(number.multiply(number), v.getVariable('m'));
        assertComplexEquals(number.plus(number), v.getVariable('p'));
    }
    
    @Test(expected=RuntimeException.class)
    public void testSetVariableExceptionSx (){
        v.setVariable('_', number);
    }
    
    @Test(expected=RuntimeException.class)
    public void testSetVariableExceptionDx (){
        v.setVariable('{', number);
    }
    
    @Test
    public void testSetVariable (){
        v.setVariable('m', number.minus(number));
        assertComplexEquals(new Complex(),v.getVariable('m'));
    }
    
    @Test(expected=RuntimeException.class)
    public void testSumVariableExceptionSx (){
        v.sumVariable('_', number);
    }
    
    @Test(expected=RuntimeException.class)
    public void testSumVariableExceptionDx (){
        v.sumVariable('{', number);
    }
    
    @Test
    public void testSumVariable (){
        v.sumVariable('j', number);
        assertComplexEquals(v.getVariable('p'),v.getVariable('j'));
    }
    
    @Test(expected=RuntimeException.class)
    public void testSubVariableExceptionSx (){
        v.sumVariable('_', number);
    }
    
    @Test(expected=RuntimeException.class)
    public void testSubVariableExceptionDx (){
        v.sumVariable('{', number);
    }
    
    @Test
    public void testSubVariable (){
        v.subVariable('j', number);
        assertComplexEquals(new Complex(), v.getVariable('j'));
    }
    
    private void assertComplexEquals(Complex expected, Complex actual) {
        Assert.assertEquals(expected.getReal(), actual.getReal(), 0.00000001);
        Assert.assertEquals(expected.getImaginary(), actual.getImaginary(), 0.00000001);
    }
    
    
    
}
