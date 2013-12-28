/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 * 
 * @author Arthur
 */
public class MethodModel extends ClassModel{
    private String source;
    private Object signature; //#todo, figure this out...again
    
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
}
