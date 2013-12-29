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
     * Test of setUpDataStructures method, of class ClassModel.
     */
    @Test
    public void testSetUpDataStructures() {
        System.out.println("setUpDataStructures");
        ClassModel instance = new ClassModel();
        instance.setUpDataStructures();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addClass method, of class ClassModel.
     */
    @Test
    public void testAddClass_String() throws Exception {
        System.out.println("addClass");
        String newClassName = "";
        ClassModel instance = new ClassModel();
        ClassModel expResult = null;
        ClassModel result = instance.addClass(newClassName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addClass method, of class ClassModel.
     */
    @Test
    public void testAddClass_ClassModel() {
        System.out.println("addClass");
        ClassModel newClass = null;
        ClassModel instance = new ClassModel();
        ClassModel expResult = null;
        ClassModel result = instance.addClass(newClass);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inheritableMethods method, of class ClassModel.
     */
    @Test
    public void testInheritableMethods() {
        System.out.println("inheritableMethods");
        ClassModel instance = new ClassModel();
        ArrayList expResult = null;
        ArrayList result = instance.inheritableMethods();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isTopLevel method, of class ClassModel.
     */
    @Test
    public void testIsTopLevel() {
        System.out.println("isTopLevel");
        ClassModel instance = new ClassModel();
        boolean expResult = false;
        boolean result = instance.isTopLevel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of instanceMethods method, of class ClassModel.
     */
    @Test
    public void testInstanceMethods() {
        System.out.println("instanceMethods");
        ClassModel instance = new ClassModel();
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
        ClassModel instance = new ClassModel();
        ArrayList expResult = null;
        ArrayList result = instance.classMethods();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInheritedMethods method, of class ClassModel.
     */
    @Test
    public void testGetInheritedMethods() {
        System.out.println("getInheritedMethods");
        ClassModel instance = new ClassModel();
        ArrayList expResult = null;
        ArrayList result = instance.getInheritedMethods();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
