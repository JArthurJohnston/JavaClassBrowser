/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.NameAlreadyExistsException;
import Exceptions.PackageDoesNotExistException;
import Exceptions.VeryVeryBadException;
import Internal.BaseTest;
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
        main = new MainApplication();
        parentProject = new ProjectModel("AProject");
        testPackage = 
                this.addPackageToProject("New Package", parentProject);
    }
    
    @After
    public void tearDown() {
        parentProject = null;
        testPackage = null;
        main = null;
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
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, testPackage.getClassList().size());
        assertEquals(newClass, testPackage.getClassList().getFirst());
        assertEquals(newClass, parentProject.findClass("NewClass"));
        try {
            testPackage.addClass(new ClassModel("NewClass"));
            fail("exception not thrown");
        } catch (NameAlreadyExistsException ex) { }
    }
    
    @Test
    public void testAddSubClass(){
        assertEquals(parentProject, testPackage.getParent());
        assertEquals(parentProject, testPackage.getProject());
        
        ClassModel aClass = 
                this.addClassToParent("AClass", testPackage);
        ClassModel newClass = 
                this.addClassToParent("NewSubClass", aClass);
        
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
        } catch (NameAlreadyExistsException ex) {
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
        ClassModel aClass = this.addClassToParent("AClass", testPackage);
        ClassModel subClass = this.addClassToParent("Subclass", testPackage);
        assertTrue(testPackage.getClassList().contains(subClass));
        testClasses.add(aClass);
        testClasses.add(subClass);
        assertTrue(this.compareLists(testClasses, testPackage.getClassList()));
        ClassModel anotherClass = this.addClassToParent("AnotherClass", testPackage);
        testClasses.add(anotherClass);
        assertTrue(this.compareLists(testClasses, testPackage.getClassList()));
        PackageModel anotherPackage = this.addPackageToProject("Another Package", parentProject);
        assertEquals(3, testPackage.getClassList().size());
        try {
            aClass.moveToPackage(anotherPackage);
        } catch (NameAlreadyExistsException | VeryVeryBadException ex) {
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
        PackageModel subPackage =  this.addPackageToProject("Sub Package", testPackage);
        ClassModel subPackageClass = this.addClassToParent("NewSubClass", subPackage);
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
        } catch (NameAlreadyExistsException ex) {
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
        } catch (NameAlreadyExistsException ex) {
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
        } catch (NameAlreadyExistsException ex) {
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
        } catch (NameAlreadyExistsException ex) {
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
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        try {
            aClass.moveToPackage(anotherPackage);
        } catch (NameAlreadyExistsException | VeryVeryBadException ex) {
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
        } catch (NameAlreadyExistsException | VeryVeryBadException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, testPackage.getClassList().size());
        assertEquals(1, anotherPackage.getClassList().size());
        assertFalse(testPackage.getClassList().contains(parentClass));
        assertTrue(anotherPackage.getClassList().contains(parentClass));
        assertTrue(testPackage.getClassList().contains(subClass));
    }
    
    @Test
    public void testAddPackageTriggersUpdateShells(){
        fail("main should tell every shell except the caller"
                + "to update itself with the new package, if appliable");
    }
    @Test
    public void testAddClassTriggersUpdateShells(){
        fail("main should tell every shell except the caller"
                + "to update itself with the new package, if appliable");
    }
    
    @Test
    public void testGetMain(){
        assertEquals(main, testPackage.getMain());
    }
    
    @Test
    public void testParentIsProject(){
        assertEquals(testPackage.getParent(), testPackage.getProject());
    }
}
