/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import Exceptions.NameAlreadyExistsException;
import Internal.BaseTest;
import LanguageBase.JavaLang;
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
        ClassModel parentClass = this.setUpParentClass();
        try {
            method = parentClass.addMethod(new MethodModel("aMethod"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
    }
    
    @After
    public void tearDown() {
        method = null;
    }
    
    private ClassModel setUpParentClass(){
        ClassModel aClass = null;
        try {
            MainApplication main = new MainApplication();
            ProjectModel aProject = main.addProject(new ProjectModel("A Project"));
            PackageModel aPackage = aProject.addPackage(new PackageModel("a package"));
            aClass = aPackage.addClass(new ClassModel("ParentClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        return aClass;
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
        fail("Write me!");
    }
    
    @Test
    public void testInheritedFields(){
        assertEquals(ClassModel.class, method.getParent().getClass());
        assertEquals("ParentClass", method.getParent().name());
        assertEquals(ProjectModel.class, method.getProject().getClass());
        assertEquals("A Project", method.getProject().name());
        assertEquals(PackageModel.class, method.getParentPackage().getClass());
        assertEquals(method.getParentPackage(), method.parent.getParentPackage());
        assertEquals("a package", method.getParentPackage().name());
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
            otherMethod = new ClassModel("AClass").addMethod(new MethodModel("anotherMethod"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertFalse(method.matchSignature(otherMethod));
        otherMethod.setName("aMethod");
        assertTrue(method.matchSignature(otherMethod));
        VariableModel intVar = new VariableModel(ScopeType.NONE, new ClassModel("Int"),"x");
        VariableModel charVar = new VariableModel(ScopeType.NONE, new ClassModel("Char"),"y");
        LinkedList vars = new LinkedList();
        vars.add(intVar);
        vars.add(charVar);
        otherMethod.setParameters(vars);
        assertFalse(method.matchSignature(otherMethod));
        method.setParameters(vars);
        assertTrue(method.matchSignature(otherMethod));
    }
    
    @Test
    public void testParameters(){
        LinkedList params = (LinkedList)this.getVariableFromClass(method, "parameters");
        assertEquals(0, params.size());
        fail("Write more of me!");
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
        assertEquals(ScopeType.PRIVATE, method.scope());
        method.setScope(ScopeType.PROTECTED);
        assertEquals(ScopeType.PROTECTED, method.getScope());
        method.setScope(ScopeType.PRIVATE);
        assertEquals(ScopeType.PRIVATE, method.getScope());
    }
    
    @Test
    public void testReturnType(){      
        assertEquals(JavaLang.getVoid(), method.getReturnType());
        ClassModel newReturn = new ClassModel("AClass");
        method.setReturnType(newReturn);
        assertEquals(newReturn, method.getReturnType());
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
        String expected = "private void aMethod(){\n"
                + "Some.testCode();\n"
                + "}";
        assertTrue(this.compareStrings(expected, method.toSourceString()));
    }
    
    @Test
    public void testReferences(){
        assertEquals(LinkedList.class, method.getReferences().getClass());
        fail("need to write logic to check for method references in method source");
    }
    
    @Test public void testDefinitions(){
        assertEquals(1, method.getDefinitions().size());
        assertEquals(method, method.getDefinitions().getFirst());
        
        MethodModel anotherMethod = null;
        try {
            ClassModel aClass = method.getParent().addClass(new ClassModel("AnotherClass"));
            anotherMethod = aClass.addMethod(new MethodModel("aMethod"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        
        assertEquals(2, method.getDefinitions().size());
        assertEquals(method, method.getDefinitions().getFirst());
        assertEquals(anotherMethod, method.getDefinitions().getLast());
    }
    
    @Test
    public void testGetParentPackage(){
        assertEquals("a package", method.getParentPackage().name());
    }
}
