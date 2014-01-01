/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.ClassDoesNotExistException;
import Exceptions.MethodDoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import java.util.ArrayList;
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
        assertEquals(ArrayList.class, instance.getClassMethods().getClass());
        assertEquals(ArrayList.class, instance.getInstanceMethods().getClass());
        assertEquals(ArrayList.class, instance.getConstructors().getClass());
        assertEquals(0, instance.getConstructors().size());
                
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
    
    @Test
    public void testRemoveClass(){
        ClassModel classToBeRemoved = new ClassModel();
        System.out.println("testRemoveClass");
        try {
            classToBeRemoved = instance.addClass("ClassToBeRemoved");
            assertEquals(2, parentProject.getClasses().size());
            assertEquals(1, instance.getClassList().size());
            assertTrue(instance.getClassList().contains(classToBeRemoved));
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(ClassModelTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            instance.removeClass("ClassToBeRemoved");
        } catch (ClassDoesNotExistException ex) {
            Logger.getLogger(ClassModelTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
        try {
            instance.removeClass("ClassToBeRemoved");
            fail("Exception not thrown");
        } catch (ClassDoesNotExistException ex) {
            Logger.getLogger(ClassModelTest.class.getName()).log(Level.FINE, null, ex);
        }
        assertFalse(parentProject.getClasses().containsKey("ClassToBeRemoved"));
        assertFalse(instance.getClassList().contains(classToBeRemoved));
    }
    
    @Test
    public void testAddMethod(){
        MethodModel newMethod = new MethodModel();
        System.out.println("testAddMethod");
        try {
            newMethod = instance.addMethod("newMethod");
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(ClassModelTest.class.getName()).log(Level.FINE, null, ex);
            fail("Exception thrown when it shouldnt");
        }
        assertEquals(instance, newMethod.getParent());
        assertEquals(1, instance.getMethods().size());
        try {
            instance.addMethod("newMethod");
            fail("Exception not thrown");
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(ClassModelTest.class.getName()).log(Level.FINE, null, ex);
            assertEquals(NameAlreadyExistsException.class, ex.getClass());
        }
    }
    
    @Test
    public void testRemoveMethod(){
        System.out.println("testRemoveMethod");
        try {
            instance.addMethod("aMethodForTesting");
            assertEquals(1, instance.getMethods().size());
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(ClassModelTest.class.getName()).log(Level.FINE, null, ex);
        }
        try {
            instance.removeMethod("aMethodForTesting");
        } catch (MethodDoesNotExistException ex) {
            Logger.getLogger(ClassModelTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
        assertEquals(0, instance.getMethods().size());
        try {
            instance.removeMethod("aNonExistantMethod");
            fail("Exception not thrown");
        } catch (MethodDoesNotExistException ex) {
            Logger.getLogger(ClassModelTest.class.getName()).log(Level.FINE, null, ex);
        }
    }
    
}
