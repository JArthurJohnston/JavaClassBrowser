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
    
}
