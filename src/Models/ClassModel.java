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
    private LinkedList<MethodModel> methods;
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
        this.methods = new LinkedList();
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
        if(methods.contains(aMethod)) 
            return aMethod;
        return null;
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
    
    public LinkedList getMethods(){
        return methods;
    }
    
    public LinkedList getStaticMethods(){
        return this.getMethodsOfType(ClassType.CLASS);
    }
    public LinkedList getInstanceMethods(){
        return this.getMethodsOfType(ClassType.INSTANCE);
    }
    
    private LinkedList<MethodModel> getMethodsOfType(ClassType aType){
        LinkedList<MethodModel> aList = new LinkedList();
        for(MethodModel m : methods){
            if(m.getType() ==  aType)
                aList.add(m);
        }
        return aList;
    }
    
    
    public ScopeType getScope(){
        return this.scope;
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
