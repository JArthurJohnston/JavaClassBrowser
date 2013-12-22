/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;

/**
 * An object for keeping track of all the classes in a single project
 * @author Arthur
 */
public class ProjectModel extends ProgramModel{
    
    public ProjectModel(String newName){
        name = new String(newName);
    }
    
    public void addClass(ClassModel newClass){
        classes.put(newClass.name(), newClass);
    }
    public int numberOfClasses(){
        return classes.size();
    }
}
