/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.TreePanels;

import Internal.BaseTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author arthur
 */
public class BaseTreePanelTest extends BaseTest{
    
    protected BaseTreePanel getPanel(){
        return null;
    }
    
    protected void verifyTreeSize(int x){
        assertEquals(x, getPanel().getRootNode().size());
        assertEquals(x, getPanel().getHash().size());
    }
    
}
