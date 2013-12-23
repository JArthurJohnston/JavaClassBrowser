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
    public String name;
    private WorkspaceModel workspace;
    
    public ProjectModel(){
        this.name = ProjectModel.defaultName;
    }
    public ProjectModel(String name, WorkspaceModel workspace){
        this.workspace = workspace;
        this.name = name;
    }
    
    //Getters
    public String name(){
        return name;
    }
    public WorkspaceModel workspace(){
        return workspace;
    }
         
}
