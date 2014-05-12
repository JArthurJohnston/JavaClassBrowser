/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import Models.BaseModel;
import UIModels.BrowserUIController;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Arthur
 */
public class BaseTreePanel extends BasePanel {
    protected DefaultMutableTreeNode rootNode;
    
    protected JTree tree(){
        return null;
    }
    
    
    
    public DefaultMutableTreeNode setRootNode(BaseModel aModel){
        rootNode = new DefaultMutableTreeNode(aModel);
        return rootNode;
    }
    
    protected void setUpListener(){
        this.tree().getSelectionModel()
                .setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.tree().addTreeSelectionListener(new TreeSelectionListener(){
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                controller.setSelected(
                        ((ModelNode)tree()
                                .getLastSelectedPathComponent()).getModel());
            }
            
        });
    }
    
    public void addModelToTree(BaseModel aModel){
        
    }
    
    protected void setTreeModel(){
        
    }
    
    
    
    
    
}
