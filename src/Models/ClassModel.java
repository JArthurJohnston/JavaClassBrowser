/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.MethodDoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Types.ClassType;
import Types.ReturnType;
import Types.ScopeType;
import java.util.ArrayList;

/**
 *
 * @author Arthur
 */
public class ClassModel extends PackageModel{
    //parent here means the class's package
    protected ScopeType scope;
    private ArrayList<MethodModel> methods;
    private ArrayList<MethodModel> instanceMethods;
    private ArrayList<MethodModel> classMethods;
    private ArrayList<ConstructorModel> constructors;
    private ArrayList<MethodModel> inheritedMethods;
    private ArrayList<VariableModel> instanceVars;
    private ArrayList<VariableModel> classVars;
    private ArrayList<VariableModel> variables;
    //at this level, the classList variable is used to hold onto subclasses
     
    //use these constructors for testing only
    public ClassModel(){}
    public ClassModel(String name){
        this.name = name;
    }
    
    public ClassModel(PackageModel parent, String name, ScopeType scope){
        this.project = parent.getProject();
        this.parent = parent;
        this.name = name;
        this.scope = scope;
    }
    
    public ClassModel(PackageModel parent, String name){
        this.project = parent.getProject();
        this.parent = parent;
        this.name = name;
        this.scope = ScopeType.PUBLIC;
    }
    
    @Override
    protected void setUpFields(){
        this.classList = new ArrayList();
        this.methods = new ArrayList();
        this.instanceMethods = new ArrayList();
        this.classMethods = new ArrayList();
        this.constructors = new ArrayList();
        //this.instanceVars = new ArrayList();
        //this.classVars = new ArrayList();
        this.variables = new ArrayList();
    }
    
    private boolean okToAddMethod(String newMethodName){
        for(MethodModel m : methods){
            if(m.name.compareTo(newMethodName) == 0) {
                return false;
            }
        }
        return true;
    }
    
    private boolean okToAddVariable(VariableModel newVar){
        //check for redefining instanceVar
        for(VariableModel v: variables){
            if(v.name().compareTo(newVar.name())==0) {
                return false;
            }
        }
        return true;
    }
    
    public VariableModel addVariable(VariableModel newVar) throws NameAlreadyExistsException{
        if(this.okToAddVariable(newVar)){
            variables.add(newVar);
            return newVar;
        }else {
            throw new NameAlreadyExistsException(this, newVar);
        }
    }
    
    public MethodModel addMethod(String newMethodName, ClassType type, ScopeType scope, 
            ReturnType returnType, boolean isOverride) throws NameAlreadyExistsException{
        if(this.okToAddMethod(newMethodName)){
            MethodModel newMethod = new MethodModel(this, type, scope, newMethodName);
            return this.addMethod(newMethod);
        }else {
            throw new NameAlreadyExistsException(this, newMethodName);
        }
    }
    
    /**
     * #test
     * This addMethod is for testing purposes only. 
     * use the long one in production
     * @param newMethodName
     * @return
     * @throws NameAlreadyExistsException 
     */
    protected MethodModel addMethod(String newMethodName) throws NameAlreadyExistsException{
        return this.addMethod(newMethodName, ClassType.INSTANCE, ScopeType.PUBLIC, ReturnType.VOID, false);
    }
    
    public MethodModel addMethod(MethodModel newMethod){
        if(newMethod.getType() == ClassType.CLASS) {
            classMethods.add(this.project.addMethod(newMethod));
        }else if(newMethod.getType() == ClassType.INSTANCE) {
            instanceMethods.add(this.project.addMethod(newMethod));
        }
        methods.add(newMethod);
        return newMethod;
    }
    
    public MethodModel removeMethod(String methodName) throws MethodDoesNotExistException{
        for(MethodModel m : methods){
            if(m.name.compareTo(methodName) == 0) {
                return this.removeMethod(m);
            }
        }
        throw new MethodDoesNotExistException(this, methodName);
    }
    
    private MethodModel removeMethod(MethodModel aMethod){
        methods.remove(aMethod);
        if(classMethods.contains(aMethod)) {
            classMethods.remove(aMethod);
        }
        if(instanceMethods.contains(aMethod)) {
            instanceMethods.remove(aMethod);
        }
        return aMethod;
    }
    
    @Override
    protected boolean isClass(){
        return true;
    }
    
    @Override
    protected PackageModel getParentPackage(){
        if(parent.isClass()) {
            return parent.getParentPackage();
        }
        else {
            return (PackageModel)parent;
        }
    }
    
    public ArrayList getInstanceMethods(){
        return instanceMethods;
    }
    public ArrayList getClassMethods(){
        return classMethods;
    }
    public ArrayList getConstructors(){
        return constructors;
    }
    
    public ArrayList getMethods(){
        return methods;
    }
    public ScopeType getScope(){
        return this.scope;
    }
    public ArrayList getInstanceVariables(){
        return instanceVars;
    }
    public ArrayList getClassVariables(){
        return classVars;
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
        return this.getParentPackage().getPath() + this.path;
    }
}
