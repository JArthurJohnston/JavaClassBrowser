/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIShells;

import java.awt.Component;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author Arthur
 */
public class BaseUIShellTest {
    
    public Component getComponentFromShell(JFrame fromShell, String componentName){
        try {
            Field privateField = JFrame.class.getDeclaredField(componentName);
            privateField.setAccessible(true);
            return privateField;
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(BaseUIShellTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(BaseUIShellTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
