/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Types;

import Models.ClassModel;

/**
 *
 * @author arthur
 */
public enum PrimitiveTypes {
    VOID(new ClassModel("Void")),
    INT(new ClassModel("Integer")), 
    CHAR(new ClassModel("Character"));
    
    private final ClassModel primitiveClass;
    
    private PrimitiveTypes(ClassModel aClass){
        primitiveClass = aClass;
    }
    
    public ClassModel getPrimitiveClass(){
        return primitiveClass;
    }
}
