package MainBase;

import Exceptions.DoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Models.*;
import UIModels.BaseUIModel;
import UIModels.ProjectSelectionShellModel;
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
        if(this.okToAdd(newProject.name())){
            if(projects.isEmpty())
                this.setSelectedProejct(newProject);
            projects.add(newProject);
            this.projectAdded(newProject);
        }else {
            throw new NameAlreadyExistsException(this, newProject);
        }
        return newProject;
    }
    
    public ProjectModel removeProject(ProjectModel aProject) throws DoesNotExistException{
        if(this.okToDelete(aProject)) {
            projects.remove(aProject);
            if(this.selectedProject == aProject){
                if(!this.projects.isEmpty())
                    selectedProject = projects.get(0);
                else selectedProject = null;
            }
            this.projectRemoved(aProject);
        }
        else {
            throw new DoesNotExistException(this, aProject);
        }
        return aProject;
    }
    
    private void projectAdded(ProjectModel newProject){
        for(BaseUIModel model : openWindowModels){
            model.projectAdded(newProject);
        }
    }
    private void projectRemoved(ProjectModel newProject){
        for(BaseUIShell shell : openWindowShells){
            shell.projectRemoved(newProject);
        }
    }
    
    public String getUserName(){
        return userName;
    }
    public void setUserName(String newUserName){
        this.userName = newUserName;
    }
    
    public void removeShell(Object shellClass){
        BaseUIShell aShell = null;
        for(BaseUIShell shell : openWindowShells){
            if(shell.getClass() == shellClass){
                aShell = shell;
                break;
            }
        }
        if(aShell != null)
            openWindowShells.remove(aShell);
    }
    
    public void openAddProjectShell(){
        if(this.okToOpenShell(AddNewProjectShell.class))
            openWindowShells.add(new AddNewProjectShell(this));
    }
    public ProjectSelectionShellModel openProjectSelectionShell(){
        ProjectSelectionShellModel model = null;
        if(this.okToOpenShell(ProjectSelectionShellModel.class)){
            model = new ProjectSelectionShellModel(this);
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
    
    public void setSelectedProejct(ProjectModel aProject){
        this.selectedProject = aProject;
    }
    public ProjectModel getSelectedProject(){
        return selectedProject;
    }
    
    public BaseUIShell addShell(BaseUIShell shell){
        openWindowShells.add(shell);
        return shell;
    }
}
