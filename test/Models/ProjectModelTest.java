/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.DoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Exceptions.PackageDoesNotExistException;
import Exceptions.VeryVeryBadException;
import Internal.BaseTest;
import MainBase.MainApplication;
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
        try {
            project = new MainApplication().addProject(new ProjectModel("Test Project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
    }
    
    @After
    public void tearDown() {
        project = null;
    }

    @Test
    public void testInitialize(){
        assertEquals(project.getClassList().size(),  0);
        assertEquals(project.getClassList().getClass(),  LinkedList.class);
        assertEquals(project.getPackageList().getClass(),  LinkedList.class);
        assertEquals("Test Project", project.name());
        assertEquals(2, project.getPackageList().size());
        assertEquals(project.findPackage("default package"), project.getPackageList().get(1));
        assertEquals("All", project.getPackageList().getFirst().name());
        
        project = new ProjectModel("A Project");
        assertEquals(ProjectModel.class, project.getClass());
        assertEquals(project.getPackageList().getClass(),  LinkedList.class);
        assertEquals(project.getClassList().size(),  0);
        assertEquals(project.findPackage("default package"), project.getPackageList().get(1));
    }
    
    @Test
    public void testDefaultPackage(){
        assertEquals(PackageModel.class, project.getDefaultPackage().getClass());
        assertEquals("default package", project.getDefaultPackage().name());
        try {
            project.removePackage(project.getDefaultPackage());
            project.getDefaultPackage();
        } catch ( PackageDoesNotExistException ex) {
            assertEquals(DoesNotExistException.class, ex.getClass());
        }
    }
    
    @Test
    public void testAddPackage() {
        PackageModel newPackage = null;
        try {
            newPackage = project.addPackage(new PackageModel("New Package"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(project, newPackage.getParent());
        assertEquals(newPackage ,project.findPackage("New Package"));
        
        assertEquals(newPackage, project.getPackageList().getLast());
        assertTrue(project.getPackageList().size() == 3);
        try {
            project.addPackage(new PackageModel("New Package"));
            fail("NameAlreadyExistsException not thrown");
        } catch (NameAlreadyExistsException ex) {
        }
        assertTrue(project.getPackageList().size() == 3);
    }
    
    @Test
    public void testGetPackageList(){
        assertEquals(2, project.getPackageList().size());
        assertEquals(ProjectModel.allPackage, project.getPackageList().getFirst());
        assertEquals("default package", project.getPackageList().getLast().name());
    }
    
    @Test
    public void testAddClass(){
        PackageModel testPackage = null;
        try {
            testPackage = project.addPackage(new PackageModel("Test Package"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        ClassModel newClass = null;
        try {
            newClass = testPackage.addClass(new ClassModel("NewClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, project.getClassList().size());
        assertEquals(newClass, project.findClass("NewClass"));
        try {
            testPackage.addClass(new ClassModel("NewClass"));
            fail("Expected exception was not thrown");
        } catch (NameAlreadyExistsException ex) {}
    }
    
    @Test
    public void testRemovePackage(){
        PackageModel packageToBeRemoved = null;
        try {
            packageToBeRemoved = project.addPackage(new PackageModel("PackageToBeRemoved"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(packageToBeRemoved, project.findPackage("PackageToBeRemoved"));
        assertTrue(project.getPackageList().contains(packageToBeRemoved));
        try {
            project.removePackage(packageToBeRemoved);
        } catch (PackageDoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertEquals(null, project.findPackage("PackageToBeRemoved"));
        assertFalse(project.getPackageList().contains(packageToBeRemoved));
    }
    
    @Test
    public void testDescription(){
        //remember, every project starts out with a package called 'default package'
        project.setUserName("Barry Allen");
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
        MainApplication testMain = new MainApplication();
        testMain.setUserName("Barry Allen");
        try {
            project = testMain.addProject(new ProjectModel("New Project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
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
        project.addMethodDefinition(new MethodModel("aMethod"));
        assertEquals(1, projectMethods.size());
        project.addMethodDefinition(new MethodModel("aMethod"));
        assertEquals(1, projectMethods.size());
    }
    
    @Test
    public void testListClasses(){
        assertEquals(0, project.getClassList().size());
        PackageModel aPackage = null;
        ClassModel aClass = null;
        try {
            aPackage = project.addPackage(new PackageModel("New Package"));
            aClass = aPackage.addClass(new ClassModel("AClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, project.getClassList().size());
        ClassModel anotherClass = null;
        try {
            anotherClass = aClass.addClass(new ClassModel("AnotherClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(2, project.getClassList().size());
        ClassModel yetAnotherClass = null;
        try {
            yetAnotherClass = anotherClass.addClass(new ClassModel("YetAnotherClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(3, project.getClassList().size());
        PackageModel anotherPackage = null;
        try {
            anotherPackage = project.addPackage(new PackageModel("Another Package"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        ClassModel classInAnotherPackage = null;
        try {
            classInAnotherPackage = anotherPackage.addClass(new ClassModel("AnotherClassInAnotherPackage"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(4, project.getClassList().size());
        LinkedList classList = project.getClassList();
        assertEquals(aClass, classList.get(0));
        assertEquals(anotherClass, classList.get(1));
        assertEquals(yetAnotherClass, classList.get(2));
        assertEquals(classInAnotherPackage, classList.get(3));
        PackageModel subPackage = null;
        try {
            subPackage = aPackage.addPackage(new PackageModel("Sub Package"));
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(ProjectModelTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ClassModel subPackageClass =
                    subPackage.addClass(new ClassModel("SubPackageClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(5, project.getClassList().size());
        fail("Split me up into smaller tests!!!");
    }
    
    private MethodModel setUpProjectMethod(){
        MethodModel aMethod = null;
        try {
            PackageModel aPackage = project.addPackage(new PackageModel("A Pak"));
            ClassModel aClass = aPackage.addClass(new ClassModel("AClass"));
            aMethod = aClass.addMethod(new MethodModel("aMethod"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        return aMethod;
    }
    
    @Test
    public void testRemoveMethod(){
        HashMap methods = (HashMap)this.getVariableFromClass(project, "methods");
        MethodModel aMethod = this.setUpProjectMethod();
        try {
            project.removeMethod(aMethod);
        } catch (VeryVeryBadException ex) {
            fail(ex.getMessage());
        }
        assertNull(project.getMethodDefinitions(aMethod));
        assertFalse(methods.containsKey(aMethod.name()));
        assertFalse(methods.containsValue(aMethod));
    }
    
    @Test
    public void testGetMethodDefinitions(){
        try {
            PackageModel aPackage = project.addPackage(new PackageModel("A Package"));
            ClassModel aClass = aPackage.addClass(new ClassModel("AClass"));
            MethodModel aMethod = aClass.addMethod(new MethodModel("newMethod"));
            ClassModel anotherClass = aPackage.addClass(new ClassModel("AnotherClass"));
            
            assertEquals(aMethod, project.getMethodDefinitions(aMethod).getFirst());
            assertEquals(1, project.getMethodDefinitions(aMethod).size());
            aMethod = anotherClass.addMethod(new MethodModel("newMethod"));
            assertEquals(2, project.getMethodDefinitions(aMethod).size());
            assertEquals(aMethod, project.getMethodDefinitions(aMethod).getLast());
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void testGetMethodReferences(){
        fail("write me!");
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
        MainApplication main = new MainApplication();
        try {
            project = main.addProject(new ProjectModel("aProject"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(main, project.getMain());
    }
    
    @Test
    public void testAllPackage(){
        assertEquals(PackageModel.class, ProjectModel.allPackage.getClass());
        assertEquals("All", ProjectModel.allPackage.name());
        assertTrue(project.getPackageList().contains(ProjectModel.allPackage));
    }
    
    @Test
    public void testGetDefaultPackage(){
        assertTrue(this.compareStrings("default package", project.getDefaultPackage().name));
    }
    
    @Test
    public void testAddPackageToDefaultPackage(){
        ClassModel aClass = null;
        try {
            aClass = project.getDefaultPackage().addClass(
                    new ClassModel("AClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals("default package", aClass.getParentPackage().name());
    }
    
    @Test
    public void testReservedWords(){
        assertEquals(16, ProjectModel.getReservedWords().size());
    }
}
