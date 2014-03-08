/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Types.ClassType;
import Types.ReturnType;
import Types.ScopeType;
import java.util.ArrayList;

/**
 * 
 * @author Arthur
 */
public class MethodModel extends ClassModel{
    private String source;
    private ReturnType returnType;
    private Object signature; //#todo, figure this out...again
    private ClassType type;
    private ArrayList<VariableModel> parameters;
    
    public MethodModel(){}
    
    public MethodModel(ClassType type, ScopeType scope, ReturnType returnType, String name){
        this.initializeFields();
        this.name = name;
        this.type = type;
        this.scope = scope;
        this.returnType = returnType;
    }
    
    public MethodModel(String name){
        this.initializeFields();
        this.name = name;
    }
    
    public MethodModel (ClassType instanceOrStatic, ReturnType type, ArrayList params, String name){
        this.initializeFields();
        this.name = name;
        this.type = instanceOrStatic;
        this.returnType = type;
        this.parameters = params;
    }
    
    private void initializeFields(){
        this.type = ClassType.INSTANCE;
        this.scope = ScopeType.PUBLIC;
        this.parameters = new ArrayList();
        this.returnType = ReturnType.VOID;
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
        } 
        for(int i=0; i< parameters.size(); i++){
            if(this.parameters.get(i).getType() != otherMethod.getParameters().get(i).getType())
                return false;
        }
        return true;
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
    
    
    /*
     * Overridden methods
     */
    @Override
    public String getPath(){
        return parent.getClass() + this.path;
    }
    
    @Override
    public String toSourceString(){
        return this.getSource();
    }
    
    public class MethodSignature{
        
        public MethodSignature(ReturnType methodReturn, String methodName, ArrayList params){
            
        }
    }
}
