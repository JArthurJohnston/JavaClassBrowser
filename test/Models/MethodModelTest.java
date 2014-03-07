/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import Internal.BaseTest;
import Types.ClassType;
import Types.ScopeType;
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
    private ClassModel parentClass;
    
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
        this.setUpParentClass();
        method = new MethodModel(parentClass, "aMethod");
    }
    
    @After
    public void tearDown() {
        method = null;
        parentClass = null;
    }
    
    private void setUpParentClass(){
        ProjectModel aProject = new ProjectModel();
        PackageModel aPackage = new PackageModel(aProject, "a package");
        parentClass = new ClassModel(aPackage, "AClass");
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
        String methodSource = (String)this.getVariableFromClass(method, "source");
        assertEquals("", methodSource);
        method.setSource("some text for source code");
        assertEquals("some text for source code", methodSource);
    }

    /**
     * Test of getType method, of class MethodModel.
     */
    @Test
    public void testGetType() {
        assertEquals(null, method.getType());
        fail("The test case is a prototype.");
    }

    /**
     * Test of scope method, of class MethodModel.
     */
    @Test
    public void testScope() {
        System.out.println("scope");
        MethodModel instance = new MethodModel();
        ScopeType expResult = null;
        ScopeType result = instance.scope();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPath method, of class MethodModel.
     */
    @Test
    public void testGetPath() {
        System.out.println("getPath");
        MethodModel instance = new MethodModel();
        String expResult = "";
        String result = instance.getPath();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toSourceString method, of class MethodModel.
     */
    @Test
    public void testToSourceString() {
        System.out.println("toSourceString");
        MethodModel instance = new MethodModel();
        String expResult = "";
        String result = instance.toSourceString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
