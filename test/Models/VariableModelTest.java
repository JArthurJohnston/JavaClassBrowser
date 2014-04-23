/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import Internal.BaseTest;
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
public class VariableModelTest extends BaseTest{
    private VariableModel var;
    
    public VariableModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ClassModel type = new ClassModel("Type");
        var = new VariableModel(ScopeType.PRIVATE, type, "x");
    }
    
    @After
    public void tearDown() {
        var = null;
    }

    /**
     * Test of getType method, of class VariableModel.
     */
    @Test
    public void testGetType() {
        assertEquals(ClassModel.class, var.getObjectType().getClass());
    }

    /**
     * Test of setType method, of class VariableModel.
     */
    @Test
    public void testSetObjectType() {
        ClassModel newType = new ClassModel("NewType");
        var.setObjectType(newType);
        assertEquals(newType, var.getObjectType());
    }

    /**
     * Test of toSourceString method, of class VariableModel.
     */
    @Test
    public void testToSourceString() {
        String expected = "private Type x;";
        assertTrue(this.compareStrings(expected, var.toSourceString()));
        var.setScope(ScopeType.NONE);
        expected = "Type x;";
        assertTrue(this.compareStrings(expected, var.toSourceString()));
    }
    
    @Test 
    public void testToSourceStringWithInitializedValue(){
        String expected = "private Type x = new Type()";
        assertTrue(this.compareStrings(expected, var.toSourceString()));
    }

    @Test
    public void testGetPath() {
        fail("The test case is a prototype.");
    }
    
    @Test
    public void testVarParser(){
        String source = "private Type x = new Type();";
        System.out.println("__Testing Parser__");
        VariableModel newVar = VariableModel.parseSource(source);
        
        assertTrue(var.parseDeclaration(source));
        assertEquals("new Type()", var.getValue());
    }
    
    @Test
    public void testParseDeclaration(){
        String source = "Integer x;";
        assertTrue(var.parseDeclaration(source));
        source = "private Integer x;";
        assertTrue(var.parseDeclaration(source));
        source = "private static Integer x;";
        assertTrue(var.parseDeclaration(source));
        source = "private static Integer x;";
        assertTrue(var.parseDeclaration(source));
        source = "static private Integer x;";
        assertTrue(var.parseDeclaration(source));
    }
    
    @Test
    public void testParseDeclarationWithFinalContainsValue(){
        String source = "final Integer x = 4;";
        assertTrue(var.parseDeclaration(source));
        source = "final Integer x;";
        assertFalse(var.parseDeclaration(source));
        
    }
    
    @Test
    public void testFinalVar(){
        fail();
        //need to test/figure out the 'final' modifier
    }
    
}
