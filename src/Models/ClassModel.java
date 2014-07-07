/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.AlreadyExistsException;
import Exceptions.CannotBeDeletedException;
import Exceptions.DoesNotExistException;
import Exceptions.VeryVeryBadException;
import MainBase.SortedList;
import Types.ClassType;
import Types.ScopeType;
import UIModels.Buffer.ClassModelBuffer;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Arthur
 */
public class ClassModel extends PackageModel{
    //parent here means the class's package
    protected ScopeType scope;
    protected boolean isAbstract;
    protected boolean isFinal;
    private ClassModel generic;
    protected PackageModel parentPackage;
    public LinkedList<MethodModel> methods;
    public LinkedList<VariableModel> variables;
    //at this level, the classList variable is used to hold onto subclasses
    private static String hasSubClassesError = "Class has subclasses.";
    
    private static HashMap <String, ClassModel> PRIMITIVE_TYPES
            = new HashMap();
    private static ClassModel OBJECT_CLASS;
    
    protected ClassModel(){
        this.methods = new LinkedList();
        this.variables = new LinkedList();
        this.isAbstract = false;
    }
    
    public ClassModel(String name, ScopeType scope){
        this(name);
        this.scope = scope;
    }
    
    public ClassModel(String name){
        this();
        this.name = name;
    }
    
    public static SortedList<String> getPrimitiveTypes(){
        return new SortedList()
                    .addElm("int")
                    .addElm("byte")
                    .addElm("long")
                    .addElm("char")
                    .addElm("float")
                    .addElm("double")
                    .addElm("void");
    }
    
    public void setAbstract(boolean isAbstract){
        this.isAbstract = isAbstract;
    }
    
    public boolean isAbstract(){
        return isAbstract;
    }
    
    public void setFinal(boolean isFinal){
        this.isFinal = isFinal;
    }
    public boolean isFinal(){
        return isFinal;
    }
    
    public ClassModel getGenericType(){
        return generic;
    }
    
    public void setGenericType(ClassModel generic){
        this.generic = generic;
    }
    
    private String genericTypeString(){
        if(generic == null)
            return "";
        return " <"+generic.name()+"> ";
    }
    
    public static ClassModel getPrimitive(String aString){
        if(getPrimitiveTypes().contains(aString))
            if(!PRIMITIVE_TYPES.containsKey(aString))
                PRIMITIVE_TYPES.put(aString, new ClassModel(aString));
        return PRIMITIVE_TYPES.get(aString);
    }
    
    /**
     * returns a singleton reference to an "Object"
     * ClassModel.
     * 
     * @return 
     */
    public static ClassModel getObjectClass(){
        if(OBJECT_CLASS == null)
            OBJECT_CLASS = new ClassModel("Object");
        return OBJECT_CLASS;
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
        fireRemoved(parent, this);
        return parent.removeClass(this);
    }
    
    @Override
    public ClassModel removeClass(ClassModel aClass) throws VeryVeryBadException{
        if(!this.classList.remove(aClass))
            throw new VeryVeryBadException(this, aClass);
        fireRemoved(this, aClass);
        return this.getParentPackage().removeClass(aClass);
    }
    
    public VariableModel addVariable(VariableModel newVar) throws AlreadyExistsException{
        if(this.okToAddVariable(newVar.name())){
            variables.add(newVar);
            newVar.setParent(this);
            fireAdded(this, newVar);
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
    
    @Override
    public PackageModel getParent(){
        if(parent == null)
            return parentPackage;
        return (ClassModel) parent;
    }
    
    /**
     * This way, each method can ask its parent
     * for the class its being called from.
     * And I wont have to write extra logic on 
     * Project Model to handle the keyword "this"
     * 
     * @param className name of the class being searched for
     * @return the ClassModel whose name equals className, or null if none found
     */
    @Override
    public ClassModel findClass(String className) throws DoesNotExistException{
        if(className.compareTo("this") == 0)
            return this;
        return this.getProject().findClass(className);
    }
    
    /**
     * #test
     * This addMethod is for testing purposes only. 
     * use the long one in production
     * @param newMethod the method being added
     * @return the method being added
     */
    @Override
    public MethodModel addMethod(MethodModel newMethod) throws AlreadyExistsException {
        for(MethodModel mm : methods)
            if(newMethod.hasSignatureOf(mm))
                throw new AlreadyExistsException(this, newMethod);
        methods.add(newMethod);
        newMethod.setParent(this);
        fireAdded(this, this.getProject().addMethod(newMethod));
        return newMethod;
    }
    
    
    @Override
    public MethodModel removeMethod(MethodModel aMethod) throws DoesNotExistException{
        if(!methods.contains(aMethod))
            throw new DoesNotExistException(this, aMethod);
        methods.remove(aMethod);
        fireRemoved(this, this.getProject().removeMethod(aMethod));
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
        if(scope == null)
            scope = ScopeType.NONE;
        return scope;
    }
    
    /*
     * Setters
     */
    public void setScope(ScopeType newScope){
        this.scope = newScope;
        fireChanged(this);
    }
    
    /*
     * Overridden methods
     */
    @Override
    public String getPath(){
        return this.getParentPackage().getPath() + this.path;
    }
    
    public void setInterfaces(LinkedList<InterfaceModel> newInterfaces){
        interfaceList.clear();
        interfaceList.addAll(newInterfaces);
    }
    
    public void moveToPackage(PackageModel aPackage) throws AlreadyExistsException, VeryVeryBadException{
        if(aPackage == null)
            return;
        this.getParentPackage().classMoved(this);
        this.parent = aPackage;
        aPackage.adoptClass(this);
    }
    
    public void moveToClass(ClassModel newParent){
        if(newParent == null)
            return;
        this.getParent().removeChild(this);
        this.setParent(newParent);
        newParent.addChild(this);
    }
    
    @Override
    public LinkedList<ClassModel> getClassList(){
        LinkedList myClassList = new LinkedList();
        myClassList.add(this);
        for(ClassModel c : classList)
            myClassList.addAll(c.getClassList());
        return myClassList;
    }
    
    public LinkedList<ClassModel> getSubClasses(){
        if(classList == null)
            return new LinkedList();
        return classList;
    }
    
    public InterfaceModel implementsInterface(InterfaceModel anInterface){
        interfaceList.add(anInterface);
        this.implementAbstractMethods();
        return anInterface;
    }
    
    private void implementAbstractMethods(){
        //write me
    }
    
    public ClassModel getReturnType(){
        return this;
    }
    
    @Override
    public boolean isPackage(){
        return false;
    }
    
    @Override
    public boolean contains(BaseModel aModel){
        if(this.getProject().contains(aModel))
            if(aModel.isMethod() || aModel.isMethod())
                return ((MethodModel)aModel).getParent() == this;
        return false;
    }
    
    @Override
    public ClassModelBuffer getBuffer(){
        return new ClassModelBuffer(this);
    }
    
    public boolean isInterface(){
        return false;
    }
    
    private String getScopeString(){
        if(this.getScope() == ScopeType.NONE)
            return "";
        return scope.toString().toLowerCase() + " ";
    }
    
    private String inheritenceString(){
        if(parent == null || !parent.isClass())
            return "";
        return " extends " + this.parent.name();
    }
    
    private String interfacesString(){
        String s = "";
        for(InterfaceModel im : interfaceList)
            s += " implements " + im.name();
        return s;
    }
    
    public String getDeclaration(){
        return this.getScopeString() 
                + "class "
                + this.name() 
                + this.inheritenceString()
                + this.interfacesString();
    }
    
}
