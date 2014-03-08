/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Types.ClassType;
import Types.ReturnType;

/**
 *
 * @author Arthur
 */
public class VariableModel extends BaseModel{
    private ClassType staticOrInstance;
    private ReturnType type;
    private Object value;
    
    
    public VariableModel ( ReturnType type, String name){
        this.type = type;
        this.name = name;
    }
    
    /*
     * Getters
     */
    public ReturnType getType(){
        return type;
    }
    public Object getValue(){
        return value;
    }
    
    
    /*
     * Setters
     */
    public void setType(ReturnType type){
        this.type = type;
    }
    public void setValue(Object value){
        this.value = value;
    }
    
    /*
     * Overridden Methods
     */
    @Override
    public String toSourceString() {
        throw new UnsupportedOperationException("Not supported yet.");
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
