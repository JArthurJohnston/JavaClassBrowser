/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.ClassDoesNotExistException;
import Exceptions.MethodDoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Types.ReturnType;
import Types.ScopeType;
import java.util.ArrayList;
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
    private MainApplication main;
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
        main = new MainApplication();
        parentProject = new ProjectModel(main,"Parent Project");
        parentPackage = parentProject.addPackage(new PackageModel(parentProject, "Parent Package"));
        testClass = parentPackage.addClass(new ClassModel(parentPackage,"InstanceClass"));
    }
    
    @After
    public void tearDown() {
        testClass = null;
        parentPackage = null;
        parentProject = null;
        main = null;
    }
    
    @Test
    public void testInitialize(){
        assertEquals("InstanceClass", testClass.name());
        assertEquals(parentProject, testClass.getProject());
        assertEquals(parentPackage, testClass.getParent());
        assertEquals(ClassModel.class, testClass.getClass());
        assertEquals(0, testClass.getClassList().size());
        assertEquals(ArrayList.class, testClass.getClassList().getClass());
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
        ClassModel newSubClass = null;
        try {
            newSubClass = testClass.addClass(new ClassModel(testClass, "NewSubClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(testClass, newSubClass.getParent());
        assertEquals(parentProject, newSubClass.getProject());
        assertEquals(2, parentProject.getClasses().size());
        assertEquals(1, testClass.getClassList().size());
        assertEquals(newSubClass, testClass.getClassList().get(0));
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
            assertEquals(2, parentProject.getClasses().size());
            assertEquals(1, testClass.getClassList().size());
            assertTrue(testClass.getClassList().contains(classToBeRemoved));
        } catch (NameAlreadyExistsException ex) {}
        try {
            testClass.removeClass("ClassToBeRemoved");
        } catch (ClassDoesNotExistException ex) {
            fail(ex.getMessage());
        }
        try {
            testClass.removeClass("ClassToBeRemoved");
            fail("Exception not thrown");
        } catch (ClassDoesNotExistException ex) {}
        assertFalse(parentProject.getClasses().containsKey("ClassToBeRemoved"));
        assertFalse(testClass.getClassList().contains(classToBeRemoved));
    }
    
    @Test
    public void testAddMethod(){
        MethodModel newMethod = new MethodModel();
        try {
            newMethod = testClass.addMethod("newMethod");
        } catch (NameAlreadyExistsException ex) {
            fail("Exception thrown when it shouldnt");
        }
        assertEquals(testClass, newMethod.getParent());
        assertEquals(1, testClass.getMethods().size());
        try {
            testClass.addMethod("newMethod");
            fail("Exception not thrown");
        } catch (NameAlreadyExistsException ex) {
            assertEquals(NameAlreadyExistsException.class, ex.getClass());
        }
    }
    
    @Test
    public void testRemoveMethod(){
        try {
            testClass.addMethod("aMethodForTesting");
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
    public void testAddVariable(){
        VariableModel var = new VariableModel(ScopeType.PUBLIC, testClass, "aVar");
        ArrayList varList = (ArrayList)this.getVariableFromClass(testClass, "variables");
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
    public void testRemoveVariable(){
        fail("write me!");
    }
}
