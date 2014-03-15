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
import java.util.LinkedList;
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
public class PackageModelTest extends BaseTest{
    
    private MainApplication main;
    PackageModel testPackage;
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
        testPackage = 
                this.addPackageToProject(new PackageModel(parentProject, "New Package"), parentProject);
    }
    
    @After
    public void tearDown() {
        parentProject = null;
        testPackage = null;
    }

    @Test
    public void testInitialize(){
        System.out.println("testInitialize");
        assertEquals(parentProject, testPackage.getParent());
        assertEquals(parentProject, testPackage.getProject());
        assertEquals(LinkedList.class, testPackage.getClassList().getClass());
        assertEquals(0,testPackage.getClassList().size());
    }
    
    
    /**
     * Test of addClass method, of class PackageModel.
     */
    @Test
    public void testAddClass() {
        ClassModel newClass = null;
        try {
            newClass =  testPackage.addClass(new ClassModel(testPackage, "NewClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, testPackage.getClassList().size());
        assertEquals(newClass, testPackage.getClassList().get(0));
        assertEquals(1, ((ProjectModel)testPackage.getParent()).getClasses().size());
        assertEquals(((ProjectModel)testPackage.getParent()).getClasses().get("NewClass"), newClass);
        try {
            testPackage.addClass(new ClassModel(testPackage, "NewClass"));
            fail("exception not thrown");
        } catch (NameAlreadyExistsException ex) { }
    }
    
    @Test
    public void testAddSubClass(){
        assertEquals(parentProject, testPackage.getParent());
        assertEquals(parentProject, testPackage.getProject());
        
        ClassModel aClass = 
                this.addClassToParent(new ClassModel(testPackage,"AClass"), testPackage);
        ClassModel newClass = 
                this.addClassToParent(new ClassModel(aClass,"NewSubClass"), aClass);
        
        assertEquals(2, parentProject.getClasses().size());
        assertEquals(newClass, parentProject.getClasses().get("NewSubClass"));
        assertEquals(2, testPackage.getClassList().size());
        assertTrue(testPackage.getClassList().contains(newClass));
        assertEquals(aClass, testPackage.getClassList().get(0));
    }
    
    @Test
    public void testRemoveClass(){
        ClassModel classToBeRemoved = 
                this.addClassToParent(new ClassModel(testPackage, "ClassToBeRemoved"), testPackage);
        assertTrue(testPackage.getClassList().contains(classToBeRemoved));
        assertTrue(parentProject.getClasses().containsKey("ClassToBeRemoved"));
        assertTrue(parentProject.getClasses().containsValue(classToBeRemoved));
        try {
            testPackage.removeClass("ClassToBeRemoved");
        } catch (ClassDoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertFalse(parentProject.getClasses().containsValue(classToBeRemoved));
        assertFalse(parentProject.getClasses().containsKey("ClassToBeRemoved"));
        assertFalse(testPackage.getClassList().contains(classToBeRemoved));
    }
    
    @Test
    public void testRemovePackage(){
        System.out.println("TestRemovePackage");
        PackageModel packageToBeRemoved = null;
        try {
            packageToBeRemoved = testPackage.addPackage(new PackageModel(testPackage, "Packge to be Removed"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(parentProject.getPackages().containsValue(packageToBeRemoved));
        assertTrue(testPackage.getPackageList().contains(packageToBeRemoved));
        try {
            testPackage.removePackage(packageToBeRemoved);
        } catch (PackageDoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertFalse(parentProject.getPackages().containsValue(packageToBeRemoved));
        assertFalse(testPackage.getPackageList().contains(packageToBeRemoved));
        try {
            testPackage.removePackage(packageToBeRemoved);
            fail("exception not thrown");
        } catch (PackageDoesNotExistException ex) {}
    }
    
    @Test
    public void testGetClassList(){
        LinkedList testClasses = new LinkedList();
        assertTrue(testPackage.getClassList().isEmpty());
        ClassModel aClass = 
                this.addClassToParent(new ClassModel(testPackage, "AClass"), testPackage);
        ClassModel subClass = 
                this.addClassToParent(new ClassModel(testPackage, "Subclass"), testPackage);
        assertTrue(testPackage.getClassList().contains(subClass));
        testClasses.add(aClass);
        testClasses.add(subClass);
        assertTrue(this.compareLists(testClasses, testPackage.getClassList()));
        ClassModel anotherClass = 
                this.addClassToParent(new ClassModel(testPackage, "AnotherClass"), testPackage);
        testClasses.add(anotherClass);
        assertTrue(this.compareLists(testClasses, testPackage.getClassList()));
        PackageModel anotherPackage = 
                this.addPackageToProject(new PackageModel(parentProject, "Another Package"), parentProject);
        assertEquals(3, testPackage.getClassList().size());
        try {
            aClass.moveToPackage(anotherPackage);
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(anotherPackage.getClassList().contains(aClass));
        assertFalse(testPackage.getClassList().contains(aClass));
        assertTrue(testPackage.getClassList().contains(subClass));
        assertFalse(anotherPackage.getClassList().contains(subClass));
        assertEquals(anotherPackage, aClass.getParentPackage());
        assertEquals(testPackage, subClass.getParentPackage());
        assertEquals(2, testPackage.getClassList().size());
        assertEquals(1, anotherPackage.getClassList().size());
    }
}
