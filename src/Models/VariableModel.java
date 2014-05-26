/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.CannotBeDeletedException;
import Exceptions.VeryVeryBadException;
import Types.ClassType;
import Types.ScopeType;

/**
 *
 * @author Arthur
 */
public class VariableModel extends BaseModel{
    private ClassModel parent;
    private ScopeType scope;
    private ClassModel type;
    private ClassType staticOrInstance;
    private String value;
    private boolean isFinal;
    
    private VariableModel(){
        //used only for returning a new VariableModel from parsed source code.
    }
    
    public VariableModel(String name){
        this.name = name;
    }
    
    public VariableModel(String name, ClassType instanceOrStatic){
        this.staticOrInstance = instanceOrStatic;
        this.name = name;
    }
    
    public VariableModel(ScopeType scope, ClassModel type, String name){
        this.scope = scope;
        this.type = type;
        this.name = name;
    }
    
    public VariableModel(ClassType staticOrInstance, ClassModel objectType, String name){
        this.staticOrInstance = staticOrInstance;
        this.type = objectType;
        this.name = name;
    }
    
    public boolean isFinal(){
        return this.isFinal;
    }
    
    /*
     * Getters
     */
    public ClassModel getObjectType(){
        if(type == null)
            type = ClassModel.getObjectClass();
        return type;
    }
    @Override
    public ClassType getType(){
        return staticOrInstance;
    }
    public String getValue(){
        return value;
    }
    public ClassModel getParent(){
        return parent;
    }
    public ScopeType getScope(){
        if(scope == null)
            scope = ScopeType.NONE;
        return scope;
    }
    
    
    /*
     * Setters
     */
    @Override
    public void setName(String aString){
        this.name = aString;
        fireChanged(this);
    }
    public VariableModel setObjectType(ClassModel type){
        this.type = type;
        fireChanged(this);
        return this;
    }
    public VariableModel setScope(ScopeType scope){
        if(scope == null)
            this.scope = ScopeType.NONE;
        else
            this.scope = scope;
        fireChanged(this);
        return this;
    }
    public VariableModel setType(ClassType aType){
        staticOrInstance = aType;
        fireChanged(this);
        return this;
    }
    public VariableModel setParent(ClassModel aClass){
        if(this.parent != aClass)
            this.parent = aClass;
        return this;
    }
    
    public VariableModel setValue(String aValue){
        this.value = aValue;
        fireChanged(this);
        return this;
    }
    public VariableModel setFinal(boolean isFinal){
        this.isFinal = isFinal;
        fireChanged(this);
        return this;
    }
    
    public String scopeString(){
        if(this.getScope() == ScopeType.NONE)
            return "";
        return this.getScope().toString().toLowerCase() + " ";
    }
    
    /*
     * Overridden Methods
     */
    @Override
    public String toSourceString() {
        String source = this.scopeString();
        if(this.isFinal)
            source = source + "final ";
        return source+this.type.name()+" "+this.name()+ this.getValueString() + ";";
    }
    
    private String getValueString(){
        if(value == null)
            return new String();
        return " = " + this.value;
    }
    
    public boolean hasValue(){
        return value != null;
        /*
        for use in a method parser
        to tell if a final variable has already been set
        */
    }
    
    @Override
    public boolean isVariable(){
        return true;
    }
    
    @Override
    public String getPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BaseModel remove() throws CannotBeDeletedException, VeryVeryBadException {
        /*
        check for references in methods
        if any exist, cant be removed. or warn the user that they will
        no longer compile
        */
        return this;
    }
}
