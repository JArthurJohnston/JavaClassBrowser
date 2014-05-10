/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Internal.Mocks;

import Models.ClassModel;
import Models.MethodModel;

/**
 *
 * @author arthur
 */
public class MockClassModel extends ClassModel{
    
    public MockClassModel(String name){
        super(name);
    }
    
    @Override
    public MethodModel addMethod(MethodModel aMethod){
        if(this.okToAddMethod(aMethod.name()));
            methods.add(aMethod);
        return aMethod;
    }
    
}
