package MainBase;

import Exceptions.DoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Models.*;
import UIShells.AddNewProjectShell;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;

/**
 * MainApplication is the top-level model for the whole program
 * it keeps track of all active projects and any other non-coding
 * functions of the program. Its always the first thing that runs
 * when the program is started.
 * 
 * @author Arthur
 */
public class MainApplication {
    private ArrayList<ProjectModel> projects;
    private ArrayList openWindowModels;
    private ArrayList openWindowShells;
    private String userName;
    
    public MainApplication(){
        userName = System.getProperty("user.name");
        projects = new ArrayList();
        openWindowModels = new ArrayList();
        openWindowShells = new ArrayList();
        //openWindowModels.add(shellModel);
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
    
    public ProjectModel addProject(ProjectModel newProject) throws NameAlreadyExistsException{
        if(this.okToAdd(newProject.name())){
            projects.add(newProject);
        }else {
            throw new NameAlreadyExistsException(this, newProject);
        }
        return newProject;
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
    
    public void removeShell(JFrame shell){
        openWindowShells.remove(shell);
    }
    
    public void openAddProjectShell(){
        for(Object shell : openWindowShells){
            if(shell.getClass() == AddNewProjectShell.class)
                return;
        }
        openWindowShells.add(new AddNewProjectShell(this));
    }
}
