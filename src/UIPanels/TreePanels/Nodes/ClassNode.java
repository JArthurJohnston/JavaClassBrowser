/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.TreePanels.Nodes;

import Models.ClassModel;

/**
 *
 * @author arthur
 */
public class ClassNode extends ModelNode{
    
    public static ClassNode getDefaultRoot(){
        return new ClassNode(ClassModel.getObjectClass());
    }
        
    public ClassNode(ClassModel aClass){
        super(aClass);
        this.generateTreeFromClass();
    }
        
    private void generateTreeFromClass(){
        for(ClassModel c : this.getModel().getSubClasses())
            this.add(new ClassNode(c));
    }
        
    @Override
    public ClassModel getModel(){
        return (ClassModel)super.getModel();
    }
    
}
