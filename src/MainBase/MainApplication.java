package MainBase;

import Exceptions.DoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Models.*;
import UIModels.ProjectManagerShellModel;
import java.util.ArrayList;

/**
 * MainApplication is the top-level model for the whole program
 * it keeps track of all active projects and any other non-coding
 * functions of the program. Its always the first thing that runs
 * when the program is started.
 * 
 * @author Arthur
 */
public class MainApplication {
    private ProjectManagerShellModel shellModel;
    private ArrayList<ProjectModel> projects;
    private ArrayList openWindowModels;
    private String userName;
    
    public MainApplication(){
        userName = System.getProperty("user.name");
        projects = new ArrayList();
        openWindowModels = new ArrayList();
        shellModel = new ProjectManagerShellModel(this);
        
    }
    
    public ArrayList<ProjectModel> getProjects(){
        return projects;
    }
    
    public boolean okToAdd(String newProjectName){
        for(ProjectModel p: projects){
            if(p.name().compareTo(newProjectName) == 0) {
                return false;
            }
        }
        return true;
    }
    
    public boolean okToDelete(ProjectModel aProject){
        return projects.contains(aProject);
    }
    
    public ProjectModel addProject(ProjectModel newProject){
        projects.add(newProject);
        return newProject;
    }
    
    public ProjectModel addProject(String newProjectName) throws NameAlreadyExistsException{
        if(this.okToAdd(newProjectName)){
            ProjectModel newProject = new ProjectModel(this, newProjectName);
            return this.addProject(newProject);
        }else {
            throw new NameAlreadyExistsException(this, newProjectName);
        }
    }
    
    public ProjectModel removeProject(ProjectModel aProject) throws DoesNotExistException{
        if(this.okToDelete(aProject)) {
            projects.remove(aProject);
        }
        else {
            throw new DoesNotExistException(this, aProject);
        }
        return aProject;
    }
    
    public String getUserName(){
        return userName;
    }
    public void setUserName(String newUserName){
        this.userName = newUserName;
    }
}
