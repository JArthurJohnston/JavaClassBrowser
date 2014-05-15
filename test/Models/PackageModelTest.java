/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.AlreadyExistsException;
import Exceptions.PackageDoesNotExistException;
import Exceptions.VeryVeryBadException;
import Internal.BaseTest;
import MainBase.EventTester;
import MainBase.MainApplication;
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
        try {
            parentProject = new MainApplication().addProject(new ProjectModel("AProject"));
            testPackage = parentProject.addPackage(new PackageModel("New Package"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
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
        assertEquals(LinkedList.class, testPackage.getPackageList().getClass());
        assertEquals(0, testPackage.getPackageList().size());
        assertEquals(0, testPackage.getClassList().size());
    }
    
    
    /**
     * Test of addClass method, of class PackageModel.
     */
    @Test
    public void testAddClass() {
        ClassModel newClass = null;
        try {
            newClass =  testPackage.addClass(new ClassModel("NewClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, testPackage.getClassList().size());
        assertEquals(newClass, testPackage.getClassList().getFirst());
        assertEquals(newClass, parentProject.findClass("NewClass"));
        try {
            testPackage.addClass(new ClassModel("NewClass"));
            fail("exception not thrown");
        } catch (AlreadyExistsException ex) { }
    }
    
    @Test
    public void testAddSubClass(){
        assertEquals(parentProject, testPackage.getParent());
        assertEquals(parentProject, testPackage.getProject());
        
        ClassModel aClass = null;
        ClassModel newClass = null;
        try {
            aClass = testPackage.addClass(new ClassModel("AClass"));
            newClass = aClass.addClass(new ClassModel("NewSubClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        
        assertEquals(newClass, parentProject.findClass("NewSubClass"));
        assertEquals(2, testPackage.getClassList().size());
        assertTrue(testPackage.getClassList().contains(newClass));
        assertEquals(aClass, testPackage.getClassList().get(0));
    }
    
    @Test
    public void testRemoveClass(){
        ClassModel classToBeRemoved = null;
        try {
            classToBeRemoved = testPackage.addClass(new ClassModel("ClassToBeRemoved"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(testPackage.getClassList().contains(classToBeRemoved));
        assertEquals(classToBeRemoved, parentProject.findClass("ClassToBeRemoved"));
        try {
            testPackage.removeClass(classToBeRemoved);
        } catch (VeryVeryBadException ex) {
            fail(ex.getMessage());
        }
        assertEquals(null, parentProject.findClass("ClassToBeRemoved"));
        assertFalse(testPackage.getClassList().contains(classToBeRemoved));
    }
    
    @Test
    public void testRemovePackage(){
        System.out.println("TestRemovePackage");
        PackageModel packageToBeRemoved = null;
        try {
            packageToBeRemoved = testPackage.addPackage(new PackageModel("Packge to be Removed"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(packageToBeRemoved, parentProject.findPackage("Packge to be Removed"));
        assertTrue(parentProject.getPackageList().contains(packageToBeRemoved));
        try {
            testPackage.removePackage(packageToBeRemoved);
        } catch (PackageDoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertEquals(null, parentProject.findPackage("Packge to be Removed"));
        assertFalse(testPackage.getPackageList().contains(packageToBeRemoved));
        try {
            testPackage.removePackage(packageToBeRemoved);
            fail("exception not thrown");
        } catch (PackageDoesNotExistException ex) {}
    }
    
    @Test
    public void testGetClassList(){
        assertTrue(testPackage.getClassList().isEmpty());
        ClassModel aClass = null;
        ClassModel subClass = null;
        try {
            aClass = testPackage.addClass(new ClassModel("AClass"));
            subClass = testPackage.addClass(new ClassModel("SubClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(testPackage.getClassList().contains(aClass));
        assertTrue(testPackage.getClassList().contains(subClass));
        assertEquals(2, testPackage.getClassList().size());
        ClassModel anotherClass = null;
        try {
            anotherClass = testPackage.addClass(new ClassModel("AnotherClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(testPackage.getClassList().contains(anotherClass));
        assertEquals(3, testPackage.getClassList().size());
        PackageModel anotherPackage = null;
        try {
            anotherPackage = parentProject.addPackage(new PackageModel("Another Package"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(3, testPackage.getClassList().size());
        try {
            aClass.moveToPackage(anotherPackage);
        } catch (AlreadyExistsException | VeryVeryBadException ex) {
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
        //test it gets the classes in sub-packages
        PackageModel subPackage = null;
        ClassModel subPackageClass = null;
        try {
            subPackage = testPackage.addPackage(new PackageModel("Sub Package"));
            subPackageClass = subPackage.addClass(new ClassModel("NewSubClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, subPackage.getClassList().size());
        /*
        for(Object c : testPackage.getClassList()){
            System.out.println(c.toString());
        }
        */
        assertEquals(3, testPackage.getClassList().size());
    }
    
    @Test
    public void testClassListAfterClassAdded(){
        assertTrue(testPackage.getClassList().isEmpty());
        ClassModel aClass = null;
        try {
            aClass = testPackage.addClass(new ClassModel("NewClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(testPackage.getClassList().contains(aClass));
    }
    
    @Test
    public void testClassListClassWithSubclass(){
        ClassModel aClass = null;
        ClassModel aSubClass = null;
        try {
            aClass = testPackage.addClass(new ClassModel("NewClass"));
            aSubClass = aClass.addClass(new ClassModel("NewSubClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(2, testPackage.getClassList().size());
        assertTrue(testPackage.getClassList().contains(aClass));
        assertTrue(testPackage.getClassList().contains(aSubClass));
    }
    
    @Test
    public void testClassListForClassWithinSubPackage(){
        ClassModel aClass = null;
        PackageModel subPackage = null;
        try {
            subPackage = testPackage.addPackage(new PackageModel("SubPackage"));
            aClass = subPackage.addClass(new ClassModel("NewClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, testPackage.getClassList().size());
        assertTrue(testPackage.getClassList().contains(aClass));
    }
    
    @Test
    public void testClassListForSubclassWithinSubPackage(){
        ClassModel aSubClass = null;
        PackageModel subPackage = null;
        try {
            subPackage = testPackage.addPackage(new PackageModel("SubPackage"));
            ClassModel aClass = subPackage.addClass(new ClassModel("NewClass"));
            aSubClass = aClass.addClass(new ClassModel("NewSubClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(2, testPackage.getClassList().size());
        assertTrue(testPackage.getClassList().contains(aSubClass));
    }
    
    @Test
    public void testClassListAfterClassMoved(){
        ClassModel aClass = null;
        PackageModel anotherPackage = null;
        try {
            aClass = testPackage.addClass(new ClassModel("AClass"));
            anotherPackage = parentProject.addPackage(new PackageModel("Yet another Package"));
            assertTrue(testPackage.getClassList().contains(aClass));
            assertTrue(anotherPackage.getClassList().isEmpty());
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        try {
            aClass.moveToPackage(anotherPackage);
        } catch (AlreadyExistsException | VeryVeryBadException ex) {
            fail(ex.getMessage());
        }
        assertTrue(testPackage.getClassList().isEmpty());
        assertEquals(1, anotherPackage.getClassList().size());
    }
    
    @Test
    public void testPackageRetainsSubclassesAfterParentMoved(){
        ClassModel parentClass = null;
        ClassModel subClass = null;
        PackageModel anotherPackage = null;
        try {
            parentClass = testPackage.addClass(new ClassModel("parent"));
            subClass = parentClass.addClass(new ClassModel("sub"));
            anotherPackage = parentProject.addPackage(new PackageModel("another package"));
            assertEquals(2, testPackage.getClassList().size());
            assertTrue(testPackage.getClassList().contains(parentClass));
            assertTrue(testPackage.getClassList().contains(subClass));
            parentClass.moveToPackage(anotherPackage);
        } catch (AlreadyExistsException | VeryVeryBadException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, testPackage.getClassList().size());
        assertEquals(1, anotherPackage.getClassList().size());
        assertFalse(testPackage.getClassList().contains(parentClass));
        assertTrue(anotherPackage.getClassList().contains(parentClass));
        assertTrue(testPackage.getClassList().contains(subClass));
    }
    
    @Test
    public void testAddPackageTriggersEvent(){
        EventTester listener = this.getTestListener();
        PackageModel aPackage = null;
        try {
            aPackage = testPackage.addPackage(new PackageModel("new package"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(listener.getEvent().isAdd());
        assertEquals(testPackage, listener.getEvent().getSource());
        assertEquals(aPackage, listener.getEvent().getModel());
    }
    
    @Test
    public void testAddClassTriggersEvent(){
        EventTester listener = this.getTestListener();
        ClassModel aClass = null;
        try {
            aClass = testPackage.addClass(new ClassModel("NewClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(listener.getEvent().isAdd());
        assertEquals(testPackage, listener.getEvent().getSource());
        assertEquals(aClass, listener.getEvent().getModel());
    }
    
    @Test
    public void testRemovePackageTriggersEvent(){
        fail();
    }
    
    @Test
    public void testRemoveClassTriggersEvent(){
        fail();
    }
    
    @Test
    public void testChangedEvents(){
        fail("test the other changed events");
    }
    
    @Test
    public void testParentIsProject(){
        assertEquals(testPackage.getParent(), testPackage.getProject());
    }
}
