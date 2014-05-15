/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels.TreePanels;

import Models.*;
import UIPanels.BasePanel;
import UIPanels.TreePanels.Nodes.*;
import java.util.Collection;
import java.util.HashMap;
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
    protected TreeMap treeMap;
    
    protected JTree tree(){
        return new JTree();
    }
    
    protected ModelNode getRootNode(){
        treeMap = new TreeMap();
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
    
    protected ModelNode getNodeFromModel(BaseModel aModel){
        if(aModel.isPackage())
            return new PackageNode((PackageModel)aModel);
        if(aModel.isClass())
            return new ClassNode((ClassModel)aModel);
        return new ModelNode(aModel);
    }
    
    public int getTreeSize(){
        return treeMap.getNodes().size();
    }
    
    
    /**
     * for fast access to every node in the tree
     */
    protected class TreeMap {
        private final HashMap <BaseModel, ModelNode> treeHash;
        
        public TreeMap(){
            treeHash = new HashMap();
        }
        
        public ModelNode getNodeForModel(BaseModel aModel){
            return treeHash.get(aModel);
        }
        
        public ModelNode addModel(BaseModel aModel){
            ModelNode newNode = new ModelNode(aModel);
            treeHash.put(aModel, new ModelNode(aModel));
            return newNode;
        }
        
        public void removeModel(BaseModel aModel){
            treeHash.get(aModel).removeFromParent();
        }
        
        public Collection<ModelNode> getNodes(){
            return treeHash.values();
        }
    }
}
