/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import MainBase.MainApplication;
import Models.ProjectModel;
import UIShells.BaseUIModel;
import UIShells.ProjectManagerShell;
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
    
    public ProjectManagerShellModel(MainApplication main){
        projectList = new DefaultListModel();
        this.main = main;
        this.fillListModel(main.getProjects(), projectList);
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
    
}
