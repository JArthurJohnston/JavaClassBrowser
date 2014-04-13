package MainBase;

import Exceptions.DoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Models.*;
import UIModels.*;
import UIShells.AddNewProjectShell;
import java.util.ArrayList;
import java.util.LinkedList;

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
    private LinkedList <BaseUIModel>openWindowModels;
    private String userName;
    private ProjectModel selectedProject;
    private PackageModel selectedPackage;
    
    public MainApplication(){
        userName = System.getProperty("user.name");
        projects = new ArrayList();
        openWindowModels = new LinkedList();
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
    
    
    public AddNewProjectShell openAddProjectShell(){
        return AddNewProjectShell.getInstance(this);
    }
    
    public ProjectSelectionModel openProjectSelection(){
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
    
    public BaseUIModel addModel(BaseUIModel model){
        openWindowModels.add(model);
        return model;
    }
    
    public void removeModel(BaseUIModel model){
        openWindowModels.remove(model);
    }
    
    public PackageModel selectedPackage(){
        if(this.selectedProject == null)
            return null;
        if(this.selectedPackage == null){
            if(selectedProject.getPackages().isEmpty())
                return null;
            selectedPackage = selectedProject.getPackageList().get(0);
        }
        return selectedPackage;
    }
    
    public PackageModel setSelectedPackage(PackageModel aPackage){
        selectedPackage = aPackage;
        return selectedPackage;
    }
    
    public ClassBrowserShellModel openAddClassBrowser(){
        ClassBrowserShellModel newShellModel;
        openWindowModels.add(newShellModel = new ClassBrowserShellModel(this));
        return newShellModel;
    }
    
    public PackageSelectionShellModel openPackageSelection(){
        PackageSelectionShellModel shell;
        openWindowModels.add(shell = new PackageSelectionShellModel(this));
        return shell;
    }
    
    public ClassBrowserShellModel openClassBrowser(){
        openWindowModels.add(new ClassBrowserShellModel(this));
        return (ClassBrowserShellModel)openWindowModels.get(openWindowModels.size()-1);
    }
    
    public void addUpdateShells(BaseModel newModel){
        for(BaseUIModel m : openWindowModels){
            m.modelAdded(newModel);
        }
    }
    
    public SystemBrowserShellModel openSystemBrowser(){
        openWindowModels.add(new SystemBrowserShellModel(this));
        return (SystemBrowserShellModel)openWindowModels.getLast();
    }
}
