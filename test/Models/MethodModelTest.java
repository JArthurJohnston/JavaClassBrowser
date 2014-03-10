/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import Exceptions.NameAlreadyExistsException;
import Internal.BaseTest;
import Types.ClassType;
import Types.ScopeType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author arthur
 */
public class MethodModelTest extends BaseTest{
    private MethodModel method;
    
    public MethodModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        method = new MethodModel(this.setUpParentClass(), 
                ClassType.INSTANCE, ScopeType.PUBLIC, "aMethod");
    }
    
    @After
    public void tearDown() {
        method = null;
    }
    
    private ClassModel setUpParentClass(){
        ProjectModel aProject = new ProjectModel();
        PackageModel aPackage = new PackageModel(aProject, "a package");
        return new ClassModel(aPackage, "ParentClass");
    }

    /**
     * Test of isMethod method, of class MethodModel.
     */
    @Test
    public void testIsMethod() {
        assertTrue(method.isMethod());
    }

    /**
     * Test of setSource method, of class MethodModel.
     */
    @Test
    public void testSetSource() {
        
        assertEquals("", method.getSource());
        method.setSource("some text for source code");
        assertEquals("some text for source code", method.getSource());
    }
    
    @Test
    public void testMethodSignature(){
        MethodModel otherMethod = new MethodModel("anotherMethod");
        assertFalse(method.matchSignature(otherMethod));
        otherMethod.setName("aMethod");
        assertTrue(method.matchSignature(otherMethod));
        VariableModel intVar = new VariableModel(ScopeType.NONE, new ClassModel("Int"),"x");
        VariableModel charVar = new VariableModel(ScopeType.NONE, new ClassModel("Char"),"y");
        ArrayList vars = new ArrayList();
        vars.add(intVar);
        vars.add(charVar);
        otherMethod.setParameters(vars);
        assertFalse(method.matchSignature(otherMethod));
        method.setParameters(vars);
        assertTrue(method.matchSignature(otherMethod));
    }
    
    
    @Test
    public void testParameters(){
        ArrayList params = (ArrayList)this.getVariableFromClass(method, "parameters");
        assertEquals(0, params.size());
    }

    /**
     * Test of getType method, of class MethodModel.
     */
    @Test
    public void testInstanceOrStatic() {
        assertEquals(ClassType.INSTANCE, method.getType());
        method.setType(ClassType.CLASS);
        assertEquals(ClassType.CLASS, method.getType());
    }

    /**
     * Test of scope method, of class MethodModel.
     */
    @Test
    public void testScope() {
        assertEquals(ScopeType.PUBLIC, method.scope());
        method.setScope(ScopeType.PROTECTED);
        assertEquals(ScopeType.PROTECTED, method.getScope());
        method.setScope(ScopeType.PRIVATE);
        assertEquals(ScopeType.PRIVATE, method.getScope());
    }
    
    @Test
    public void testReturnType(){      
        fail("return types should be a ClassModel, not an Enum");
    }

    /**
     * Test of getPath method, of class MethodModel.
     */
    @Test
    public void testGetPath() {
        fail("The test case is a prototype.");
    }

    /**
     * Test of toSourceString method, of class MethodModel.
     */
    @Test
    public void testToSourceString() {
        method.setSource("Some.testCode();");
        String expected = "public void aMethod(){\n"
                + "Some.testCode();\n"
                + "}";
        assertTrue(this.compareStrings(expected, method.toSourceString()));
    }
    
    @Test
    public void testReferences(){
        assertEquals(LinkedList.class, method.getReferences().getClass());
        assertTrue(method.getReferences().isEmpty());
        MethodModel newMethodDef = null;
        try {
           newMethodDef =  new ClassModel("anotherClass").addMethod("aMethod");
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        //assertTrue(method.getReferences().contains(newMethodDef));
        fail("need to write logic to check for method references in method source");
    }
    
    @Test
    public void testAddReference(){
        
        ClassModel aClass = new ClassModel(method.parent.getParentPackage(), "AnotherClass");
        try {
            aClass.addMethod("aMethod");
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
       fail("same reason as above");
    }
    
}
