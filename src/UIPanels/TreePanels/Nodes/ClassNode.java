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
    ClassModel baseClass;
        
        public ClassNode(ClassModel aClass){
            super(aClass);
            this.generateTreeFromClass();
        }
        
        private void generateTreeFromClass(){
            for(ClassModel c : this.baseClass().getSubClasses())
                this.add(new ClassNode(c));
        }
        
        public void regenerateTreeFromClass(){
            
        }
        
        private ClassModel baseClass(){
            return (ClassModel)this.getUserObject();
        }
        
    @Override
    public ClassModel getModel(){
        return (ClassModel)super.getModel();
    }
    
}
