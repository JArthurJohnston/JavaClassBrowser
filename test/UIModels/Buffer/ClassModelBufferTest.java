/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import Models.ClassModel;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class ClassModelBufferTest extends BaseBufferTest{
    private ClassModel baseClass;
    private ClassModelBuffer buffer;
    
    public ClassModelBufferTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        baseClass= null;
        buffer = null;
    }
    
    private void setUpClass(){
        
    }

    @Test
    public void testSomeMethod() {
    }
    
}
