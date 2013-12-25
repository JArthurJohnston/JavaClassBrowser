package MainBase;

import Models.*;
import UIModels.NewProjectShellModel;
import UIModels.SystemMainShellModel;
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
    private ArrayList<ProjectModel> projects;
    private SystemMainShellModel main;
    
    /*
     * Todo:
     * -SettingsModel
     */
    public MainApplication(){
        projects = new ArrayList();
        main = new SystemMainShellModel(this);
    }
    /**
     * This Constructor is for Testing purposes ONLY
     * @param testProjects 
     */
    public MainApplication(ArrayList testProjects){
        this.projects = testProjects;
    }
    /*
     * Todo: Methods
     * -check for saved projects onLoad
     */
    
    public void openAddProjectShell(){
       new NewProjectShellModel(this);
    }
    /**
     * 
     * @param newProject
     * @return 
     */
    public boolean okToAddProjectWithName(String newProjectName){
        if(projects.isEmpty())
            return true;
        if(newProjectName == null)
            return false;
        if(newProjectName == "")
            return false;
        for(ProjectModel i : projects){
            if(i.name().equals(newProjectName))
                return false;
        }
        return true;
    }
    /**
     * the calling UIModel should always call okToAddProject() 
     *  before calling addProject()
     * @param newProject 
     */
    public void addProject(ProjectModel newProject){
        projects.add(newProject);
        main.projectAdded(newProject);
    }
    
    public ArrayList<ProjectModel> projects(){
        return projects;
    }
}