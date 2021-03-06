/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.AlreadyExistsException;

/**
 *
 * @author arthur
 */
public class InterfaceModel extends ClassModel {

    public InterfaceModel(String name) {
        super(name);
    }

    @Override
    public AbstractMethodModel addMethod(MethodModel newMethod) throws AlreadyExistsException {
        return (AbstractMethodModel) super.addMethod(newMethod);
    }

    @Override
    public boolean isInterface() {
        return true;
    }

    @Override
    public ClassModel addClass(ClassModel aClass) {
        this.classList.add(aClass);
        return aClass;
    }

}
