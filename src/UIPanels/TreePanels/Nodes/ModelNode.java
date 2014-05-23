/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.TreePanels.Nodes;

import Models.BaseModel;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author arthur
 */
public class ModelNode extends DefaultMutableTreeNode{
    
        
    public ModelNode(BaseModel aModel){
        super(aModel);
    }
    
    public ModelNode(BaseModel aModel, HashMap aMap){
        this(aModel);
        aMap.put(aModel, this);
    }
        
    public BaseModel getModel(){
        return (BaseModel)this.getUserObject();
    }
    
    public ModelNode addNode(ModelNode aNode){
        this.add(aNode);
        return aNode;
    }
    
    public ModelNode removeChildNode(ModelNode aNode, HashMap aMap){
        this.remove(aNode);
        aMap.remove(aNode.getModel());
        return aNode;
    }
    
    public ModelNode remove(HashMap aMap){
        this.removeFromParent();
        aMap.remove(this.getModel());
        return this;
    }
    
    /**
     * Returns the number of nodes in this branch of a tree.
     * For testing purposes only.
     * @return  the total number of child nodes and their children
     */
    public int size(){
        int i=0;
        Enumeration<ModelNode> e = this.breadthFirstEnumeration();
        while(e.hasMoreElements()){
            i++;
            e.nextElement();
        }
        return i;
    }
    
    public void printChildren(){
        System.out.println(this.getModel().toString());
        if(children == null)
            return;
        for(ModelNode m : this.getChildren())
            m.printChildren();
    }
    
    public Vector<ModelNode> getChildren(){
        return children;
    }
    
}
