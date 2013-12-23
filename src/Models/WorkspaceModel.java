/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * WorkspaceModel is a model of the users workspace
 * it holds some of the file IO logic
 * @author Arthur
 */
public class WorkspaceModel {
    public static int numberOfDefaultWorkspaces = 0;
    public static String defaultWorkspaceName = "New Workspace";
    
    private ArrayList<ProjectModel> projects;
    private String path; //Todo: path to the users folder
    private String name;
    
    
    
    /*
     * Constructors
     * default constructor shouldnt be called outside the default constructor
     * for other classes.
     */
    public WorkspaceModel(){
        projects = new ArrayList();
        this.name = WorkspaceModel.defaultWorkspaceName();
    }
    public WorkspaceModel(String name){
        this.name = name;
        projects =  new ArrayList();
    }
    
    //Class-side methods
    public static String defaultWorkspaceName(){
        if(numberOfDefaultWorkspaces > 0)
            return defaultWorkspaceName+numberOfDefaultWorkspaces;
        else
            return defaultWorkspaceName;
    }
    
    //Getters
    public String name(){
        return name;
    }
    public ArrayList projects(){
        return projects;
    }
    
    //logic
    public void addProject(ProjectModel newProject){
        for(ProjectModel i : projects){
            if(i.name().equals(newProject.name()))
                return;
        }
        projects.add(newProject);
    }
    
}
