
import it.unisa.diem.Gruppo20.Model.Calculator;
import it.unisa.diem.Gruppo20.Model.Complex;
import it.unisa.diem.Gruppo20.Model.UserDefinedOperations;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Gruppo20
 */
public class UserDefinedOperationsTest {

    private UserDefinedOperations userOp;
    private Calculator c;
    private final File testSaveFile = new File("testSaveFile.txt");
    private final File testReadFile = new File("testReadFile.txt");

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
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), result.get(i)); //check if macro's operations are equals
        }
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
    public void testExecuteOperationWithAnotherOperation() {
        userOp.parseOperations("   test :  clear  4+10j 5-4j + ");
        userOp.parseOperations("   test1 :  test  -4 + ");

        userOp.executeOperation("test1");

        assertComplexEquals(new Complex(5.0, 6.0), c.getData().peekFirst());
    }

    @Test
    public void testUserOperationsNames() {
        userOp.parseOperations("   test :  clear  4 8 + ");
        userOp.parseOperations(" test1 :  clear  4 8 + ");
        Set<String> result = userOp.userOperationsNames();
        Set<String> expected = Set.of("test", "test1");

        assertNotNull(result);
        assertEquals(expected.size(), result.size());

        for (String s : expected) {
            assertTrue(result.contains(s));
        }
    }

    @Test
    public void testRemoveOperation() {
        userOp.parseOperations("   test :  clear  4 8 + ");
        userOp.removeOperations("test");
        assertNull(userOp.getOperationsCommand("test"));
    }

    @Test
    public void testSaveOnFile() {
        String expected_1 = "test_1: + - * / +- sqrt";
        String expected_2 = "test: clear drop dup swap over + - * / +- sqrt";
        userOp.parseOperations("    test_1 :       + -  * / +- sqrt   ");
        userOp.parseOperations("    test :   clear   drop  dup swap over     + -  * / +- sqrt   ");

        try {
            userOp.saveOnFile(testSaveFile);
        } catch (IOException ex) {
            Logger.getLogger(UserDefinedOperationsTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        String actual = read(testSaveFile);
        String expected = expected_1 + "\n" + expected_2 + "\n";
        assertEquals(expected, actual);
    }

    @Test
    public void testLoadFromFile() {
        String expected_1 = "test_1: + - * / +- sqrt";
        String expected_2 = "test: clear drop dup swap over + - * / +- sqrt";
        String expected = expected_1 + "\n" + expected_2 + "\n";

        userOp.parseOperations("    test_1 :       + -  * / +- sqrt   ");
        userOp.parseOperations("    test :   clear   drop  dup swap over     + -  * / +- sqrt   ");

        write(testReadFile);
        try {
            userOp.loadFromFile(testReadFile);
        } catch (IOException ex) {
            Logger.getLogger(UserDefinedOperationsTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        String actual = "";
        for (String i : userOp.userOperationsNames()) {
            actual += i + ":" + userOp.operationsNameToString(i);
        }
        assertEquals(expected, actual);
    }

    private String read(File file) {
        String str = "";
        try (Scanner in = new Scanner(new BufferedReader(new FileReader(file)))) {
            in.useLocale(Locale.US);
            in.useDelimiter("\n+|\n\r");
            while (in.hasNext()) {
                str += in.next() + "\n";
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return str;
    }

    private void write(File file) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            for (String i : userOp.userOperationsNames()) {
                out.write(i + ":" + userOp.operationsNameToString(i)/*userOp.getOperationsNames(i)*/);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void assertComplexEquals(Complex expected, Complex actual) {
        Assert.assertEquals(expected.getReal(), actual.getReal(), 0.00000001);
        Assert.assertEquals(expected.getImaginary(), actual.getImaginary(), 0.00000001);
    }

}
