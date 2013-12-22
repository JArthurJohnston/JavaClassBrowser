/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Types.ClassType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Arthur
 */
public class VariableModelTest {
     private ClassModel parent;
     private VariableModel instance;
    
    public VariableModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        int test = 5;
        parent = new ClassModel();
        instance = new VariableModel(parent, "someName", ClassType.INSTANCE, test);
    }
    
    @After
    public void tearDown() {
        parent = null;
        instance = null;
    }

    /**
     * Test of classType method, of class VariableModel.
     */
    @Test
    public void testClassType() {
        System.out.println("classType");
        ClassType expResult = ClassType.INSTANCE;
        ClassType result = instance.classType();
        assertEquals(expResult, result);
        instance = new VariableModel(parent, "someName", ClassType.CLASS);
        result = instance.classType();
        assertEquals(ClassType.CLASS, result);
    }

    /**
     * Test of name method, of class VariableModel.
     */
    @Test
    public void testName() {
        System.out.println("name");
        String expResult = "someName";
        String result = instance.name();
        assertEquals(expResult, result);
    }

    /**
     * Test of type method, of class VariableModel.
     */
    @Test
    public void testType() {
        System.out.println("type");
        Object result = instance.type();
        assertEquals(Integer.class, result);
    }

    /**
     * Test of changeName method, of class VariableModel.
     */
    @Test
    public void testChangeName() {
        System.out.println("changeName");
        String newName = "someNewName";
        instance.setName(newName);
        assertEquals(newName, instance.name());
    }
    
    @Test
    public void testChangeValue() {
        System.out.println("changeValue");
        int newVal = 6;
        instance.setValue(newVal);
        assertEquals(newVal, instance.value());
        String someString = "wrong value";
        instance.setValue(someString);
        assertEquals(instance.value(), newVal); //value should still be 6
    }
    
    @Test
    public void testConstructors(){
        assertEquals(parent, instance.parent());
        MethodModel someMethod = new MethodModel(parent);
        instance = new VariableModel(someMethod, "someName", ClassType.CLASS);
        assertEquals(someMethod, instance.parent());
        assertEquals(null, instance.scope());
    }
    
    @Test
    public void testToSourceString(){
        instance.setScope(ClassType.PUBLIC);
        String result = instance.toSourceString();
        String expResult = "public int someName = 5;\n";
        System.out.println(result);
        assertEquals(expResult, result);
    }
}
