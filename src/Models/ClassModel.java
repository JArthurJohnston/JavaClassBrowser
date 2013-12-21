/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;

/**
 *
 * @author Arthur
 */
public class ClassModel {
    private String name;
    private ArrayList instanceVariables;
    private ArrayList classVariables;
    private ArrayList instanceMethods;
    private ArrayList classMethods;
    
    public ClassModel(String newName){
        name = new String(newName);
        instanceVariables = new ArrayList();
        classVariables = new ArrayList();
        instanceMethods = new ArrayList();
        classMethods = new ArrayList();
    }
    
    /*
     * when adding variables, Ill need to differentiate between primitive types
     * which are easy to deal with,
     * and other objects. 
     * Ill need logic to properly add object variables.
     * probably just need to printout the class name
     */
    public void addInstanceVariable(Object anObject){
        instanceVariables.add(anObject);
    }
    public void addClassVariables(Object anObject){
        classVariables.add(anObject);
    }
    public void addInstanceMethod(MethodModel newMethod){
        instanceMethods.add(newMethod);
    }
    public void addClassMethod(MethodModel newMethod){
        classMethods.add(newMethod);
    }
    
    public void removeClassVariable(Object var){
        classVariables.remove(var);
    }
    public void removeInstanceVariables(Object var){
        instanceVariables.remove(var);
    }
    public void removeClassMethod(MethodModel method){
        classMethods.remove(method);
    }
    public void removeInstanceMethods(MethodModel method){
        instanceMethods.remove(method);
    }
    
    public void changeName(String newName){
        //validate the new name, iftrue...
        name = new String(newName);
    }
}
