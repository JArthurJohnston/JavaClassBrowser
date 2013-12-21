/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;
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
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addInstanceVariable method, of class ClassModel.
     */
    @Test
    public void testAddInstanceVariable() {
        System.out.println("addInstanceVariable");
        Object anObject = null;
        ClassModel instance = null;
        instance.addInstanceVariable(anObject);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addClassVariables method, of class ClassModel.
     */
    @Test
    public void testAddClassVariables() {
        System.out.println("addClassVariables");
        Object anObject = null;
        ClassModel instance = null;
        instance.addClassVariables(anObject);
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
        ClassModel instance = null;
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
        ClassModel instance = null;
        instance.addClassMethod(newMethod);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeClassVariable method, of class ClassModel.
     */
    @Test
    public void testRemoveClassVariable() {
        System.out.println("removeClassVariable");
        Object var = null;
        ClassModel instance = null;
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
        Object var = null;
        ClassModel instance = null;
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
        ClassModel instance = null;
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
        ClassModel instance = null;
        instance.removeInstanceMethods(method);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of instanceMethods method, of class ClassModel.
     */
    @Test
    public void testInstanceMethods() {
        System.out.println("instanceMethods");
        ClassModel instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.instanceMethods();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of classMethods method, of class ClassModel.
     */
    @Test
    public void testClassMethods() {
        System.out.println("classMethods");
        ClassModel instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.classMethods();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of instanceVariables method, of class ClassModel.
     */
    @Test
    public void testInstanceVariables() {
        System.out.println("instanceVariables");
        ClassModel instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.instanceVariables();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of classVariables method, of class ClassModel.
     */
    @Test
    public void testClassVariables() {
        System.out.println("classVariables");
        ClassModel instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.classVariables();
        assertEquals(expResult, result);
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
        ClassModel instance = null;
        instance.changeName(newName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
