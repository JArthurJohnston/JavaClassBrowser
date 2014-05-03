/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Types.ClassType;
import Types.ScopeType;
import java.util.LinkedList;

/**
 * 
 * @author Arthur
 */
public class MethodModel extends ClassModel{
    private String source;
    private ClassModel returnType;
    private ClassType type;
    private LinkedList<VariableModel> parameters;
    private LinkedList references;
    
    public MethodModel(){
        this.type = ClassType.INSTANCE;
        this.parameters = new LinkedList();
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
            ClassModel returnType, String name, LinkedList params, String source){
        this.type = instanceOrStatic;
        this.scope = scope;
        this.source = source;
        this.returnType = returnType;
        this.name = name;
        this.parameters = params;
    }
    
    /**
     * Constructor for constructor type methods
     * @param parent
     * @param scope
     * @param params
     * @param source 
     */
    public MethodModel(ClassModel parent, ScopeType scope, LinkedList params, String source){
        this.parent = parent;
        this.name = parent.name();
        this.returnType = parent;
        this.parameters = params;
        this.source = source;
    }
    
    
    private void initializeFields(){
        this.type = ClassType.INSTANCE;
        this.scope = ScopeType.PUBLIC;
        this.parameters = new LinkedList();
        this.references = new LinkedList();
    }
    
    @Override
    public boolean isMethod(){
        return true;
    }
    
    public LinkedList getDefinitions(){
        return this.getProject().getMethodDefinitions(this);
    }
    
    public boolean matchSignature(MethodModel otherMethod){
        if(this.name.compareTo(otherMethod.name()) != 0){
            return false;
        } else if(this.parameters.size() != otherMethod.getParameters().size()){
            return false;
        }else{
            int i;
            for(i=0; i< parameters.size(); i++){
                if(this.parameters.get(i).getType() != (otherMethod.parameters.get(i).getType()))
                    break;
            }
            return i >= parameters.size();
        }
    }
    
    public MethodModel addDefinition(MethodModel newDef){
        this.references.add(newDef);
        return newDef;
    }
    
    /*
     * Setters
     */
    public void setSource(String source){
        this.source = source;
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
    }
    public void setParameters(LinkedList params){
        this.parameters = params;
    }
    
    @Override
    public ClassModel getReturnType(){
        return returnType;
    }
    
    public void setReturnType(ClassModel aClassValue){
        this.returnType = aClassValue;
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
    
    private String scopeString(){
        if(this.getScope() == ScopeType.NONE)
            return "";
        return this.getScope().toString().toLowerCase() + " ";
    }
    
    public LinkedList<VariableModel> getParameters(){
        return parameters;
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
    
    @Override
    public String toSourceString(){
        String signature =  this.scopeString() + 
                            this.returnType.toString().toLowerCase() +" "+ 
                            this.name() + "(";
        for(VariableModel param: parameters){
            if(param != parameters.get(0))
                signature += ", ";
            signature += param.getType().name()+" "+param.name();
        }
        return signature + "){\n"+ this.getSource() + "\n}";
    }
    
    @Override
    public boolean isClass(){
        return false;
    }
    
    public boolean isConstructor(){
        return this.name().compareTo(this.getParent().name()) == 0;
    }
}
