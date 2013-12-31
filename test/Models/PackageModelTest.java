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
        parentProject = new ProjectModel();
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
    }
    
    @Test
    public void testInitialize(){
        System.out.println("testInitialize");
        instance = new PackageModel();
        assertEquals("default package", instance.name());
    }
    /**
     * Test of addClass method, of class PackageModel.
     */
    @Test
    public void testAddClass_String() {
        System.out.println("testAddClass");
        ClassModel newClass = new ClassModel("WrongClass");
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
        ClassModel newClass = new ClassModel("WrongClass");
        ClassModel aClass = new ClassModel("WrongParentClass");
        try {
            aClass = instance.addClass("AClass");
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
