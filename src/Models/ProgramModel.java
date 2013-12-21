/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;


import java.util.Set;

/**
 *
 * @author Arthur
 */
public class ProgramModel {
    private ProjectModel project;
    private Set classes;
     
    /**
     * checks the classes set to ensure it does not already
     * contain a class with the same name as the class
     * being added.
     * @param newClass
     * @return 
     */
    public boolean okToAddClass(ClassModel newClass){
        return true;
        //Todo:
    }
    
}
