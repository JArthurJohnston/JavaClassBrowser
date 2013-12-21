/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 * a class to hold onto the logic in a method.
 * for now it just holds onto the source code.
 * 
 * though in the future it would be nice if it held onto its variables
 * and other method calls and lambdas as well.
 * 
 * @author Arthur
 */
public class MethodModel {
    private ClassModel myClass;
    private String source;
    
    public MethodModel(ClassModel aClass){
        myClass = aClass;
    }
    /**
     * method for adding code to the method.
     * this overrites the previous method string.
     * 
     * should probably make seperate methods for appending code.
     * should probably check the new code string for errors before
     * overwriting the old string.
     * @param newSource 
     */
    public void addSource(String newSource){
        source = newSource;
    }
    /**
     * this method check the current source variable for errors
     * @return 
     */
    public boolean parseSource(){
        boolean sourceCheck = true;
        //logic to parse the soure string looking for syntax errors.
        //returns false if any are found
        return sourceCheck;
    }
    /**
     * this method checks newly entered code, before its saved.
     * @return 
     */
    public boolean parseSource(String newSource){
        boolean sourceCheck = true;
        //logic to parse the soure string looking for syntax errors.
        //returns false if any are found
        return sourceCheck;
    }
}
