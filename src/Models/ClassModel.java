/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.NameAlreadyExistsException;
import Types.ClassType;
import Types.ScopeType;
import java.util.ArrayList;

/**
 *
 * @author Arthur
 */
public class ClassModel extends PackageModel{
    //parent here means the class's package
    protected ScopeType scope;
    private ClassModel parentClass;
    private ArrayList<ClassModel> classList;
    private ArrayList<MethodModel> methods;
    private ArrayList<MethodModel> inheritedMethods;
    //at this level, the classList variable is used to hold onto subclasses
     
    /**
     * the default constructor is only used to define 
     * the defaultParentClass variable. it shouldn't be used for anything
     * else, outside of testing.
     */
    public ClassModel(){
        this.name = "Object";
        methods = new ArrayList();
        this.inheritedMethods = new ArrayList();
    }
    
    /**
     * this constructor is for testing purposes ONLY
     * @param nameForTesting 
     */
    public ClassModel(String nameForTesting){
        this.name = nameForTesting;
    }
    
    public ClassModel(PackageModel parent, String name){
        this.parent = parent;
        this.name = name;
        this.setUpFields();
    }
    public ClassModel (ClassModel parentClass, String name){
        this.parent = parentClass.getParent();
        this.parentClass = parentClass;
        this.name = name;
        this.setUpFields();
    }
    
    @Override
    protected void setUpFields(){
        classList = new ArrayList();
        scope = ScopeType.PUBLIC;
    }
    /*
     * Getters
     */
    public ArrayList<MethodModel> inheritableMethods(){
        ArrayList<MethodModel> inhMethods = new ArrayList();
        for(MethodModel m : methods){
            
        }
        return inhMethods;
    }
    
    public ArrayList instanceMethods(){
        ArrayList classMethods = new ArrayList();
        for(MethodModel m : methods){
            if(m.getType() == ClassType.INSTANCE) {
                classMethods.add(m);
            }
        }
        return classMethods;
    }
    public ArrayList classMethods(){
        ArrayList classMethods = new ArrayList();
        for(MethodModel m : methods){
            if(m.getType() == ClassType.CLASS) {
                classMethods.add(m);
            }
        }
        return classMethods;
    }
}
