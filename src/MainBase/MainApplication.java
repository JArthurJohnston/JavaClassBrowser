package MainBase;

import Exceptions.DoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Models.*;
import UIModels.BaseUIModel;
import UIModels.ProjectSelectionModel;
import UIShells.AddNewProjectShell;
import UIShells.BaseUIShell;
import UIShells.ProjectSelectionShell;
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
    private ArrayList <BaseUIModel>openWindowModels;
    private ArrayList <BaseUIShell>openWindowShells;
    private String userName;
    private ProjectModel selectedProject;
    
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
        if(this.okToAdd(newProject.name()))
            projects.add(newProject);
        else {
            throw new NameAlreadyExistsException(this, newProject);
        }
        return newProject;
    }
    
    public ProjectModel removeProject(ProjectModel aProject) throws DoesNotExistException{
        if(this.okToDelete(aProject))
            projects.remove(aProject);
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
    
    
    public void openAddProjectShell(){
        if(this.okToOpenShell(AddNewProjectShell.class))
            openWindowShells.add(new AddNewProjectShell(this));
    }
    
    public ProjectSelectionModel openProjectSelectionShell(){
        ProjectSelectionModel model = null;
        if(this.okToOpenShell(ProjectSelectionModel.class)){
            model = new ProjectSelectionModel(this);
            openWindowModels.add(model);
        }
        return model;
    }
    
    /**
     * boolean okToOpenShell.
     * used to check and see if certain shells are already open.
     * some shells don't need this check.
     * Note: the method takes a model as a parameter, since EVERY
     * shill will have a corresponding model. and since Main should only
     * be communicating with that model, not the actual shell.
     * 
     * @param modelClass
     * @return 
     */
    private boolean okToOpenShell(Object modelClass){
        if(openWindowModels.isEmpty())
            return true;
        for(BaseUIModel model : openWindowModels){
            if(model.getClass() == modelClass)
                return false;
        }
        return true;
    }
    
    public ProjectModel setSelectedProejct(ProjectModel aProject){
        this.selectedProject = aProject;
        return selectedProject;
    }
    public ProjectModel getSelectedProject(){
        return selectedProject;
    }
    
    public BaseUIShell addShell(BaseUIShell shell){
        openWindowShells.add(shell);
        return shell;
    }
    
    public void removeModel(BaseUIModel model){
        openWindowModels.remove(model);
    }
}
