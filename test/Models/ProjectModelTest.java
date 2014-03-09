/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.NameAlreadyExistsException;
import Exceptions.PackageDoesNotExistException;
import Internal.BaseTest;
import MainBase.MainApplication;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
public class ProjectModelTest extends BaseTest{
    private MainApplication testMain;
    private ProjectModel instance;
    
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
        testMain = new MainApplication();
        testMain.setUserName("Barry Allen");
        instance = new ProjectModel(testMain, "Test Project");
    }
    
    @After
    public void tearDown() {
        testMain = null;
        instance = null;
    }

    
    @Test
    public void testInitialize(){
        System.out.println("testInitialize");
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
    //useless test
    public void testStaticAccessors(){
        assertEquals("Project", ProjectModel.getSelectionString());
        
    }
    
    @Test
    public void testAddPackage() {
        System.out.println("testAddPackage");
        PackageModel newPackage = null;
        try {
            newPackage = instance.addPackage(new PackageModel(instance,"New Package"));
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
            instance.addPackage(new PackageModel(instance, "New Package"));
            fail("NameAlreadyExistsException not thrown");
        } catch (NameAlreadyExistsException ex) {
        }
        assertTrue(instance.getPackages().size() == 2);
        assertTrue(instance.getPackageList().size() == 2);
        assertEquals(newPackage ,instance.getPackageList().get(1));
        assertEquals(newPackage ,instance.getPackages().get(newPackage.name()));
    }
    
    @Test
    public void testAddClass(){
        PackageModel testPackage = null;
        try {
            testPackage = instance.addPackage(new PackageModel(instance,"Test Package"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(testPackage.name().compareTo("Test Package") == 0);
        assertEquals(2, instance.getPackages().size());
        assertEquals(testPackage, instance.getPackages().get("Test Package"));
        assertEquals(testPackage, instance.getPackageList().get(1));
        ClassModel newClass = null;
        try {
            newClass = testPackage.addClass(new ClassModel(testPackage, "NewClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, instance.getClasses().size());
        assertEquals(newClass, instance.getClasses().get("NewClass"));
        try {
            testPackage.addClass(new ClassModel(testPackage, "NewClass"));
            fail("Expected exception was not thrown");
        } catch (NameAlreadyExistsException ex) {}
    }
    
    @Test
    public void testRemovePackage(){
        PackageModel packageToBeRemoved = null;
        try {
            packageToBeRemoved = instance.addPackage(new PackageModel(instance, "PackageToBeRemoved"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(instance.getPackages().containsKey("PackageToBeRemoved"));
        assertTrue(instance.getPackages().containsValue(packageToBeRemoved));
        assertTrue(instance.getPackageList().contains(packageToBeRemoved));
        try {
            instance.removePackage(packageToBeRemoved);
        } catch (PackageDoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertFalse(instance.getPackages().containsValue(packageToBeRemoved));
        assertFalse(instance.getPackageList().contains(packageToBeRemoved));
    }
    
    @Test
    public void testDescription(){
        //remember, every project starts out with a package called 'default package'
        System.out.print("test description");
        String expectedDescr = "Project Name: Test Project\n"+
                               "Author: Barry Allen\n"+
                               "Date Created: "+new Date().toString()+"\n"+
                               "Number of Packages: 1\n"+
                               "Number of Classes: 0";
        assertEquals(expectedDescr, instance.getDescription());
    }
    
    @Test
    public void testDateCreated(){
        System.out.println("test date created");
        //I can concieve of a time when there will be a milisecond difference between the two...
        assertEquals(new Date().toString(), instance.getDateCreated().toString());
    }
    
    @Test
    public void testUserName(){
        System.out.println("test user name");
        assertEquals(testMain.getUserName(), instance.getUserName());
        instance.setUserName("Kyle Raynor");
        assertEquals("Kyle Raynor", instance.getUserName());
        assertEquals("Barry Allen", testMain.getUserName());
    }
    
    @Test
    public void testPackageClassLists(){
        LinkedList tLClasses = (LinkedList)this.getVariableFromClass(instance, "packageClasses");
        assertEquals(LinkedList.class, tLClasses.getClass());
        assertEquals(0, tLClasses.size());
        PackageModel newPackage = new PackageModel(instance, "New Package");
        PackageModel aPackage;
        try {
            aPackage = instance.addPackage(newPackage);
            ClassModel aClass = aPackage.addClass(new ClassModel(aPackage, "AClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, tLClasses.size());
    }
    
    @Test
    public void testBasicTypes(){
        assertEquals(instance.basicTypes().size() == 10);
    }
}
