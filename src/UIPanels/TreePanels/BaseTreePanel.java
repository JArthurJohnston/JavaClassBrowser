/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels.TreePanels;

import Models.*;
import UIPanels.BasePanel;
import UIPanels.TreePanels.Nodes.*;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Arthur
 */
public class BaseTreePanel extends BasePanel {
    protected DefaultMutableTreeNode rootNode;
    
    protected JTree tree(){
        return new JTree();
    }
    
    protected ModelNode getRootNode(){
        return null;
    }
    
    protected void setUpTree(){
        this.tree().setModel(new DefaultTreeModel(this.getRootNode()));
        this.setUpSelectionListener();
    }
    
    protected void setUpSelectionListener(){
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
    
    protected DefaultTreeModel treeModel(){
        return (DefaultTreeModel)this.tree().getModel();
    }
    
    public TreePath getSelectedPath(){
        return this.tree().getLeadSelectionPath();
    }
    
    public ModelNode getSelectedNode(){
        return (ModelNode)this.tree().getLastSelectedPathComponent();
    }
    
    public void addToSelected(BaseModel aModel){
        TreePath selection = this.getSelectedPath();
        if(selection != null){
            ModelNode newNode;
            ((ModelNode)selection.getLastPathComponent())
                    .add(newNode = this.getNodeFromModel(aModel));
            this.treeModel().nodeStructureChanged(
                (ModelNode)selection.getLastPathComponent());
            this.tree().scrollPathToVisible(new TreePath(newNode.getPath()));
        }
    }
    
    protected ModelNode getNodeFromModel(BaseModel aModel){
        if(aModel.isPackage())
            return new PackageNode((PackageModel)aModel);
        if(aModel.isClass())
            return new ClassNode((ClassModel)aModel);
        return new ModelNode(aModel);
    }
}
