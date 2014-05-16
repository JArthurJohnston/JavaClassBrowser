/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.TreePanels.Nodes;

import Models.ClassModel;
import java.util.HashMap;

/**
 *
 * @author arthur
 */
public class ClassNode extends ModelNode{
    
    public static ClassNode getDefaultRoot(){
        return new ClassNode(ClassModel.getObjectClass());
    }
        
    private ClassNode(ClassModel aClass){
        super(aClass);
    }
    
    public ClassNode(ClassModel aClass, HashMap aMap){
        super(aClass);
        aMap.put(aClass, this);
        this.generateTreeFromClass(aMap);
    }
        
    private void generateTreeFromClass(HashMap aMap){
        for(ClassModel c : this.getModel().getSubClasses())
            this.add(new ClassNode(c, aMap));
    }
        
    @Override
    public ClassModel getModel(){
        return (ClassModel)super.getModel();
    }
    
}
