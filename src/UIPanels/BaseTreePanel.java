/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import Models.BaseModel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Arthur
 */
public class BaseTreePanel extends BasePanel {
    
    protected JTree tree(){
        return null;
    }
    
    protected void setUpTreeModel(BaseModel aModel){
        this.tree().setModel(DefaultTreeModel(new DefaultMutableTreeNode(aModel)));
        
    }
    
    protected void setUpListener(){
        this.tree().getSelectionModel()
                .setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.tree().addTreeSelectionListener(new TreeSelectionListener(){
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
        });
    }
    
    public void addModelToTree(BaseModel aModel){
        
    }
    
    protected void setTreeModel(){
        
    }
    
}
