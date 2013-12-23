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
public class ClassModelTest {
    private ClassModel instance;
    
    public ClassModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new ClassModel(); 
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of addVariables method, of class ClassModel.
     */
    @Test
    public void testAddVariables() {
        System.out.println("addVariables");
        
    }

    /**
     * Test of removeVariables method, of class ClassModel.
     */
    @Test
    public void testRemoveVariables() {
        System.out.println("removeVariables");
        VariableModel var = null;
        ClassModel instance = new ClassModel();
        instance.removeVariables(var);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addInstanceVariable method, of class ClassModel.
     */
    @Test
    public void testAddInstanceVariable() {
        System.out.println("addInstanceVariable");
        VariableModel newVar = null;
        ClassModel instance = new ClassModel();
        instance.addInstanceVariable(newVar);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addClassVariables method, of class ClassModel.
     */
    @Test
    public void testAddClassVariables() {
        System.out.println("addClassVariables");
        VariableModel newVar = null;
        ClassModel instance = new ClassModel();
        instance.addClassVariables(newVar);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addInstanceMethod method, of class ClassModel.
     */
    @Test
    public void testAddInstanceMethod() {
        System.out.println("addInstanceMethod");
        MethodModel newMethod = null;
        ClassModel instance = new ClassModel();
        instance.addInstanceMethod(newMethod);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addClassMethod method, of class ClassModel.
     */
    @Test
    public void testAddClassMethod() {
        System.out.println("addClassMethod");
        MethodModel newMethod = null;
        ClassModel instance = new ClassModel();
        instance.addClassMethod(newMethod);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setScope method, of class ClassModel.
     */
    @Test
    public void testSetScope() {
        System.out.println("setScope");
        ClassType newScope = null;
        ClassModel instance = new ClassModel();
        instance.setScope(newScope);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeClassVariable method, of class ClassModel.
     */
    @Test
    public void testRemoveClassVariable() {
        System.out.println("removeClassVariable");
        VariableModel var = null;
        ClassModel instance = new ClassModel();
        instance.removeClassVariable(var);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeInstanceVariables method, of class ClassModel.
     */
    @Test
    public void testRemoveInstanceVariables() {
        System.out.println("removeInstanceVariables");
        VariableModel var = null;
        ClassModel instance = new ClassModel();
        instance.removeInstanceVariables(var);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeClassMethod method, of class ClassModel.
     */
    @Test
    public void testRemoveClassMethod() {
        System.out.println("removeClassMethod");
        MethodModel method = null;
        ClassModel instance = new ClassModel();
        instance.removeClassMethod(method);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeInstanceMethods method, of class ClassModel.
     */
    @Test
    public void testRemoveInstanceMethods() {
        System.out.println("removeInstanceMethods");
        MethodModel method = null;
        ClassModel instance = new ClassModel();
        instance.removeInstanceMethods(method);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeName method, of class ClassModel.
     */
    @Test
    public void testChangeName() {
        System.out.println("changeName");
        String newName = "";
        ClassModel instance = new ClassModel();
        instance.changeName(newName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of okToAddVariable method, of class ClassModel.
     */
    @Test
    public void testOkToAddVariable() {
        System.out.println("okToAddVariable");
        VariableModel newVar = null;
        ClassModel instance = new ClassModel();
        boolean expResult = false;
        boolean result = instance.okToAddVariable(newVar);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of okToRemoveVariable method, of class ClassModel.
     */
    @Test
    public void testOkToRemoveVariable() {
        System.out.println("okToRemoveVariable");
        VariableModel var = null;
        ClassModel instance = new ClassModel();
        boolean expResult = false;
        boolean result = instance.okToRemoveVariable(var);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of name method, of class ClassModel.
     */
    @Test
    public void testName() {
        System.out.println("name");
        ClassModel instance = new ClassModel();
        String expResult = "";
        String result = instance.name();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of scope method, of class ClassModel.
     */
    @Test
    public void testScope() {
        System.out.println("scope");
        ClassModel instance = new ClassModel();
        ClassType expResult = null;
        ClassType result = instance.scope();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
