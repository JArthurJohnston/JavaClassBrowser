/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Internal.Mocks;

import Models.ClassModel;
import Models.MethodModel;
import Models.VariableModel;
import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public class MockClassModel extends ClassModel{
    private final LinkedList methods;
    private final LinkedList variables;
    
    public MockClassModel(String name){
        super(name);
        methods = new LinkedList();
        variables = new LinkedList();
        classList = new LinkedList();
    }
    /*
    Overidden for testing 
    So I can add without throwing exceptions or 
    triggering shell updates.
    */
    @Override
    public MethodModel addMethod(MethodModel aMethod){
        if(this.okToAddMethod(aMethod.name()))
            methods.add(aMethod);
        aMethod.setParent(this);
        return aMethod;
    }
    @Override
    public VariableModel addVariable(VariableModel aVar){
        if(this.okToAddVariable(aVar.name()))
            this.variables.add(aVar);
        aVar.setParent(this);
        return aVar;
    }
    
    @Override
    public ClassModel addClass(ClassModel aClass){
        classList.add(aClass);
        return aClass;
    }
}
