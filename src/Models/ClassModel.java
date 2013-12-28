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
public class ClassModel extends PackageModel{
    private PackageModel parentPackage;
    private ClassModel parentClass;
    private ArrayList<ClassModel> classList;
    private ArrayList<MethodModel> methods;
    //at this level, the classList variable is used to hold onto subclasses
     
    /**
     * the default constructor is only used to define 
     * the defaultParentClass variable. it shouldn't be used for anything
     * else, outside of testing.
     */
    public ClassModel(){
        this.name = "Object";
    }
    
    public ClassModel(PackageModel parentPackage, String name){
        parentClass = defaultParentClass;
        this.parentPackage = parentPackage;
        this.methods = new ArrayList();
        this.name = name;
    }
    
    protected boolean isTopLevel(){
        return parentClass == defaultParentClass;
    }
    
    public ArrayList methods(){
        return methods;
    }
    
    
}
