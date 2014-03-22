/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels;

import Exceptions.NameAlreadyExistsException;
import MainBase.MainApplication;
import Models.ProjectModel;
import UIShells.ProjectSelectionShell;
import javax.swing.DefaultListModel;

/**
 *
 * @author arthur
 */
public class ProjectSelectionShellModel extends BaseUIModel{
    private DefaultListModel projectList;
    
    public ProjectSelectionShellModel(MainApplication main){
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
        shell.setVisible(true);
        return (ProjectSelectionShell)shell;
    }
    
    public ProjectModel addProject(ProjectModel newProject) throws NameAlreadyExistsException{
        projectList.addElement(main.addProject(newProject));
        return newProject; //in case the shell wont update itself when the listmodel changes
    }
}
