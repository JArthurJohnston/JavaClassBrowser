/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Models.ProjectModel;
import UIShells.SystemMainShell;
import javax.swing.DefaultListModel;

/**
 *
 * @author Arthur
 */
public class SystemMainShellModel extends BaseUIModel{
    private DefaultListModel<ProjectModel> list;
    
    
    public SystemMainShellModel(MainApplication current){
        this.application = current;
        list = new DefaultListModel();
        this.fillListModel();
        shell = new SystemMainShell(this);
    }
    
    private void fillListModel(){
        for(ProjectModel p : application.projects()){
            list.addElement(p);
        }
    }
    
    public DefaultListModel getList(){
        return list;
    }
    
    public void openNewProjectShell(){
        new NewProjectShellModel(application);
    }
    
    public void projectAdded(ProjectModel newProject){
        list.addElement(newProject);
    }
}
