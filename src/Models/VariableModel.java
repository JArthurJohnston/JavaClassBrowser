/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

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
        return scope;
    }
    
    
    /*
     * Setters
     */
    @Override
    public void setName(String aString){
        this.name = aString;
    }
    public VariableModel setObjectType(ClassModel type){
        this.type = type;
        return this;
    }
    public VariableModel setScope(ScopeType scope){
        if(scope == null)
            this.scope = ScopeType.NONE;
        else
            this.scope = scope;
        return this;
    }
    public VariableModel setType(ClassType aType){
        staticOrInstance = aType;
        return this;
    }
    public VariableModel setParent(ClassModel aClass){
        if(this.parent != aClass)
            this.parent = aClass;
        return this;
    }
    
    public VariableModel setValue(String aValue){
        this.value = aValue;
        return this;
    }
    public VariableModel setFinal(boolean isFinal){
        this.isFinal = isFinal;
        return this;
    }
    
    /*
     * Overridden Methods
     */
    @Override
    public String toSourceString() {
        String source = new String();
        if(this.scope != ScopeType.NONE)
            source = this.scope.toString().toLowerCase() + " ";
        return source+this.type.name()+" "+this.name()+";";
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
    protected void setUpFields() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
