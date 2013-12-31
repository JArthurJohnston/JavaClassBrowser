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
public class ClassModelTest {
    private ProjectModel parentProject;
    private PackageModel parentPackage;
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
    public void setUp() throws NameAlreadyExistsException {
        parentProject = new ProjectModel("Parent Project");
        parentPackage = parentProject.addPackage("Parent Package");
        instance = parentPackage.addClass("InstanceClass");
    }
    
    @After
    public void tearDown() {
        instance = null;
        parentPackage = null;
        parentProject = null;
    }
    
    @Test
    public void testInitialize(){
        System.out.println("testInitialize");
        assertEquals("InstanceClass", instance.name());
        assertEquals(parentProject, instance.getProject());
        assertEquals(parentPackage, instance.getParent());
        assertEquals(ClassModel.class, instance.getClass());
        assertEquals(0, instance.getClassList().size());
        assertEquals(ArrayList.class, instance.getClassList().getClass());
    }

    /**
     * Test of addClass method, of class ClassModel.
     */
    @Test
    public void testAddClass() {
        System.out.println("addClass");
        ClassModel newSubClass = new ClassModel();
        try {
            newSubClass = instance.addClass("NewSubClass");
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(ClassModelTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
        assertEquals(instance, newSubClass.getParent());
        assertEquals(parentProject, newSubClass.getProject());
        assertEquals(2, parentProject.getClasses().size());
        assertEquals(1, instance.getClassList().size());
        assertEquals(newSubClass, instance.getClassList().get(0));
        assertEquals(newSubClass, parentProject.getClasses().get("NewSubClass"));
        try {
            instance.addClass("NewSubClass");
            fail("Expected exception not thrown");
        } catch (NameAlreadyExistsException ex) {
            assertEquals(NameAlreadyExistsException.class, ex.getClass());
            Logger.getLogger(ClassModelTest.class.getName()).log(Level.FINE, null, ex);
        }
    }
}
