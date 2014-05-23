/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.TreePanels.Nodes;

import Internal.BaseTest;
import Models.BaseModel;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author arthur
 */
public class BaseNodeTest extends BaseTest{
    protected HashMap<BaseModel, ModelNode> treeHash;
    
    
    protected void verifyNodeSize(int x){
        assertEquals(x, node().size());
        assertEquals(x, treeHash.size());
    }
    
    protected void printHashNodes(){
        for(ModelNode m : treeHash.values())
            System.out.println(m.getModel().toString());
    }
    
    protected ModelNode node(){
        return null;
    }
    
}
