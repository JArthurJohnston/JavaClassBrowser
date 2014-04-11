/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.CannotBeDeletedException;
import Exceptions.DoesNotExistException;
import Exceptions.MethodDoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Exceptions.VeryVeryBadException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Types.ClassType;
import Types.ScopeType;
import java.util.ArrayList;
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
public class ClassModelTest extends BaseTest{
    private ProjectModel parentProject;
    private PackageModel parentPackage;
    private ClassModel testClass;
    
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
        parentPackage = parentProject.addPackage(new PackageModel(parentProject, "Parent Package"));
        testClass = parentPackage.addClass(new ClassModel(parentPackage,"InstanceClass"));
    }
    
    @After
    public void tearDown() {
        testClass = null;
        parentPackage = null;
        parentProject = null;
    }
    
    @Test
    public void testInitialize(){
        assertEquals("InstanceClass", testClass.name());
        assertEquals(parentProject, testClass.getProject());
        assertEquals(parentPackage, testClass.getParent());
        assertEquals(ClassModel.class, testClass.getClass());
        assertEquals(LinkedList.class, testClass.getClassList().getClass());
                
    }

    /**
     * Test of addClass method, of class ClassModel.
     */
    @Test
    public void testAddClass() {
        ArrayList packageClasses = (ArrayList)this.getVariableFromClass(parentPackage, "classList");
        ClassModel newSubClass = null;
        try {
            newSubClass = testClass.addClass(new ClassModel(testClass, "NewSubClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(parentPackage.getClassList().contains(newSubClass));
        assertFalse(packageClasses.contains(newSubClass));
        assertEquals(testClass, newSubClass.getParent());
        assertEquals(parentProject, newSubClass.getProject());
        assertEquals(2, parentProject.getClasses().size());
        assertEquals(2, testClass.getClassList().size());
        assertEquals(newSubClass, testClass.getClassList().getLast());
        assertEquals(newSubClass, parentProject.getClasses().get("NewSubClass"));
        try {
            testClass.addClass(new ClassModel(testClass,"NewSubClass"));
            fail("Expected exception not thrown");
        } catch (NameAlreadyExistsException ex) {
            assertEquals(NameAlreadyExistsException.class, ex.getClass());
        }
    }
    
    @Test
    public void testRemoveClass(){
        ClassModel classToBeRemoved = this.addClassToParent("ClassToBeRemoved", parentPackage);
        assertEquals(2, parentProject.getClasses().size());
        assertEquals(parentPackage, classToBeRemoved.getParentPackage());
        try {
            classToBeRemoved.remove();
        } catch (VeryVeryBadException | CannotBeDeletedException ex) {
            fail(ex.getMessage());
        }
        //if the above were in java 6 youd have to use the  || operator
        assertFalse(testClass.getClassList().contains(classToBeRemoved));
        assertFalse(parentProject.getClasses().containsKey("ClassToBeRemoved"));
        classToBeRemoved = this.addClassToParent("ClassToBeRemoved", testClass);
        try {
            testClass.remove();
            fail("exception not thrown");
        } catch (CannotBeDeletedException | VeryVeryBadException ex) {
            assertEquals(CannotBeDeletedException.class, ex.getClass());
        }
        assertTrue(testClass.getClassList().contains(classToBeRemoved));
    }
    
    @Test
    public void testAddMethod(){
        MethodModel newMethod = new MethodModel();
        try {
            newMethod = testClass.addMethod(new MethodModel(testClass, "newMethod"));
        } catch (NameAlreadyExistsException ex) {
            fail("Exception thrown when it shouldnt");
        }
        assertEquals(testClass, newMethod.getParent());
        assertEquals(1, testClass.getMethods().size());
        try {
            testClass.addMethod(new MethodModel(testClass, "newMethod"));
            fail("Exception not thrown");
        } catch (NameAlreadyExistsException ex) {
            assertEquals(NameAlreadyExistsException.class, ex.getClass());
        }
    }
    
    @Test
    public void testRemoveMethod(){
        try {
            testClass.addMethod(new MethodModel(testClass, "aMethodForTesting"));
            assertEquals(1, testClass.getMethods().size());
        } catch (NameAlreadyExistsException ex) {
        }
        try {
            testClass.removeMethod("aMethodForTesting");
        } catch (MethodDoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertEquals(0, testClass.getMethods().size());
        try {
            testClass.removeMethod("aNonExistantMethod");
            fail("Exception not thrown");
        } catch (MethodDoesNotExistException ex) {}
    }
    
    @Test
    public void testMoveToPackage(){
        PackageModel anotherPackage = new PackageModel(parentProject, "AnotherPackage");
        try {
            testClass.moveToPackage(anotherPackage);
        } catch (NameAlreadyExistsException | VeryVeryBadException ex) {
            fail(ex.getMessage());
        }
        assertTrue(anotherPackage.getClassList().contains(testClass));
        assertFalse(parentPackage.getClassList().contains(testClass));
        assertEquals(anotherPackage, testClass.getParentPackage());
        ClassModel subClass = this.addClassToParent("SubClass", testClass);
        assertEquals(anotherPackage, subClass.getParentPackage());
        assertTrue(anotherPackage.getClassList().contains(subClass));
        assertFalse(parentPackage.getClassList().contains(subClass));
    }
    
    @Test
    public void testAddVariable(){
        VariableModel var = new VariableModel(ScopeType.PUBLIC, testClass, "aVar");
        LinkedList varList = (LinkedList)this.getVariableFromClass(testClass, "variables");
        try {
            testClass.addVariable(var);
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, varList.size());
        try {
            testClass.addVariable(var);
            fail("exception not thrown");
        } catch (NameAlreadyExistsException ex) {}
    }
    
    @Test
    public void testGetVariables(){
        assertEquals(0, testClass.getVariables().size());
        try {
            testClass.addVariable(new VariableModel(ScopeType.PRIVATE, testClass, "aVar"));
            testClass.addVariable(new VariableModel(ScopeType.PUBLIC, testClass, "anotherVar"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(2, testClass.getVariables().size());
        try {
            testClass.removeVariable((VariableModel)testClass.getVariables().get(0));
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, testClass.getVariables().size());
        try {
            testClass.removeVariable(new VariableModel(ScopeType.NONE, testClass, "nonExistentVar"));
            fail("Exception not thrown");
        } catch (DoesNotExistException ex) {}
    }
    
    @Test
    public void testRemoveVariable(){
        VariableModel aVar = null;
        try {
            aVar = testClass.addVariable(new VariableModel(ScopeType.PRIVATE, testClass, "aVar"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        try {
            testClass.removeVariable(aVar);
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertTrue(testClass.getVariables().isEmpty());
        try {
            testClass.removeVariable(aVar);
            fail("exception not thrown");
        } catch (DoesNotExistException ex) { }
    }
    
    @Test
    public void testGetClassList(){
        assertEquals(1, testClass.getClassList().size());
        assertEquals(testClass, testClass.getClassList().getFirst());
        ClassModel newClass =  this.addClassToParent("NewClass", testClass);
        assertEquals(newClass, testClass.getClassList().getLast());
        assertEquals(2, testClass.getClassList().size());
        try {
            newClass.remove();
        } catch (CannotBeDeletedException | VeryVeryBadException ex) {
            assertEquals(VeryVeryBadException.class, ex.getClass());
            fail(ex.getMessage());
        }
        assertEquals(1, testClass.getClassList().size());
        assertEquals(testClass, testClass.getClassList().getLast());
        assertEquals(testClass.getClassList().getFirst(), testClass.getClassList().getLast());
    }
    
    @Test
    public void testGetStaticMethods(){
        MethodModel aMethod = null;
        assertTrue(testClass.getStaticMethods().isEmpty());
        try {
            aMethod = testClass.addMethod(new MethodModel(testClass, "aMethod", ClassType.CLASS));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(testClass.getStaticMethods().contains(aMethod));
        try {
            aMethod = testClass.addMethod(new MethodModel(testClass, "anotherMethod", ClassType.INSTANCE));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertFalse(testClass.getStaticMethods().contains(aMethod));
    }
    
    @Test
    public void testGetInstanceMethods(){
        MethodModel aMethod = null;
        assertTrue(testClass.getInstanceMethods().isEmpty());
        try {
            aMethod = testClass.addMethod(new MethodModel(testClass, "aMethod", ClassType.INSTANCE));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(testClass.getInstanceMethods().contains(aMethod));
        try {
            aMethod = testClass.addMethod(new MethodModel(testClass, "anotherMethod", ClassType.CLASS));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertFalse(testClass.getInstanceMethods().contains(aMethod));
    }
    
    @Test
    public void testGetStaticVars(){
        VariableModel aVar = null;
        assertTrue(testClass.getStaticVars().isEmpty());
        try {
            aVar = testClass.addVariable(new VariableModel(ClassType.CLASS, new ClassModel(), "aVar"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(testClass.getVariables().contains(aVar));
        assertTrue(testClass.getStaticVars().contains(aVar));
        try {
            aVar = testClass.addVariable(new VariableModel(ClassType.INSTANCE, new ClassModel(), "anotherVar"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertFalse(testClass.getStaticVars().contains(aVar));
    }
    
    @Test
    public void testGetInstanceVars(){
        VariableModel aVar = null;
        assertTrue(testClass.getStaticVars().isEmpty());
        try {
            aVar = testClass.addVariable(new VariableModel(ClassType.INSTANCE, new ClassModel(), "aVar"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(testClass.getInstanceVars().contains(aVar));
        try {
            aVar = testClass.addVariable(new VariableModel(ClassType.CLASS, new ClassModel(), "anotherVar"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertFalse(testClass.getInstanceVars().contains(aVar));
    }
    
    @Test
    public void testGetReturnTyppe(){
        assertEquals(testClass,testClass.getReturnType());
        fail("need to test for primitive types");
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
            ProjectModel aProject = main.addProject(new ProjectModel(main,"aProject"));
            PackageModel aPackage = aProject.addPackage(new PackageModel(aProject, "a package"));
            testClass = aPackage.addClass(new ClassModel(aPackage, "AClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(main, testClass.getMain());
    }
}
