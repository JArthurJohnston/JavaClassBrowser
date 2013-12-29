/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.NameAlreadyExistsException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class PackageModelTest {
    PackageModel instance;
    ProjectModel parent;
    
    public PackageModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        try {
            parent = new ProjectModel();
            instance = parent.addPackage("New Package");
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(PackageModelTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {
        parent = null;
        instance = null;
    }

    @Test
    public void testInitialize(){
        
        
    }
    
    
    /**
     * Test of setUpDataStructures method, of class PackageModel.
     */
    @Test
    public void testSetUpDataStructures() {
        System.out.println("setUpDataStructures");
        PackageModel instance = new PackageModel();
        instance.setUpDataStructures();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addClass method, of class PackageModel.
     */
    @Test
    public void testAddClass_String() throws Exception {
        System.out.println("addClass");
        String newClassName = "";
        PackageModel instance = new PackageModel();
        ClassModel expResult = null;
        ClassModel result = instance.addClass(newClassName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addClass method, of class PackageModel.
     */
    @Test
    public void testAddClass_ClassModel() {
        System.out.println("addClass");
        ClassModel newClass = null;
        PackageModel instance = new PackageModel();
        ClassModel expResult = null;
        ClassModel result = instance.addClass(newClass);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of classList method, of class PackageModel.
     */
    @Test
    public void testClassList() {
        System.out.println("classList");
        PackageModel instance = new PackageModel();
        ArrayList expResult = null;
        ArrayList result = instance.classList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
