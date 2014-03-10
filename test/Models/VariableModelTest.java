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
        assertEquals(ClassModel.class, var.getType().getClass());
    }

    /**
     * Test of setType method, of class VariableModel.
     */
    @Test
    public void testSetType() {
        ClassModel newType = new ClassModel("NewType");
        var.setType(newType);
        assertEquals(newType, var.getType());
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
        //set var initial value
        String expected = "private Type x = new Type()";
        assertEquals("private Type x = new Type()", var.toSourceString());
    }

    @Test
    public void testGetPath() {
        fail("The test case is a prototype.");
    }
    
}
