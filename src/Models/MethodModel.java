/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Types.ClassType;
import Types.ScopeType;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 
 * @author Arthur
 */
public class MethodModel extends ClassModel{
    private String source;
    private ClassType type;
    private LinkedList<VariableModel> arguments;
    private LinkedList references;
    protected ClassModel returnType;
    private MethodSignature signature;
    
    public MethodModel(){
        this.type = ClassType.INSTANCE;
        this.arguments = new LinkedList();
        this.references = new LinkedList();
    }
    public MethodModel(String name){
        this();
        this.name = name;
    }
    public MethodModel(String name, ClassType type){
        this(name);
        this.type = type;
    }
    public MethodModel(String name, ScopeType type){
        this(name);
        this.scope = type;
    }
    
    /**
     * this is THE constructor for non-constructor methods
     * @param scope
     * @param instanceOrStatic
     * @param returnType
     * @param name
     * @param params
     * @param source 
     */
    public MethodModel(ScopeType scope, ClassType instanceOrStatic, 
            ClassModel returnType, String name, LinkedList<VariableModel> params, String source){
        this.type = instanceOrStatic;
        this.scope = scope;
        this.source = source;
        this.returnType = returnType;
        this.name = name;
        this.arguments = params;
    }
    
    /**
     * Constructor for constructor type methods
     * @param parent
     * @param scope
     * @param params
     * @param source 
     */
    public MethodModel(ClassModel parent, ScopeType scope, LinkedList<VariableModel> params, String source){
        this.parent = parent;
        this.name = parent.name();
        this.returnType = parent;
        this.arguments = params;
        this.source = source;
    }
    
    public boolean hasSignatureOf(MethodModel anotherMethod){
        return this.getSignature().equals(anotherMethod.getSignature());
    }
    
    @Override
    public boolean isMethod(){
        return true;
    }
    
    public LinkedList getDefinitions(){
        return this.getProject().getMethodDefinitions(this);
    }
    
    public MethodModel addDefinition(MethodModel newDef){
        this.references.add(newDef);
        fireChanged(this);
        return newDef;
    }
    
    /**
     * To ensure that each signature is a singleton, this should only be
     * called when the method is added to the projects method HashMap.
     * Or this can be called in tests.
     * 
     * But if an already created method, outside of a test, has a null signature,
     * you have a problem.
     * 
     * @return 
     */
    public MethodSignature getSignature(){
        if(signature == null)
            return new MethodSignature(this);
        return signature;
    }
    
    public void setSignature(MethodSignature signature) {
        this.signature = signature;
    }
    
    public void setSource(String source){
        this.source = source;
        fireChanged(this);
    }
    
    public void setType(ClassType newType){
        /*
        -needs a validation method
        static methods cannot reference 'this', and can only reference
        other static methods and vars
        -alternatively, let the change go through, and just
        throw a lot of red underlines to denote syntax/compile errors.
        */
        this.type = newType;
        fireChanged(this);
    }
    
    public void setArguments(LinkedList params){
        this.arguments = params;
        fireChanged(this);
    }
    
    @Override
    public ClassModel getReturnType(){
        if(returnType == null)
            returnType = ClassModel.getPrimitive("void");
        return returnType;
    }
    
    public void setReturnType(ClassModel aClassValue){
        this.returnType = aClassValue;
        fireChanged(this);
    }
    
    /*
     * Getters
     */
    public String getSource(){
        if(source == null)
            return new String();
        return source;
    }
    
    @Override
    public ClassType getType(){
        return type;
    }
    
    @Override
    public ScopeType getScope(){
        if(scope == null)
            scope = ScopeType.NONE;
        return scope;
    }
    
    public String scopeString(){
        if(this.getScope() == ScopeType.NONE)
            return "";
        return this.getScope().toString().toLowerCase() + " ";
    }
    
    public LinkedList<VariableModel> arguments(){
        return arguments;
    }
    
    public LinkedList getReferences(){
        return references;
    }
    
    /*
     * Overridden methods
     */
    @Override
    public String getPath(){
        return parent.getClass() + this.path;
    }
    
    public String getSignarureString(){
        return this.scopeString() + 
                    this.getReturnType().toString() +" "+ 
                        this.name();
    }
    
    @Override
    public String toSourceString(){
        String signature =  this.getSignarureString() + "(";
        for(VariableModel param: arguments){
            if(param != arguments.get(0))
                signature += ", ";
            signature += param.getType().name()+" "+param.name();
        }
        signature += ")";
        return signature + this.getMethodBody();
    }
    
    public String getMethodBody(){
        return "{\n"+ this.getSource() + "\n}";
    }
    
    @Override
    public boolean isClass(){
        return false;
    }
    
    public boolean isConstructor(){
        return this.name().compareTo(this.getParent().name()) == 0;
    }
    
    @Override
    public boolean isAbstract(){
        return false;
    }
    
    @Override
    public PackageModel getParentPackage(){
        return this.parent.getParentPackage();
    }
    
    public ClassModel getParentClass(){
        return (ClassModel)this.parent;
    }
}
