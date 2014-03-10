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
    private ScopeType scope;
    private ClassModel type;
    private Object value;
    
    
    public VariableModel (ScopeType scope, ClassModel type, String name){
        this.scope = scope;
        this.type = type;
        this.name = name;
    }
    
    /*
     * Getters
     */
    public ClassModel getType(){
        return type;
    }
    
    /*
     * Setters
     */
    public void setType(ClassModel type){
        this.type = type;
    }
    public void setScope(ScopeType scope){
        this.scope = scope;
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
    protected void setUpFields() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
