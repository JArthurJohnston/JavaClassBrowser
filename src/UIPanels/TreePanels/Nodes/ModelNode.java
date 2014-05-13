/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.TreePanels.Nodes;

import Models.BaseModel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author arthur
 */
public class ModelNode extends DefaultMutableTreeNode{
    
        
    public ModelNode(BaseModel aModel){
        super(aModel);
    }
        
    public BaseModel getModel(){
        return (BaseModel)this.getUserObject();
    }
    
    /**
     * Returns the number of nodes in this branch of a tree.
     * For testing purposes only.
     * @return  the total number of child nodes and their children
     */
    public int size(){
        int s = 1;
        if(children != null)
            for (Object c : children) {
                s += ((ModelNode)c).size();
        }
        return s;
    }
    
}
