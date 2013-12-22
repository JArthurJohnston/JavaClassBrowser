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
public class ClassModel extends ProgramModel {
    public ArrayList instanceVariables;
    public ArrayList classVariables;
    public ArrayList instanceMethods;
    public ArrayList classMethods;
    public PackageModel parentPackage;
    public ClassModel parentClass;
    public ArrayList imports; //Todo: ImportsModel class?
    private ArrayList allVariables;
    
    /**
     * Default constructor
     * Should not be called. 
     * only used in one of the other ClassModel constructors
     */
    public ClassModel(){
        name = "Object";
        parentClass = null;
    }
    
    public ClassModel(String name){
        this.name = name;
        parentClass = new ClassModel();
        parentPackage = new PackageModel();
        allVariables = new ArrayList();
    }
    
    public ClassModel(PackageModel parentPackage, String newName){
        this.parentPackage = parentPackage;
        name = new String(newName);
        instanceVariables = new ArrayList();
        classVariables = new ArrayList();
        instanceMethods = new ArrayList();
        classMethods = new ArrayList();
        imports = new ArrayList();
        allVariables = new ArrayList();
    }
    
    /*
     * when adding variables, Ill need to differentiate between primitive types
     * which are easy to deal with,
     * and other objects. 
     * Ill need logic to properly add object variables.
     *  -check for duplicates in this class, and its parents
     * probably just need to printout the class name
     */
    public void addVariables(VariableModel newVar){
        allVariables.add(newVar);
    }
    public void removeVariables(VariableModel var){
        allVariables.remove(var);
    }
    public void addInstanceVariable(VariableModel newVar){
        if(this.okToAddVariable(newVar)){
            instanceVariables.add(newVar);
            this.addVariables(newVar);
        }
    }
    public void addClassVariables(VariableModel newVar){
        if(this.okToAddVariable(newVar)){
            classVariables.add(newVar);
            this.addVariables(newVar);
        }
    }
    public void addInstanceMethod(MethodModel newMethod){
        instanceMethods.add(newMethod);
    }
    public void addClassMethod(MethodModel newMethod){
        classMethods.add(newMethod);
    }
    
    public void removeClassVariable(VariableModel var){
        if(this.okToRemoveVariable(var)){
            classVariables.remove(var);
            this.addVariables(var);
        }
    }
    public void removeInstanceVariables(VariableModel var){
        if(this.okToRemoveVariable(var)){
            instanceVariables.remove(var);
            allVariables.remove(var);
        }
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
    public boolean okToAddVariable(VariableModel newVar){
        return !(allVariables.contains(newVar));
    }
    public boolean okToRemoveVariable(VariableModel var){
        return allVariables.contains(var);
    }
    /*
     * Accessors
     */
    public String name(){
        return name;
    }
}

/*
 * notes:
 * classes hold a list of their own methods, and a seperate list of the methods
 * of any of the classes its using; for source code auto-complete.
 */