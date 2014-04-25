/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import Models.ClassModel;
import Models.VariableModel;
import Types.ScopeType;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class VariableModelBufferTest {
    private VariableModel model;
    private VariableModelBuffer buffer;
    
    public VariableModelBufferTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        model = new VariableModel(ScopeType.PRIVATE, new ClassModel("int"), "x");
        buffer = new VariableModelBuffer(model);
    }
    
    @After
    public void tearDown() {
        buffer = null;
        model = null;
    }

    @Test
    public void testInit() {
        assertEquals(model, buffer.getEntity());
        assertTrue(buffer.getEntity().isVariable());
    }
    
}
