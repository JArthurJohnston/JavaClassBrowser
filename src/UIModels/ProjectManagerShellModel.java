/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Exceptions.DoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Exceptions.NothingSelectedException;
import MainBase.MainApplication;
import Models.ProjectModel;
import UIShells.AddNewProjectShell;
import UIShells.ProjectManagerShell;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;

/**
 *
 * @author Arthur
 */
public class ProjectManagerShellModel extends BaseUIModel{
    private MainApplication main;
    private ProjectModel selected;
    private DefaultListModel projectList;
    private ProjectManagerShell shell;
    
    /*
     * I either need to keep track of ALL open shells via the 
     * open shells array, or hold onto a shell variable
     * and throw all OTHER shells into the array...
     */
    
    public ProjectManagerShellModel(MainApplication main){
        projectList = new DefaultListModel();
        this.main = main;
        this.fillListModel(main.getProjects(), projectList);
        openShells = new ArrayList();
        openShellModels = new ArrayList();
        shell = new ProjectManagerShell(this);
        shell.setVisible(true);
    }
    
    public ProjectModel getSelected(){
        if(selected == null){
            if(main.getProjects().size() > 0) {
                selected =  main.getProjects().get(0);
            }
        }
        return selected; 
    }
    
    public void removeShell(JFrame aShell){
        openShells.remove(aShell);
    }
    
    public DefaultListModel getListModel(){
        return projectList;
    }
    
    public String getUserName(){
        return main.getUserName();
    }
    
    public void setUserName(String newUserName){
        this.getSelected().setUserName(newUserName);
    }
    public MainApplication getApplication(){
        return main;
    }
    
    public ProjectModel addProject(String newProjectName) throws NameAlreadyExistsException{
        return main.addProject(newProjectName);
    }
    
    public void removeProject(ProjectModel aProject){
        //WRITE ME!!!
    }
    
    public ProjectModel projectAdded(ProjectModel newProject){
        projectList.addElement(newProject);
        return newProject;
    }
    
    /**
     * meant for shells that shouldn't have more than one 
     * instance open at a time
     * @param shell
     * @return 
     */
    public boolean okToOpen(Object shell){
        for(JFrame openShell : openShells){
            if(openShell.getClass() == shell){
                openShell.toFront();
                return false;
            }
        }
        return true;
    }
    
    public void openAddProject(){
        if(this.okToOpen(AddNewProjectShell.class)) {
            openShells.add(new AddNewProjectShell(this));
        }else{
            //get the currently opened shell and bring it to front.
        }
    }
    
    public void openClassBrowser() throws NothingSelectedException{
        if(this.getSelected() == null) {
            throw new NothingSelectedException("Project");
        }
        else {
            openShellModels.add(new ClassBrowserShellModel(this.getSelected()));
        }
    }
    
    public void setSelection(ProjectModel aProject) throws DoesNotExistException{
        if(!projectList.contains(aProject)) {
            throw new DoesNotExistException(this, aProject);
        } else {
            this.selected = aProject;
        }
    }
}
