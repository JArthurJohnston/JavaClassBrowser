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
        instance = new ProjectModel("Test Project");
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    
    @Test
    public void testInitialize(){
        assertEquals(instance.getClasses().size(),  0);
        assertEquals(instance.getClasses().getClass(),  HashMap.class);
        assertEquals(instance.getPackages().getClass(),  HashMap.class);
        assertEquals(instance.getPackageList().getClass(),  ArrayList.class);
        assertEquals("Test Project", instance.name());
        assertEquals(1, instance.getPackages().size());
        assertEquals(1, instance.getPackageList().size());
        assertEquals(instance.getPackages().get("default package"), instance.getPackageList().get(0));
        
        instance = new ProjectModel();
        assertEquals(ProjectModel.class, instance.getClass());
        assertEquals(instance.getClasses().getClass(),  HashMap.class);
        assertEquals(instance.getPackages().getClass(),  HashMap.class);
        assertEquals(instance.getPackageList().getClass(),  ArrayList.class);
        assertEquals(instance.getClasses().size(),  0);
        assertEquals(instance.getPackages().size(), 1);
        assertEquals(instance.getPackages().get("default package"), instance.getPackageList().get(0));
        assertEquals("DefaultName", instance.name());
    }
    
    @Test
    public void testAddPackage() {
        PackageModel newPackage = new PackageModel("Wrong Package");
        try {
            newPackage = instance.addPackage("New Package");
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(ProjectModelTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
        assertEquals("New Package", newPackage.name());
        assertEquals(newPackage ,instance.getPackages().get(newPackage.name()));
        
        assertTrue(instance.getPackages().size() == 2);
        assertTrue(instance.getPackageList().size() == 2);
        assertEquals("default package" ,instance.getPackageList().get(0).name());
        assertEquals(newPackage ,instance.getPackageList().get(1));
        
        try {
            instance.addPackage("New Package");
            fail("NameAlreadyExistsException not thrown");
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(ProjectModelTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertTrue(instance.getPackages().size() == 2);
        assertTrue(instance.getPackageList().size() == 2);
        assertEquals(newPackage ,instance.getPackageList().get(1));
        assertEquals(newPackage ,instance.getPackages().get(newPackage.name()));
    }
}
