package MainBase;

import Exceptions.NameAlreadyExistsException;
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
    private ProjectModel selectedProject;
    private PackageModel selectedPackage;
    private ClassModel selectedClass;
    private MethodModel selectedMethod;
    private SystemMainShellModel mainShellModel;
    
    /*
     * Todo:
     * -SettingsModel
     */
    public MainApplication(){
        projects = new ArrayList();
        mainShellModel = new SystemMainShellModel(this);
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
    private boolean okToAddProjectWithName(String newProjectName){
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
    public ProjectModel addProject(String newProjectName) throws NameAlreadyExistsException{
        if(this.okToAddProjectWithName(newProjectName)){
            ProjectModel newProject = new ProjectModel(newProjectName);
            projects.add(newProject);
            mainShellModel.projectAdded(newProject);
            return newProject;
        }else
            throw new NameAlreadyExistsException(newProjectName, this);
    }
    
    public ArrayList<ProjectModel> getProjects(){
        return projects;
    }
    public ArrayList<PackageModel> packages(){
        if (selectedProject != null)
            return selectedProject.packageList();
        else
            return new ArrayList();
    }
    public ArrayList<ClassModel> classes(){
        if (selectedPackage != null)
            return selectedPackage.classList();
        else
            return new ArrayList();
    }
    public ArrayList<MethodModel> methods(){
        if (selectedClass != null)
            return selectedClass.methods();
        else
            return new ArrayList();
    }
    
    public ProjectModel getSelectedProject(){
        return selectedProject;
    }
    public ClassModel getSelectedClass(){
        return selectedClass;
    }
    public PackageModel getSelectedPackage(){
        return selectedPackage;
    }
    public MethodModel getSelectedMethod(){
        return selectedMethod;
    }
    
    /**
     * #todo: throw an error if object model is not found
     * @param aProject 
     */
    public void setSelectedProject(ProjectModel aProject){
        if(aProject == selectedProject)
            return;
        if(projects.contains(aProject)){
            selectedProject = aProject;
            this.onProjectSelected();
        }
    }
    public void setSelectedPackage(PackageModel aPackage){
        //aPackage is null
        if(selectedProject.packages().containsKey(aPackage.name())){
            selectedPackage = aPackage;
            this.onPackageSelected();
        }
    }
    public void setSelectedClass(ClassModel aClass){
        if(selectedPackage.classes().containsKey(aClass.name())){
            selectedClass = aClass;
            this.onClassSelected();
        }
    }
    public void setSelectedMethod(MethodModel aMethod){
        if(selectedClass.methods().contains(aMethod)){
            selectedMethod = aMethod;
            this.onMethodSelected();
        }
    }
    
    /**
     * these methods reset the required variables and send 
     * the appropriate messages to the shell model
     * so it will update its lists
     */
    private void onProjectSelected(){
            selectedPackage = null;
            selectedClass = null;
            selectedMethod = null;
    }
    private void onPackageSelected(){
            selectedClass = null;
            selectedMethod = null;
    }
    private void onClassSelected(){
            selectedMethod = null;
    }
    private void onMethodSelected(){
        //may not be needed
    }
    
    public Object getSelected(){
        if(selectedMethod != null)
            return selectedMethod;
        if(selectedClass != null)
            return selectedClass;
        if(selectedPackage != null)
            return selectedPackage;
        if(selectedProject != null)
            return selectedProject;
        else
            return null;
    }
    
}
