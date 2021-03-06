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
        this.compareStrings(expected, var.toSourceString());
        var.setScope(ScopeType.NONE);
        expected = "Type x;";
        this.compareStrings(expected, var.toSourceString());
    }
    
    @Test 
    public void testToSourceStringWithInitializedValue(){
        var.setValue("new Type()");
        String expected = "private Type x = new Type();";
        this.compareStrings(expected, var.toSourceString());
    }

    @Test
    public void testGetPath() {
        fail("The test case is a prototype.");
    }
    
    @Test
    public void testSetName(){
        var.setName("aNameString");
        this.compareStrings("aNameString", var.name());
        var.setName("aNameString");
        assertEquals("aNameString", var.name());
    }
    
    @Test
    public void testSetValue(){
        var.setValue("5");
        assertEquals("5",var.getValue());
    }
    
    @Test
    public void testFinalVar(){
        var.setFinal(true);
        this.compareStrings("private final Type x;", var.toSourceString());
    }
    
}
