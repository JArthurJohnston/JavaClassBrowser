/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Types.ClassType;

/**
 * 
 * @author Arthur
 */
public class MethodModel extends ClassModel{
    private String source;
    private Object signature; //#todo, figure this out...again
    private ClassType type;
    
    public void setSource(String source){
        this.source = source;
    }
    
    private String getSource(){
        return source;
    }
    
    @Override
    public boolean isMethod(){
        return true;
    }
    public String toStringSource(){
        return this.getSource();
    }
    public ClassType getType(){
        if(type == null)
            return ClassType.INSTANCE;
        return type;
    }
}
