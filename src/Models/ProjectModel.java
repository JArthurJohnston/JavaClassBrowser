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
    public static final String defaultName = "New Project";
    
    public WorkspaceModel workspace; //user info, author name, path to files, etc...
    private boolean isDefault = false;
    
    public ProjectModel(){
        this.name = ProjectModel.defaultName;
        isDefault = true;
    }
    public ProjectModel(String name){
        this.name = name;
    }
    
    //Getters
    public String name(){
        return name;
    }
    public WorkspaceModel workspace(){
        return workspace;
    }
    
    //Setters
    public void setName(String newName){
        this.name = newName;
    }
}
