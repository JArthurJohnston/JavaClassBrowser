/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Exceptions.NameAlreadyExistsException;
import MainBase.MainApplication;
import Models.ProjectModel;
import UIShells.BaseUIModel;
import UIShells.ProjectManagerShell;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 *
 * @author Arthur
 */
public class ProjectManagerShellModel extends BaseUIModel{
    private MainApplication main;
    private ProjectModel selected;
    private DefaultListModel projectList;
    private ProjectManagerShell shell;
    private ArrayList openShells;
    
    public ProjectManagerShellModel(MainApplication main){
        projectList = new DefaultListModel();
        this.main = main;
        this.fillListModel(main.getProjects(), projectList);
        openShells = new ArrayList();
        shell = new ProjectManagerShell(this);
        openShells.add(shell);
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
    
}
