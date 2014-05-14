package MainBase;

import Exceptions.AlreadyExistsException;
import Exceptions.DoesNotExistException;
import MainBase.Events.ModelEventListener;
import Models.*;
import UIModels.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

/**
 * MainApplication is the top-level model for the whole program
 * it keeps track of all active projects and any other non-coding
 * functions of the program. Its always the first thing that runs
 * when the program is started.
 * 
 * @author Arthur
 */
public class MainApplication {
    private final ArrayList<ProjectModel> projects;
    private final LinkedList <BaseUIController>openWindowModels;
    private String userName;
    private ProjectModel selectedProject;
    private Vector<ModelEventListener> listeners;
    
    public MainApplication(){
        userName = System.getProperty("user.name");
        projects = new ArrayList();
        openWindowModels = new LinkedList();
    }
    
    synchronized public void addModelListener(ModelEventListener l){
        if(listeners == null)
            listeners = new Vector();
        listeners.add(l);
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
    
    public ProjectModel addProject(ProjectModel newProject) throws AlreadyExistsException{
        if(this.okToAdd(newProject.name()))
            projects.add(newProject);
        else {
            throw new AlreadyExistsException(this, newProject);
        }
        newProject.setMain(this);
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
        for(BaseUIController model : openWindowModels){
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
    
    public BaseUIController addModel(BaseUIController model){
        openWindowModels.add(model);
        return model;
    }
    
    public void removeModel(BaseUIController model){
        openWindowModels.remove(model);
    }
    
    public void addUpdateShells(BaseModel newModel){
        for(BaseUIController m : openWindowModels){
            m.modelAdded(newModel);
        }
    }
    
    public void removeUpdateShells(BaseModel newModel){
        for(BaseUIController m : openWindowModels){
            m.modelRemoved(newModel);
        }
    }
    
    public void changeUpdateShells(BaseModel newModel){
        for(BaseUIController m : openWindowModels){
            m.modelChanged(newModel);
        }
    }
    
    public BrowserUIController openSystemBrowser(){
        openWindowModels.add(new BrowserUIController(this));
        return (BrowserUIController)openWindowModels.getLast();
    }
}
