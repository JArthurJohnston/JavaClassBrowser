/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels.TreePanels;

import Models.*;
import UIModels.BrowserUIController;
import UIPanels.BasePanel;
import UIPanels.TreePanels.Nodes.*;
import java.util.Enumeration;
import java.util.HashMap;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Arthur
 */
public class BaseTreePanel extends BasePanel {
    protected ModelNode rootNode;
    protected HashMap<BaseModel, ModelNode> treeMap;
    
    public BaseTreePanel(){
        treeMap = new HashMap();
    }
    
    protected HashMap getHash(){
        return treeMap;
    }
    
    protected ModelNode createNodeFromModel(BaseModel aModel){
        return new ModelNode(aModel, treeMap);
    }
    
    public ModelNode addModelToNode(ModelNode parent, BaseModel aModel){
        return this.expandToNode(
                parent.addNode(
                        this.createNodeFromModel(aModel)));
    }
    
    public ModelNode addModelToRoot(BaseModel aModel){
        return this.expandToNode(
                this.addModelToNode(
                        this.getRootNode(), aModel));
    }
    
    protected ModelNode expandToNode(ModelNode aNode){
        this.tree().expandPath(new TreePath(aNode.getPath()));
        return aNode;
    }
    
    @Override
    public void setModel(BrowserUIController aController){
        this.controller = aController;
    }
    
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
    
    public void expandAll(){
        for(int i=0; i< this.tree().getRowCount(); i++)
            this.tree().expandRow(i);
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
    
    protected ModelNode getNodeFromModel(BaseModel aModel){
        return treeMap.get(aModel);
    }
    
    @Override
    public void selectionChanged(BaseModel aModel){
        //this doesnt work.
        rootNode = null;
    }
    
    public void removeNode(ModelNode aNode){
        aNode.remove(treeMap);
    }
    
    /**
     * Returns the number of nodes in the tree.
     * Not counting the root node.
     * this is for testing only.
     * @return 
     */
    public int getTreeSize(){
        int i=-1; //ignoring the root node
        Enumeration e = this.getRootNode().breadthFirstEnumeration();
        while(e.hasMoreElements()){
            i++;
            e.nextElement();
        }
        return i;
    }
    
    /**
     * Only works if the tree has been expanded.
     * @param x 
     */
    public void setSelectedIndex(int x){
        this.tree().setSelectionPath(this.tree().getPathForRow(x));
    }
    
    public int getHashSize(){
        return treeMap.values().size();
    }
    
    public boolean isEmpty(){
        return this.getTreeSize() == 0;
    }
    
    public boolean contains(BaseModel aModel){
        return treeMap.containsKey(aModel);
    }
}
