/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import Internal.BaseTest;
import LanguageBase.JavaLang;
import Types.ClassType;
import Types.ScopeType;
import java.util.ArrayList;
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
        method = this.addMethodToClass("aMethod", parentClass);
    }
    
    @After
    public void tearDown() {
        method = null;
    }
    
    private ClassModel setUpParentClass(){
        ProjectModel aProject = new ProjectModel("A Project");
        PackageModel aPackage = 
                this.addPackageToProject("a package", aProject);
        return this.addClassToParent("ParentClass", aPackage);
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
        assertEquals(ProjectModel.class, method.project.getClass());
        assertEquals("A Project", method.project.name());
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
        MethodModel otherMethod = new MethodModel(new ClassModel("AClass"),"anotherMethod");
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
        fail("Write more of me!");
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
        ProjectModel parentProject = method.project;
        PackageModel parentPackage = method.getParentPackage();
        assertEquals(1, parentProject.getMethodDefinitions(method).size());
        assertEquals(parentProject.getMethodDefinitions(method).getFirst(), method);
        ClassModel aClass = this.addClassToParent("anotherClass", parentPackage);
        MethodModel anotherMethod = this.addMethodToClass("aMethod", aClass);
        assertEquals(2, parentProject.getMethodDefinitions(method).size());
        assertEquals(parentProject.getMethodDefinitions(method).getFirst(), method);
        assertEquals(anotherMethod, parentProject.getMethodDefinitions(method).getLast());
    }
    
    @Test
    public void testGetParentPackage(){
        ProjectModel aProject = new ProjectModel("aProject");
        PackageModel aPackage = this.addPackageToProject("APackage", aProject);
        ClassModel aClass = this.addClassToParent("AClass", aPackage);
        method = this.addMethodToClass("aMethod", aClass);
        assertEquals(aPackage, method.getParentPackage());
    }
}
