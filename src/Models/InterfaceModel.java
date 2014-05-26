/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import Exceptions.BaseException;
import Exceptions.VeryVeryBadException;

/**
 *
 * @author arthur
 */
public class InterfaceModel extends ClassModel{
    
    public InterfaceModel(String name){
        super(name);
    }
    
    @Override
    public AbstractMethodModel addMethod(MethodModel newMethod) throws BaseException {
        if(!newMethod.isAbstract())
            throw new VeryVeryBadException(this, newMethod);
        return (AbstractMethodModel)super.addMethod(newMethod);
    }
    
    @Override
    public boolean isInterface(){
        return true;
    }
    
    
    
}
