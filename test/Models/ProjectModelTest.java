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
    private ProjectModel project;
    
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
        project = new ProjectModel(testMain, "Test Project");
    }
    
    @After
    public void tearDown() {
        testMain = null;
        project = null;
    }

    
    @Test
    public void testInitialize(){
        System.out.println("testInitialize");
        assertEquals(project.getClasses().size(),  0);
        assertEquals(project.getClasses().getClass(),  HashMap.class);
        assertEquals(project.getPackages().getClass(),  HashMap.class);
        assertEquals(project.getPackageList().getClass(),  ArrayList.class);
        assertEquals("Test Project", project.name());
        assertEquals(1, project.getPackages().size());
        assertEquals(1, project.getPackageList().size());
        assertEquals(project.getPackages().get("default package"), project.getPackageList().get(0));
        
        project = new ProjectModel();
        assertEquals(ProjectModel.class, project.getClass());
        assertEquals(project.getClasses().getClass(),  HashMap.class);
        assertEquals(project.getPackages().getClass(),  HashMap.class);
        assertEquals(project.getPackageList().getClass(),  ArrayList.class);
        assertEquals(project.getClasses().size(),  0);
        assertEquals(project.getPackages().size(), 1);
        assertEquals(project.getPackages().get("default package"), project.getPackageList().get(0));
        assertEquals("DefaultName", project.name());
        
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
            newPackage = project.addPackage(new PackageModel(project,"New Package"));
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(ProjectModelTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
        assertEquals("New Package", newPackage.name());
        assertEquals(newPackage ,project.getPackages().get(newPackage.name()));
        
        assertTrue(project.getPackages().size() == 2);
        assertTrue(project.getPackageList().size() == 2);
        assertEquals("default package" ,project.getPackageList().get(0).name());
        assertEquals(newPackage ,project.getPackageList().get(1));
        
        try {
            project.addPackage(new PackageModel(project, "New Package"));
            fail("NameAlreadyExistsException not thrown");
        } catch (NameAlreadyExistsException ex) {
        }
        assertTrue(project.getPackages().size() == 2);
        assertTrue(project.getPackageList().size() == 2);
        assertEquals(newPackage ,project.getPackageList().get(1));
        assertEquals(newPackage ,project.getPackages().get(newPackage.name()));
    }
    
    @Test
    public void testAddClass(){
        PackageModel testPackage = null;
        try {
            testPackage = project.addPackage(new PackageModel(project,"Test Package"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(testPackage.name().compareTo("Test Package") == 0);
        assertEquals(2, project.getPackages().size());
        assertEquals(testPackage, project.getPackages().get("Test Package"));
        assertEquals(testPackage, project.getPackageList().get(1));
        ClassModel newClass = null;
        try {
            newClass = testPackage.addClass(new ClassModel(testPackage, "NewClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, project.getClasses().size());
        assertEquals(newClass, project.getClasses().get("NewClass"));
        try {
            testPackage.addClass(new ClassModel(testPackage, "NewClass"));
            fail("Expected exception was not thrown");
        } catch (NameAlreadyExistsException ex) {}
    }
    
    @Test
    public void testRemovePackage(){
        PackageModel packageToBeRemoved = null;
        try {
            packageToBeRemoved = project.addPackage(new PackageModel(project, "PackageToBeRemoved"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(project.getPackages().containsKey("PackageToBeRemoved"));
        assertTrue(project.getPackages().containsValue(packageToBeRemoved));
        assertTrue(project.getPackageList().contains(packageToBeRemoved));
        try {
            project.removePackage(packageToBeRemoved);
        } catch (PackageDoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertFalse(project.getPackages().containsValue(packageToBeRemoved));
        assertFalse(project.getPackageList().contains(packageToBeRemoved));
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
        assertTrue(this.compareStrings(expectedDescr, project.getDescription()));
    }
    
    @Test
    public void testDateCreated(){
        System.out.println("test date created");
        //I can concieve of a time when there will be a milisecond difference between the two...
        assertEquals(new Date().toString(), project.getDateCreated().toString());
    }
    
    @Test
    public void testUserName(){
        System.out.println("test user name");
        assertEquals(testMain.getUserName(), project.getUserName());
        project.setUserName("Kyle Raynor");
        assertEquals("Kyle Raynor", project.getUserName());
        assertEquals("Barry Allen", testMain.getUserName());
    }
    
    
    @Test
    public void testMethodHash(){
        HashMap projectMethods = (HashMap)this.getVariableFromClass(project, "methods");
        assertEquals(0, projectMethods.size());
        assertEquals(HashMap.class, projectMethods.getClass());
        project.addMethod(new MethodModel("aMethod"));
        assertEquals(1, projectMethods.size());
        project.addMethod(new MethodModel("aMethod"));
        assertEquals(1, projectMethods.size());
    }
    
    @Test
    public void testListClasses(){
        assertEquals(0, project.getClassList().size());
        PackageModel aPackage = 
                this.addPackageToProject(new PackageModel(project, "New Package"), project);
        ClassModel aClass =     
                this.addClassToParent(new ClassModel(aPackage, "AClass"), aPackage);
        assertEquals(1, project.getClassList().size());
        ClassModel anotherClass = 
                this.addClassToParent(new ClassModel(aClass, "AnotherClass"), aClass);
        assertEquals(2, project.getClassList().size());
        ClassModel yetAnotherClass = 
                this.addClassToParent(new ClassModel(aClass, "YetAnotherClass"), anotherClass);
        assertEquals(3, project.getClassList().size());
        PackageModel anotherPackage = 
                this.addPackageToProject(new PackageModel(project, "Another Package"), project);
        ClassModel classInAnotherPackage = 
                this.addClassToParent(new ClassModel(aPackage, "AnotherClassInAnotherPackage"), anotherPackage);
        assertEquals(4, project.getClassList().size());
        LinkedList classList = project.getClassList();
        assertEquals(aClass, classList.get(0));
        assertEquals(anotherClass, classList.get(1));
        assertEquals(yetAnotherClass, classList.get(2));
        assertEquals(classInAnotherPackage, classList.get(3));
        PackageModel subPackage = 
                this.addPackageToProject(new PackageModel(aPackage, "Sub Package"), aPackage);
        ClassModel subPackageClass = 
                this.addClassToParent(new ClassModel(subPackage, "SubPackageClass"), subPackage);
        assertEquals(5, project.getClassList().size());
    }
}
