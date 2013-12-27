/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.NameAlreadyExistsException;
import java.util.HashMap;
import java.util.HashSet;
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
        instance = new ProjectModel("TestProject");
    }
    
    @After
    public void tearDown() {
        instance = null;
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
        assertEquals("NewTestPackage", instance.packages().get(0).name());
        assertEquals("NewTestPackage1", instance.packages().get(1).name());
        assertEquals("NewTestPackage2", instance.packages().get(2).name());
    }
}
