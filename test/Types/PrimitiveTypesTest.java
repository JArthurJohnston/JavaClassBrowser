/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Types;

import Models.ClassModel;
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
public class PrimitiveTypesTest {
    
    public PrimitiveTypesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of values method, of class PrimitiveTypes.
     */
    @Test
    public void testEqualityOfClasses(){
        System.out.println(PrimitiveTypes.CHAR.toString());
        ClassModel aVoid = PrimitiveTypes.VOID.getPrimitiveClass();
        ClassModel bVoid = PrimitiveTypes.VOID.getPrimitiveClass();
        assertEquals(aVoid, bVoid);
    }
    
}
