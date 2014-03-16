/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.CannotBeDeletedException;
import Exceptions.ClassDoesNotExistException;
import Exceptions.DoesNotExistException;
import Exceptions.MethodDoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Exceptions.VeryVeryBadException;
import Internal.BaseTest;
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
        assertEquals(ArrayList.class, testClass.getClassMethods().getClass());
        assertEquals(ArrayList.class, testClass.getInstanceMethods().getClass());
        assertEquals(ArrayList.class, testClass.getConstructors().getClass());
        assertEquals(0, testClass.getConstructors().size());
                
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
        ClassModel classToBeRemoved = null;
        try {
            classToBeRemoved = testClass.addClass(new ClassModel(testClass, "ClassToBeRemoved"));
        } catch (NameAlreadyExistsException ex) {}
        assertEquals(2, parentProject.getClasses().size());
        assertTrue(testClass.getClassList().contains(classToBeRemoved));
        assertEquals(parentPackage, classToBeRemoved.getParentPackage());
        try {
            classToBeRemoved.remove();
        } catch (VeryVeryBadException | CannotBeDeletedException ex) {
            fail(ex.getMessage());
        }
        //if the above were in java 6 youd have to use the  || operator
        assertFalse(testClass.getClassList().contains(classToBeRemoved));
        assertFalse(parentProject.getClasses().containsKey("ClassToBeRemoved"));
        this.addClassToParent(classToBeRemoved, testClass);
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
        ClassModel subClass = 
                this.addClassToParent(new ClassModel(testClass, "SubClass"), testClass);
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
        ClassModel newClass = 
                this.addClassToParent(
                        new ClassModel(testClass, "NewClass"), testClass);
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
}
