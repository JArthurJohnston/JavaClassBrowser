/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.ClassDoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Exceptions.PackageDoesNotExistException;
import Internal.BaseTest;
import MainBase.MainApplication;
import java.util.ArrayList;
import java.util.LinkedList;
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
public class PackageModelTest extends BaseTest{
    
    private MainApplication main;
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
        main = new MainApplication();
        parentProject = new ProjectModel(main, "AProject");
        try {
            instance = parentProject.addPackage(new PackageModel(parentProject, "New Package"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
    }
    
    @After
    public void tearDown() {
        parentProject = null;
        instance = null;
    }

    @Test
    public void testInitialize(){
        System.out.println("testInitialize");
        assertEquals(parentProject, instance.getParent());
        assertEquals(parentProject, instance.getProject());
        assertEquals(ArrayList.class, instance.getClassList().getClass());
        assertEquals(0,instance.getClassList().size());
    }
    
    
    /**
     * Test of addClass method, of class PackageModel.
     */
    @Test
    public void testAddClass() {
        ClassModel newClass = null;
        try {
            newClass =  instance.addClass(new ClassModel(instance, "NewClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, instance.getClassList().size());
        assertEquals(newClass, instance.getClassList().get(0));
        assertEquals(1, ((ProjectModel)instance.getParent()).getClasses().size());
        assertEquals(((ProjectModel)instance.getParent()).getClasses().get("NewClass"), newClass);
    }
    
    @Test
    public void testAddSubClass(){
        ClassModel newClass = new ClassModel();
        ClassModel aClass = new ClassModel();
        assertEquals(parentProject, instance.getParent());
        assertEquals(parentProject, instance.getProject());
        try {
            aClass = instance.addClass(new ClassModel(instance,"AClass"));
            assertEquals(parentProject, aClass.getProject());
            assertEquals(instance, aClass.getParent());
            newClass = aClass.addClass(new ClassModel(aClass,"NewSubClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(2, parentProject.getClasses().size());
        assertEquals(newClass, parentProject.getClasses().get("NewSubClass"));
        assertEquals(1, instance.getClassList().size());
        assertEquals(aClass, instance.getClassList().get(0));
        assertEquals(aClass, newClass.getParent());
        assertEquals(newClass, aClass.getClassList().get(0));
    }
    
    @Test
    public void testRemoveClass(){
        ClassModel classToBeRemoved = null;
        try {
            classToBeRemoved = instance.addClass(new ClassModel(instance, "ClassToBeRemoved"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(instance.getClassList().contains(classToBeRemoved));
        assertTrue(parentProject.getClasses().containsKey("ClassToBeRemoved"));
        assertTrue(parentProject.getClasses().containsValue(classToBeRemoved));
        try {
            instance.removeClass("ClassToBeRemoved");
        } catch (ClassDoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertFalse(parentProject.getClasses().containsValue(classToBeRemoved));
        assertFalse(parentProject.getClasses().containsKey("ClassToBeRemoved"));
        assertFalse(instance.getClassList().contains(classToBeRemoved));
    }
    
    @Test
    public void testRemovePackage(){
        System.out.println("TestRemovePackage");
        PackageModel packageToBeRemoved = null;
        try {
            packageToBeRemoved = instance.addPackage(new PackageModel(instance, "Packge to be Removed"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(parentProject.getPackages().containsValue(packageToBeRemoved));
        assertTrue(instance.getPackageList().contains(packageToBeRemoved));
        try {
            instance.removePackage(packageToBeRemoved);
        } catch (PackageDoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertFalse(parentProject.getPackages().containsValue(packageToBeRemoved));
        assertFalse(instance.getPackageList().contains(packageToBeRemoved));
        try {
            instance.removePackage(packageToBeRemoved);
            fail("exception not thrown");
        } catch (PackageDoesNotExistException ex) {}
    }
    
    @Test
    public void testTopLevelClasses(){
        LinkedList tLClasses = (LinkedList)this.getVariableFromClass(instance, "topLevelClasses");
        assertEquals(LinkedList.class, tLClasses.getClass());
        assertEquals(0, tLClasses.size());
        ClassModel newClass = null;
        try {
            newClass = instance.addClass(new ClassModel(instance, "NewClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, tLClasses.size());
        assertEquals(1, instance.getParent().getPackageClasses());
        assertTrue(tLClasses.contains(newClass));
    }
}
