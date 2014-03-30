/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.CannotBeDeletedException;
import Exceptions.DoesNotExistException;
import Exceptions.MethodDoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Exceptions.VeryVeryBadException;
import Types.ClassType;
import Types.ScopeType;
import java.util.ArrayList;
import java.util.LinkedList;

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
    private LinkedList<VariableModel> variables;
    //at this level, the classList variable is used to hold onto subclasses
    private static String hasSubClassesError = "Class has subclasses.";
     
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
        this.variables = new LinkedList();
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
    
    /**
     * The ClassModel removes itself from its package and project, 
     * it asks the package and project to remove it, after it checks to see
     * if it has any subclasses. if it does, it will throw an exception.
     * 
     * @return the ClassModel that has been removed from the package and project
     * @throws CannotBeDeletedException
     * @throws VeryVeryBadException 
     */
    public ClassModel remove() throws CannotBeDeletedException, VeryVeryBadException{
        if(!this.classList.isEmpty())
            throw new CannotBeDeletedException(this, hasSubClassesError);
        return parent.removeClass(this);
    }
    
    @Override
    public ClassModel removeClass(ClassModel aClass) throws VeryVeryBadException{
        if(!this.classList.remove(aClass))
            throw new VeryVeryBadException(this, aClass);
        return this.getParentPackage().removeClass(aClass);
    }
    
    public VariableModel addVariable(VariableModel newVar) throws NameAlreadyExistsException{
        if(this.okToAddVariable(newVar)){
            variables.add(newVar);
            return newVar;
        }else {
            throw new NameAlreadyExistsException(this, newVar);
        }
    }
    
    /**
     * #test
     * This addMethod is for testing purposes only. 
     * use the long one in production
     * @param newMethod the method being added
     * @return the method being added
     */
    public MethodModel addMethod(MethodModel newMethod) throws NameAlreadyExistsException{
        if(!this.okToAddMethod(newMethod.name()))
            throw new NameAlreadyExistsException(this, newMethod);
        if(newMethod.getType() == ClassType.CLASS) {
            classMethods.add(this.project.addMethodDefinition(newMethod));
        }else if(newMethod.getType() == ClassType.INSTANCE) {
            instanceMethods.add(this.project.addMethodDefinition(newMethod));
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
    
    @Override
    public MethodModel removeMethod(MethodModel aMethod){
        methods.remove(aMethod);
        if(classMethods.contains(aMethod)) {
            classMethods.remove(aMethod);
        }
        if(instanceMethods.contains(aMethod)) {
            instanceMethods.remove(aMethod);
        }
        return aMethod;
    }
    
    public VariableModel removeVariable(VariableModel aVar) throws DoesNotExistException{
        if(!variables.contains(aVar))
            throw new DoesNotExistException(this, aVar);
        else
            variables.remove(aVar);
        return aVar;
    }
    
    @Override
    public boolean isClass(){
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
    public LinkedList getVariables(){
        return variables;
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
    
    public void moveToPackage(PackageModel aPackage) throws NameAlreadyExistsException, VeryVeryBadException{
        this.getParentPackage().classMoved(this);
        this.parent = aPackage;
        aPackage.adoptClass(this);
    }
    
    @Override
    public LinkedList getClassList(){
        LinkedList myClassList = new LinkedList();
        myClassList.add(this);
        myClassList.addAll(this.classList);
        return myClassList;
    }
    
    public ClassModel getReturnType(){
        return this;
    }
    
    
}
