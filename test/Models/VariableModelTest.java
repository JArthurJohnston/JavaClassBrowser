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
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of classType method, of class VariableModel.
     */
    @Test
    public void testClassType() {
        System.out.println("classType");
        ClassModel owner = new ClassModel();
        VariableModel instance = null;
        ClassType expResult = null;
        ClassType result = instance.classType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of name method, of class VariableModel.
     */
    @Test
    public void testName() {
        System.out.println("name");
        VariableModel instance = null;
        String expResult = "";
        String result = instance.name();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of type method, of class VariableModel.
     */
    @Test
    public void testType() {
        System.out.println("type");
        VariableModel instance = null;
        Object expResult = null;
        Object result = instance.type();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeName method, of class VariableModel.
     */
    @Test
    public void testChangeName() {
        System.out.println("changeName");
        String newName = "";
        VariableModel instance = null;
        instance.changeName(newName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
