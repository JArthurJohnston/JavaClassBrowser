package MainBase;

import Models.*;
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
    
    public MainApplication(){
        projects = new ArrayList();
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
    
    public ProjectModel addProject(ProjectModel newProject){
        projects.add(newProject);
        return newProject;
    }
    
    public ProjectModel addProject(String newProjectName){
        if(this.okToAdd(newProjectName)){
            ProjectModel newProject = new ProjectModel(newProjectName);
            return this.addProject(newProject);
        }
        return null;
    }
}
