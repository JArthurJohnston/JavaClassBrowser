/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Models.*;

/**
 *
 * @author Arthur
 */
public class SystemBrowserShellModel extends BaseUIModel {
    private ProjectModel currentProject;
    
    public SystemBrowserShellModel(MainModel main){
        this.applicationMain = main;
        currentProject = new ProjectModel();
    }
    public SystemBrowserShellModel(MainModel main, ProjectModel project){
        this.applicationMain = main;
    }
}
