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
import MainBase.MainApplication;
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
    
    public boolean okToAddMethod(String newMethodName){
        for(MethodModel m : methods){
            if(m.name.compareTo(newMethodName) == 0) {
                return false;
            }
        }
        return true;
    }
    
    public boolean okToAddVariable(String newVarName){
        //check for redefining instanceVar
        for(VariableModel v: variables){
            if(v.name().compareTo(newVarName)==0) {
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
        if(this.okToAddVariable(newVar.name())){
            variables.add(newVar);
            newVar.setParent(this);
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
        this.project.addMethodDefinition(newMethod);
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
    
    public LinkedList<MethodModel> getMethods(){
        return methods;
    }
    
    public LinkedList getStaticMethods(){
        return this.getModelsOfType(methods, ClassType.STATIC);
    }
    public LinkedList<MethodModel> getInstanceMethods(){
        return this.getModelsOfType(methods, ClassType.INSTANCE);
    }
    
    private LinkedList getModelsOfType(LinkedList modelList, ClassType aType){
        LinkedList aList = new LinkedList();
        for(Object m : modelList){
            if(((BaseModel)m).getType() == aType)
                aList.add(m);
        }
        return aList;
    }
    
    public LinkedList getVariables(){
        return variables;
    }
    public LinkedList<VariableModel> getStaticVars(){
        return this.getModelsOfType(variables, ClassType.STATIC);
    }
    
    public LinkedList<VariableModel> getInstanceVars(){
        return this.getModelsOfType(variables, ClassType.INSTANCE);
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
    
    @Override
    public MainApplication getMain(){
        return parent.getMain();
    }
    
    @Override
    public boolean isPackage(){
        return false;
    }
}
