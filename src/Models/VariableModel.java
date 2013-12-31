/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Types.ReturnType;

/**
 *
 * @author Arthur
 */
public class VariableModel extends BaseModel{
    private ClassModel parent;
    private ReturnType type;
    private Object value;
    
    
    public VariableModel(ClassModel parent, ReturnType type, String name){
        this.parent = parent;
        this.name = name;
        this.type = type;
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
    public ClassModel getParent(){
        return parent;
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
