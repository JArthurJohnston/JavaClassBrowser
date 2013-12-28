/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.NameAlreadyExistsException;
import java.util.ArrayList;

/**
 *
 * @author Arthur
 */
public class ClassModel extends PackageModel{
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
        parentClass = defaultParentClass;
        methods = new ArrayList();
        this.inheritedMethods = new ArrayList();
    }
    
    public ClassModel(String name){
        parentClass = defaultParentClass;
        this.name = name;
        this.setUpDataStructures();
    }
    public ClassModel (String name, ClassModel parentClass){
        this.parentClass = parentClass;
        this.name = name;
        this.setUpDataStructures();
    }
    
    @Override
    protected void setUpDataStructures(){
        this.methods = new ArrayList();
        this.inheritedMethods = parentClass.getInheritedMethods();
        this.classList = new ArrayList();
    }
    
    @Override
    public ClassModel addClass(String newClassName) throws NameAlreadyExistsException{
        if(super.okToAddClass(newClassName)){
            ClassModel newClass = new ClassModel(newClassName, this);
            super.addClass(newClass);
            this.addClass(newClass);
            return newClass;
        }else {
            throw new NameAlreadyExistsException(newClassName, this);
        }
    }
    
    @Override
    protected ClassModel addClass(ClassModel newClass){
        classList.add(newClass);
        return newClass;
    }
    
    
    public ArrayList<MethodModel> inheritableMethods(){
        ArrayList<MethodModel> methods = new ArrayList();
        return methods;
    }
    
    protected boolean isTopLevel(){
        return parentClass == defaultParentClass;
    }
    
    //Getters
    public ArrayList methods(){
        return methods;
    }
    public ArrayList<MethodModel> getInheritedMethods(){
        return inheritedMethods;
    }
    
    
}
