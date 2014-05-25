/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import Models.ClassModel;

/**
 *
 * @author arthur
 */
public class ClassModelBuffer extends BaseModelBuffer{
    
    public ClassModelBuffer(ClassModel baseEntity){
        super(baseEntity);
    }
    
    @Override
    public ClassModel getEntity(){
        return (ClassModel)entity;
    }
    
    @Override
    public boolean isValid(){
        if(this.name != entity.name())
            return entity.getProject().okToAddClass(name);
        return true;
    }
    
    public String newClassName(){
        String newName = "NewClass";
        if(entity.getProject().okToAddClass(newName))
            return newName;
        int i = 0;
        while(!entity.getProject().okToAddClass(newName + ++i));
        return newName+i;
    }
    
}
