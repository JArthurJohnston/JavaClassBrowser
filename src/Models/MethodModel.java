/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Types.ClassType;
import Types.ReturnType;
import Types.ScopeType;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 
 * @author Arthur
 */
public class MethodModel extends ClassModel{
    private String source;
    private ClassModel returnType;
    private ClassType type;
    private ArrayList<VariableModel> parameters;
    private LinkedList references;
    
    //testing constructors
    public MethodModel(){}
    public MethodModel(String name){
        this.initializeFields();
        this.name = name;
    }
    public MethodModel(ClassModel parent, String name){
        this.parent = parent;
        this.project = parent.project;
        this.initializeFields();
        this.name = name;
    }
    
    public MethodModel(ClassModel parentClass, ClassType type, ScopeType scopes, String name){
        this.initializeFields();
        this.parent = parentClass;
        this.project = parentClass.project;
        this.name = name;
        this.type = type;
    }
    
    public MethodModel (ClassModel parentClass, ClassType instanceOrStatic, ArrayList params, String name){
        this.initializeFields();
        this.parent = parentClass;
        this.project = parentClass.project;
        this.name = name;
        this.type = instanceOrStatic;
        this.parameters = params;
    }
    
    /**
     * the most full constructor for MethodModel
     */
    
    private void initializeFields(){
        this.type = ClassType.INSTANCE;
        this.scope = ScopeType.PUBLIC;
        this.parameters = new ArrayList();
        this.references = new LinkedList();
    }
    
    @Override
    public boolean isMethod(){
        return true;
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
    public void setParameters(ArrayList params){
        this.parameters = params;
    }
    
    /*
     * Getters
     */
    public String getSource(){
        if(source == null)
            return new String();
        return source;
    }
    public ClassType getType(){
        return type;
    }
    public ScopeType scope(){
        return scope;
    }
    public ArrayList<VariableModel> getParameters(){
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
        String signature =  this.scope.toString().toLowerCase() + " " + 
                            this.returnType.toString().toLowerCase() +" "+ 
                            this.name() + "(";
        for(VariableModel param: parameters){
            if(param != parameters.get(0))
                signature += ", ";
            signature += param.getType().name()+" "+param.name();
        }
        return signature + "){\n"+ this.getSource() + "\n}";
    }
}
