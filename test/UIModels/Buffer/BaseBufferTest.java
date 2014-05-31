/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import Internal.BaseTest;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 *
 * @author arthur
 */
public class BaseBufferTest extends BaseTest{
    
    protected BaseModelBuffer getBuffer(){
        return null;
    }
    
    private void assertNullOrFalse(Object o){
        if(o != null)
            if((boolean)o != false){
                System.out.println(o.toString());
                fail();
            }
                
    }
    
    protected void validateNullFields(){
        int i = 1;
        for(Object o : this.getBuffer().modelFields()){
            assertNullOrFalse(o);
        }
    }
}
