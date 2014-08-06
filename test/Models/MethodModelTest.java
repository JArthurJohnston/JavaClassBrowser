/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import Exceptions.AlreadyExistsException;
import Exceptions.BaseException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Types.ClassType;
import Types.ScopeType;
import java.util.LinkedList;
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
        super.setUp();
        
        try {
            ClassModel parentClass = parentPackage
                    .addClass(new ClassModel("ParentClass"));
            method = parentClass.addMethod(new MethodModel("aMethod"));
        } catch (BaseException ex) {
            fail(ex.getMessage());
        }
    }
    
    @After
    public void tearDown() {
        super.tearDown();
        method = null;
    }
    
    private ClassModel setUpClassWithName(String className){
        ClassModel parentClass = null;
        try {
            parentClass = parentPackage.addClass(new ClassModel(className));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        return parentClass;
    }

    /**
     * Test of isMethod method, of class MethodModel.
     */
    @Test
    public void testIsMethod() {
        assertTrue(method.isMethod());
    }
    
    @Test
    public void testIsConstructor(){
        assertFalse(method.isConstructor());
        method.setName("ParentClass");
        assertTrue(method.isConstructor());
    }
    
    @Test
    public void testInheritedFields(){
        assertEquals(ClassModel.class, method.getParent().getClass());
        assertEquals("ParentClass", method.getParent().name());
        assertEquals(ProjectModel.class, method.getProject().getClass());
        assertEquals(parentProject, method.getProject());
        assertEquals(PackageModel.class, method.getParentPackage().getClass());
        assertEquals(method.getParentPackage(), method.parent.getParentPackage());
        assertEquals(parentPackage, method.getParentPackage());
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
        MethodModel otherMethod = null;
        try {
            otherMethod = this.setUpClassWithName("AClass")
                    .addMethod(new MethodModel("anotherMethod"));
        } catch (BaseException ex) {
            fail(ex.getMessage());
        }
        assertFalse(method.hasSignatureOf(otherMethod));
        otherMethod.setName("aMethod");
        /*changing the name of the method changes its signature, and its 
        references and definitions
        this would fall under some kind of refactoring logic...
        */
        assertTrue(method.hasSignatureOf(otherMethod));
        VariableModel intVar = 
                new VariableModel(ScopeType.NONE, new ClassModel("Int"),"x");
        VariableModel charVar = 
                new VariableModel(ScopeType.NONE, new ClassModel("Char"),"y");
        LinkedList vars = new LinkedList();
        vars.add(intVar);
        vars.add(charVar);
        otherMethod.setArguments(vars);
        assertFalse(method.hasSignatureOf(otherMethod));
        method.setArguments(vars);
        assertTrue(method.hasSignatureOf(otherMethod));
    }
    
    @Test
    public void testParameters(){
        LinkedList params = 
                (LinkedList)this.getVariableFromClass(method, "arguments");
        assertEquals(0, params.size());
        fail("Write more of me!");
    }
    
    @Test
    public void testGetParentClass(){
        assertSame(ClassModel.class, method.getParentClass().getClass());
        assertEquals("ParentClass", method.getParentClass().name());
    }

    /**
     * Test of getType method, of class MethodModel.
     */
    @Test
    public void testInstanceOrStatic() {
        assertEquals(ClassType.INSTANCE, method.getType());
        method.setType(ClassType.STATIC);
        assertEquals(ClassType.STATIC, method.getType());
    }
    

    /**
     * Test of scope method, of class MethodModel.
     */
    @Test
    public void testScope() {
        assertEquals(ScopeType.NONE, method.getScope());
        method.setScope(ScopeType.PRIVATE);
        assertEquals(ScopeType.PRIVATE, method.getScope());
        method.setScope(ScopeType.PROTECTED);
        assertEquals(ScopeType.PROTECTED, method.getScope());
        method.setScope(ScopeType.PRIVATE);
        assertEquals(ScopeType.PRIVATE, method.getScope());
    }
    
    @Test
    public void testReturnType(){
        assertEquals(ClassModel.getPrimitive("void"), method.getReturnType());
        ClassModel newReturn = new ClassModel("AClass");
        method.setReturnType(newReturn);
        assertEquals(newReturn, method.getReturnType());
        method.setReturnType(ClassModel.getPrimitive("void"));
        assertEquals(ClassModel.getPrimitive("void"), method.getReturnType());
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
        method.setReturnType(ClassModel.getPrimitive("void"));
        method.setScope(ScopeType.PRIVATE);
        method.setSource("Some.testCode();");
        String expected = "private void aMethod(){\n"
                + "Some.testCode();\n"
                + "}";
        this.compareStrings(expected, method.toSourceString());
    }
    
    @Test
    public void testToSourceStringWithNoneScope(){
        method.setReturnType(ClassModel.getPrimitive("void"));
        String expected = "void aMethod(){"+ "\n\n"+ "}";
        this.compareStrings(expected, method.toSourceString());
    }
    
    @Test
    public void testReferences(){
        assertEquals(LinkedList.class, method.getReferences().getClass());
        fail("need to write logic to check for method references in method source");
        /*
        this will have to wait until I write the re-factoring logic.
        */
    }
    
    @Test public void testDefinitions(){
        assertEquals(1, method.getDefinitions().size());
        assertEquals(method, method.getDefinitions().getFirst());
        
        MethodModel anotherMethod = null;
        try {
            ClassModel aClass = method
                    .getParent()
                    .addClass(new ClassModel("AnotherClass"));
            anotherMethod = aClass.addMethod(new MethodModel("aMethod"));
        } catch (BaseException ex) {
            fail(ex.getMessage());
        }
        
        assertEquals(2, method.getDefinitions().size());
        assertEquals(method, method.getDefinitions().getFirst());
        assertEquals(anotherMethod, method.getDefinitions().getLast());
    }
    
    @Test
    public void testGetParentPackage(){
        assertEquals(parentPackage, method.getParentPackage());
    }
    
    @Test
    public void testMethodWithObjectType(){
        method.setReturnType(ClassModel.getObjectClass());
        method.setName("someMethod");
        this.compareStrings("Object someMethod(){\n\n}", method.toSourceString());
    }
}
