
import it.unisa.diem.Gruppo20.Model.Calculator;
import it.unisa.diem.Gruppo20.Model.Complex;
import it.unisa.diem.Gruppo20.Model.UserCommand;
import it.unisa.diem.Gruppo20.Model.Operations;
import it.unisa.diem.Gruppo20.Model.StandardOperations;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Team 20
 */
public class OperationsTest {

    private Operations operations;
    private final Calculator c = new Calculator();
    private File testFile;

    public OperationsTest() {
    }

    @Before
    public void setUp() throws Exception {
        operations = new Operations(c);
        Field instance = StandardOperations.class.getDeclaredField("obj");
        instance.setAccessible(true);
        instance.set(null, null);
        testFile = new File("media/testFile.txt");
        testFile.deleteOnExit();
    }

    @Test(expected = RuntimeException.class)
    public void testParseOperationsExceptionName() {
        operations.parseOperations("   clear :  clear  4 8 + ");//PROBLEMA CON VARIABILI
    }
    
    @Test(expected = RuntimeException.class)
    public void testParseOperationsExceptionEmptyDef() {
        operations.parseOperations("   test :    ");
    }

    @Test
    public void testParseOperations() {
        operations.parseOperations("   test :  clear  4 8 + ");
        assertNotNull(operations.getOperationsCommand("test")); //check if user operation named test exist

        List<String> result = operations.getOperationsNames("test"); //get the operations' sequence defined by user
        List<String> expected = List.of("clear", "4", "8", "+"); //expected sequence

        assertEquals(expected.size(), result.size()); //checking if they have same size
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), result.get(i)); //check if operations are equals
        }
    }

    @Test
    public void testGetOperationsCommand() {
        operations.parseOperations("   test :  clear  4 8 + ");
        assertNotNull(operations.getOperationsCommand("test")); //check if user operation named test exist
        
        assertNotNull(operations.getOperationsCommand("mod"));
        assertNotNull(operations.getOperationsCommand("<a"));

        assertNull(operations.getOperationsCommand("notincluded"));
    }

    @Test
    public void testGetOperationsNames() {
        operations.parseOperations("   test :  clear  4 8 + ");
        assertNotNull(operations.getOperationsNames("test")); //check if user operation named test exist
    }

    @Test
    public void testExecuteOperation() {
        operations.parseOperations("   test :  clear  4 8 + ");
        operations.executeOperation("test");
        assertComplexEquals(new Complex(12.0, 0.0), c.getData().pop());
    }

    @Test
    public void testExecuteOperationWithAnotherOperation() {
        operations.parseOperations("   test :  clear  4+10j 5-4j + ");
        operations.parseOperations("   test1 :  test  -4 + ");

        operations.executeOperation("test1");

        assertComplexEquals(new Complex(5.0, 6.0), c.getData().pop());
    }

    @Test
    public void testUserOperationsNames() {
        operations.parseOperations("   test :  clear  4 8 + ");
        operations.parseOperations(" test1 :  clear  4 8 + ");
        Set<String> result = operations.userOperationsNames();
        Set<String> expected = Set.of("test", "test1");

        assertNotNull(result);
        assertEquals(expected.size(), result.size());

        for (String s : expected) {
            assertTrue(result.contains(s));
        }
    }

    @Test
    public void testRemoveOperation() {
        operations.parseOperations("   test :  clear  4 8 + ");
        operations.removeOperations("test");
        assertFalse(operations.userOperationsNames().contains("test"));
        UserCommand uc = (UserCommand) operations.getOperationsCommand("test");
        assertNotNull(uc);
        assertFalse(uc.isExecutable());
    }

    @Test
    public void testSaveOnFile() throws IOException {
        String expected_1 = "test_1: + - * / +- sqrt";
        String expected_2 = "test: clear drop dup swap over + - * / +- sqrt";
        operations.parseOperations("    test_1 :       + -  * / +- sqrt   ");
        operations.parseOperations("    test :   clear   drop  dup swap over     + -  * / +- sqrt   ");

        testFile.setWritable(true);
        operations.saveOnFile(testFile);

        String actual = read(testFile);
        String expected = expected_1 + "\n" + expected_2 + "\n";
        assertEquals(expected, actual);
    }

    @Test(expected = IOException.class)
    public void testSaveOnFileException() throws IOException {
        testFile.createNewFile();
        testFile.setReadOnly();
        operations.saveOnFile(testFile);
    }

    @Test
    public void testLoadFromFile() throws IOException {
        String expected_1 = "test_1: + - * / +- sqrt";
        String expected_2 = "test: clear drop dup swap over + - * / +- sqrt";
        String expected_3 = "test_3: 1+1j sqrt +- >a test_1 <a";
        String expected = expected_1 + "\n" + expected_2 + "\n" + expected_3 + "\n";

        operations.parseOperations("    test_1 :       + -  * / +- sqrt   ");
        operations.parseOperations("    test :   clear   drop  dup swap over     + -  * / +- sqrt   ");
        operations.parseOperations("test_3:  1+1j   sqrt +-  >a   test_1  <a   ");

        write(testFile);
        operations.loadFromFile(testFile);

        String actual = "";
        for (String i : operations.userOperationsNames()) {
            actual += operations.operationToString(i) + "\n";
        }
        assertEquals(expected, actual);
    }

    @Test(expected = IOException.class)
    public void testLoadFromFileException() throws IOException {
        testFile.delete();
        operations.loadFromFile(testFile);
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
            for (String i : operations.userOperationsNames()) {
                out.write(operations.operationToString(i) + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void assertComplexEquals(Complex expected, Complex actual) {
        assertEquals(expected.getReal(), actual.getReal(), 0.00000001);
        assertEquals(expected.getImaginary(), actual.getImaginary(), 0.00000001);
    }

}
