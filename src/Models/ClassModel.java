/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.NameAlreadyExistsException;
import Types.ScopeType;
import java.util.ArrayList;

/**
 *
 * @author Arthur
 */
public class ClassModel extends PackageModel{
    //parent here means the class's package
    protected ScopeType scope;
    private PackageModel parentPackage;
    private ArrayList<MethodModel> methods;
    private ArrayList<MethodModel> inheritedMethods;
    //at this level, the classList variable is used to hold onto subclasses
     
    public ClassModel(){}
    
    public ClassModel(PackageModel parent, String name, ScopeType scope){
        this.project = parent.getProject();
        this.parentPackage = parent;
        this.parent = parent;
        this.name = name;
        this.classList = new ArrayList();
        this.methods = new ArrayList();
        this.scope = scope;
    }
    
    public ClassModel(PackageModel parent, String name){
        this.parentPackage = parent;
        this.project = parent.getProject();
        this.parent = parent;
        this.name = name;
        this.classList = new ArrayList();
        this.methods = new ArrayList();
        this.scope = ScopeType.PUBLIC;
    }
    
    private boolean okToAddMethod(String newMethodName){
        for(MethodModel m : methods){
            if(m.name.compareTo(newMethodName) == 0) {
                return false;
            }
        }
        return true;
    }
    
    public MethodModel addMethod(String newMethodName) throws NameAlreadyExistsException{
        if(this.okToAddMethod(newMethodName)){
            MethodModel newMethod = new MethodModel(this, newMethodName);
            return this.addMethod(newMethod);
        }else {
            throw new NameAlreadyExistsException(this, newMethodName);
        }
    }
    
    private MethodModel addMethod(MethodModel newMethod){
        methods.add(newMethod);
        return newMethod;
    }
    
    /*
     * Getters
     */
    public ArrayList getInheritableMethods(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public ArrayList getInstanceMethods(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public ArrayList getClassMethods(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public ArrayList getMethods(){
        return methods;
    }
    public ScopeType getScope(){
        return this.scope;
    }
    /*
     * Setters
     */
    public void setScope(ScopeType newScope){
        this.scope = newScope;
    }
    
    /*
     * Overridden methods
     */
    @Override
    public String getPath(){
        return parentPackage.getPath() + this.path;
    }
}
