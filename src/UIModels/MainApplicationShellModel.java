/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Models.ProjectModel;
import UIShells.MainApplicationShell;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 *
 * @author Arthur
 */
public class MainApplicationShellModel extends BaseUIModel{
    
    public MainApplicationShellModel(MainModel current){
        this.applicationMain = current;
        MainApplicationShell.showShellWithModel(this);
    }
    
    public DefaultListModel fillListModel(){
        DefaultListModel listModel = new DefaultListModel();
        for(ProjectModel projectModel : applicationMain.projects()){
            listModel.addElement(projectModel);
        }
        return listModel;
    }
    
    public void openNewProjectShell(){
        applicationMain.openAddProjectShell();
    }
    
    
}
