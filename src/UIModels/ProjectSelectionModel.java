/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels;

import Exceptions.DoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import MainBase.MainApplication;
import Models.ProjectModel;
import UIShells.AddNewProjectShell;
import UIShells.ProjectSelectionShell;
import javax.swing.DefaultListModel;

/**
 *
 * @author arthur
 */
public class ProjectSelectionModel extends BaseUIModel{
    private ProjectSelectionShell shell;
    private DefaultListModel projectList;
    
    public ProjectSelectionModel(MainApplication main){
        super(main);
        this.fillProjectList();
    }
    
    private void fillProjectList(){
        projectList = new DefaultListModel();
        this.fillListModel(main.getProjects(), projectList);
    }
    
    public ProjectSelectionShell showShell(){
        if(shell == null)
            shell =  new ProjectSelectionShell(this);
        if(!shell.isVisible())
            shell.setVisible(true);
        main.addShell(shell);
        return (ProjectSelectionShell)shell;
    }
    
    public ProjectModel addProject(ProjectModel newProject) throws NameAlreadyExistsException{
        projectList.addElement(main.addProject(newProject));
        if(main.getSelectedProject() == null)
            return this.setSelectedProject(newProject);
        return newProject; //in case the shell wont update itself when the listmodel changes
    }
    
    public ProjectModel removeProject(ProjectModel aProject) throws DoesNotExistException{
        projectList.removeElement(main.removeProject(aProject));
        if(main.getSelectedProject() == aProject){
            this.setSelectedProject(this.getFirstProject());
        }
        return aProject;
    }
    
    public DefaultListModel getListModel(){
        return projectList;
    }
    
    public ProjectModel setSelectedProject(ProjectModel aProject){
        return main.setSelectedProejct(aProject);
    }
    
    public ProjectModel getSelectedProject(){
        return main.getSelectedProject();
    }
    
    private ProjectModel getFirstProject(){
        if(main.getProjects().isEmpty())
            return null;
        return main.getProjects().get(0);
    }
    
    public void close(){
        main.removeModel(this);
        if(shell != null)
            shell.dispose();
    }
    
    public AddNewProjectShell addProject(){
        return main.openAddProjectShell();
    }
    
    public MainApplication getMain(){
        return main;
    }
}
