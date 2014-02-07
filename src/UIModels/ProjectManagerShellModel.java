/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Exceptions.NameAlreadyExistsException;
import MainBase.MainApplication;
import Models.ProjectModel;
import UIShells.AddNewProjectShell;
import UIShells.BaseUIModel;
import UIShells.ProjectManagerShell;
import java.awt.Frame;
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
    private ArrayList<JFrame> openShells;
    
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
    
    public void setSelected(ProjectModel aProject){
        
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
    
    public void addProject(String newProjectName) throws NameAlreadyExistsException{
        main.addProject(newProjectName);
    }
    
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
    
}
