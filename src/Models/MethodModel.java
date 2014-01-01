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
    private boolean isOverride;
    private String source;
    private ReturnType returnType;
    private Object signature; //#todo, figure this out...again
    private ClassType type;
    private ArrayList<VariableModel> methodList;
    
    public MethodModel(){}
    
    public MethodModel(ClassModel parent, String name, ClassType type, ScopeType scope, ReturnType returnType, boolean isOverride){
        this.parent = parent;
        this.name = name;
        this.project = parent.getProject();
        this.type = type;
        this.returnType = returnType;
        this.scope = scope;
        this.methodList = new ArrayList();
        this.isOverride = isOverride;
    }
    
    public MethodModel(ClassModel parent, String name){
        this.parent = parent;
        this.name = name;
        this.project = parent.getProject();
        this.scope = ScopeType.PUBLIC;
        this.returnType = ReturnType.VOID;
        this.methodList = new ArrayList();
        this.isOverride = false;
    }
    
    
    /*
     * Setters
     */
    public void setSource(String source){
        this.source = source;
    }
    
    /*
     * Getters
     */
    private String getSource(){
        return source;
    }
    public ClassType getType(){
        return type;
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
}
