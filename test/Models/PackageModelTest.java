/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.NameAlreadyExistsException;
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
    ProjectModel parentProject;
    
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
        parentProject = new ProjectModel("AProject");
        try {
            instance = parentProject.addPackage("New Package");
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(PackageModelTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }
    
    @After
    public void tearDown() {
        parentProject = null;
        instance = null;
    }

    @Test
    public void testProjectKnowsPackage(){
        System.out.println("testProjectKnowsPackage");
        assertEquals(parentProject, instance.getParent());
        assertEquals(parentProject, instance.getProject());
    }
    
    /**
     * Test of addClass method, of class PackageModel.
     */
    @Test
    public void testAddClass() {
        System.out.println("testAddClass");
        ClassModel newClass = new ClassModel();
        try {
            newClass =  instance.addClass("NewClass");
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(PackageModelTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
        assertEquals(1, instance.getClassList().size());
        assertEquals(newClass, instance.getClassList().get(0));
        assertEquals(1, ((ProjectModel)instance.getParent()).getClasses().size());
        assertEquals(((ProjectModel)instance.getParent()).getClasses().get("NewClass"), newClass);
    }
    
    @Test
    public void testAddSubClass(){
        System.out.println("testAddSubClass");
        ClassModel newClass = new ClassModel();
        ClassModel aClass = new ClassModel();
        assertEquals(parentProject, instance.getParent());
        assertEquals(parentProject, instance.getProject());
        try {
            aClass = instance.addClass("AClass");
            assertEquals(parentProject, aClass.getProject());
            assertEquals(instance, aClass.getParent());
            newClass = aClass.addClass("NewSubClass");
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(PackageModelTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Exception thrown when it shouldnt");
        }
        assertEquals(2, parentProject.getClasses().size());
        assertEquals(newClass, parentProject.getClasses().get("NewSubClass"));
        assertEquals(1, instance.getClassList().size());
        assertEquals(aClass, instance.getClassList().get(0));
        assertEquals(aClass, newClass.getParent());
        assertEquals(newClass, aClass.getClassList().get(0));
    }

}
