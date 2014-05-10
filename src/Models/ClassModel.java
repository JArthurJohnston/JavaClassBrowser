/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.CannotBeDeletedException;
import Exceptions.DoesNotExistException;
import Exceptions.MethodDoesNotExistException;
import Exceptions.AlreadyExistsException;
import Exceptions.VeryVeryBadException;
import MainBase.UsefulList;
import Types.ClassType;
import Types.ScopeType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Arthur
 */
public class ClassModel extends PackageModel{
    //parent here means the class's package
    protected ScopeType scope;
    protected PackageModel parentPackage;
    public LinkedList<MethodModel> methods;
    public LinkedList<VariableModel> variables;
    //at this level, the classList variable is used to hold onto subclasses
    private static String hasSubClassesError = "Class has subclasses.";
    
    private static HashMap <String, ClassModel> PRIMITIVE_TYPES
            = new HashMap();
    
    //use these constructors for testing only
    public ClassModel(){
    }
    
    public ClassModel(String name, ScopeType scope){
        this(name);
        this.scope = scope;
    }
    
    public ClassModel(String name){
        initialize();
        this.name = name;
    }
    
    private void initialize(){
        this.scope = ScopeType.PUBLIC;
        classList = new ArrayList();
        this.classList = new ArrayList();
        this.methods = new LinkedList();
        this.variables = new LinkedList();
    }
    
    public static UsefulList<String> getPrimitiveTypes(){
        return new UsefulList()
                    .addElm("int")
                    .addElm("long")
                    .addElm("char")
                    .addElm("float")
                    .addElm("double")
                    .addElm("void");
    }
    
    public static ClassModel getPrimitive(String aString){
        if(getPrimitiveTypes().contains(aString))
            if(!PRIMITIVE_TYPES.containsKey(aString))
                PRIMITIVE_TYPES.put(aString, new ClassModel(aString));
        return PRIMITIVE_TYPES.get(aString);
    }
    
    public boolean isPrimitive(){
        return ClassModel.getPrimitiveTypes().contains(this.name());
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
    @Override
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
    
    public VariableModel addVariable(VariableModel newVar) throws AlreadyExistsException{
        if(this.okToAddVariable(newVar.name())){
            variables.add(newVar);
            newVar.setParent(this);
            return newVar;
        }else {
            throw new AlreadyExistsException(this, newVar);
        }
    }
    
    /**
     * sets the parent for a class to either a
     * package or another class.
     * @param packageOrClass 
     */
    public void setParent(PackageModel packageOrClass){
        if(packageOrClass.isPackage())
            this.parentPackage = packageOrClass;
        if(packageOrClass.isClass()){
            this.parent = packageOrClass;
            if(this.parentPackage == null)
                this.parentPackage = packageOrClass.getParentPackage();
        }
    }
    
    
    /**
     * #test
     * This addMethod is for testing purposes only. 
     * use the long one in production
     * @param newMethod the method being added
     * @return the method being added
     */
    public MethodModel addMethod(MethodModel newMethod) throws AlreadyExistsException{
        if(!this.okToAddMethod(newMethod.name()))
            throw new AlreadyExistsException(this, newMethod);
        methods.add(newMethod);
        this.getProject().addMethodDefinition(newMethod);
        newMethod.setParent(this);
        return newMethod;
    }
    
    //clearly doesnt work yet...
    public MethodModel removeMethod(String methodName) throws MethodDoesNotExistException{
        for(MethodModel m : methods){
            if(m.name.compareTo(methodName) == 0) {
                return this.removeMethod(m);
            }
        }
        throw new MethodDoesNotExistException(this, methodName);
    }
    
    //clearly doesnt work yet...
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
    public PackageModel getParentPackage(){
        return parentPackage;
    }
    
    @Override
    public ProjectModel getProject(){
        return parentPackage.getProject();
    }
    
    public LinkedList<MethodModel> getMethods(){
        return methods;
    }
    
    public LinkedList<MethodModel> getStaticMethods(){
        return this.getModelsOfType(methods, ClassType.STATIC);
    }
    public LinkedList<MethodModel> getInstanceMethods(){
        return this.getModelsOfType(methods, ClassType.INSTANCE);
    }
    
    public LinkedList getModelsOfType(LinkedList modelList, ClassType aType){
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
    
    public void moveToPackage(PackageModel aPackage) throws AlreadyExistsException, VeryVeryBadException{
        this.getParentPackage().classMoved(this);
        this.parent = aPackage;
        aPackage.adoptClass(this);
    }
    
    @Override
    public LinkedList getClassList(){
        LinkedList myClassList = new LinkedList();
        myClassList.add(this);
        for(ClassModel c : classList)
            myClassList.addAll(c.getClassList());
        return myClassList;
    }
    
    public ClassModel getReturnType(){
        return this;
    }
    
    @Override
    public boolean isPackage(){
        return false;
    }
}
