/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.NameAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Arthur
 */
public class ProjectModelTest {
    ProjectModel instance;
    
    public ProjectModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        String testName = "TestProject";
        instance = new ProjectModel(testName);
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    
    @Test
    public void testInitialize(){
        instance = new ProjectModel();
        assertEquals(ProjectModel.class, instance.getClass());
        assertEquals(instance.classes.getClass(),  HashMap.class);
        assertEquals(instance.packages.getClass(),  HashMap.class);
        assertEquals(instance.packageList.getClass(),  ArrayList.class);
        assertEquals(instance.classes.size(),  0);
        assertEquals(instance.packages.size(), 0);
        assertEquals("DefaultName", instance.name());
    }
    
    @Test
    public void testAddPackage() {
        try {
            instance.addPackage("NewTestPackage");
            instance.addPackage("NewTestPackage1");
            instance.addPackage("NewTestPackage2");
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(ProjectModelTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("NameAlreadyExistsException Thrown when it shouldnt have been");
        }
        try {
            instance.addPackage("NewTestPackage");
            fail("NameAlreadyExistsException wasnt thrown");
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(ProjectModelTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(3, instance.packages().size());
        assertEquals(3, instance.packageList().size());
        assertEquals(instance.packages.size(), instance.packageList.size());
        
        PackageModel expected = instance.packages().get("NewTestPackage");
        assertEquals(expected, instance.packageList().get(0));
        
        expected = instance.packages().get("NewTestPackage1");
        assertEquals(expected, instance.packageList().get(1));
        
        expected = instance.packages().get("NewTestPackage2");
        assertEquals(expected, instance.packageList().get(2));
        
        
    }
}
