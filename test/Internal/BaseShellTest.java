/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Internal;

import MainBase.MainApplication;
import UIShells.BaseUIShell;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class BaseShellTest extends BaseTest{
    protected BaseUIShell shell;
    protected MainApplication main;
    
    public void tearDown(){
        shell.signalClosedAndDispose();
        shell = null;
        main = null;
    }
    
    /**
     * returns true only if the class is BaseShellTest.
     * basically a roundabout way of making the test fail in 
     * any other test class that extends from this BaseShellTest
     * @return 
     */
    protected boolean isBase(){
        return this.getClass() == BaseShellTest.class;
    }
    
    @Test
    public void testCloseAndDispose(){
        assertTrue(this.isBase());
    }
}
