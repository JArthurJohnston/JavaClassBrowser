/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Internal;

import java.lang.reflect.Field;                   
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * uses reflection to grab private stuff from objects for testing
 * 
 * @author Arthur
 */
public class BaseTest {
    
    /**
     * gets a private variable from a class.
     * the user must cast the return object into the desired object
     * when this method is called.
     * this is for testing ONLY.
     * 
     * @param fromShell
     * @param componentName
     * @return 
     */
    public Object getVariableFromClass(Object fromShell, String componentName){
        Field privateField = null;
        try {
            privateField = fromShell.getClass().getDeclaredField(componentName);
        } catch (NoSuchFieldException | SecurityException ex) {
            Logger.getLogger(BaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        privateField.setAccessible(true);
        try {
            return privateField.get(fromShell);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(BaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
