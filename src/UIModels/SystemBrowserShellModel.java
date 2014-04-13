/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Exceptions.NameAlreadyExistsException;
import MainBase.MainApplication;
import Models.PackageModel;
import Models.ProjectModel;
import UIShells.SystemBrowserShell;
import java.util.LinkedList;

/**
 *
 * @author Arthur
 */
public class SystemBrowserShellModel extends BaseUIModel{
    private SystemBrowserShell shell;
    private ProjectModel selectedProject;
    
    public SystemBrowserShellModel(MainApplication main){
        this.main = main;
        this.selectedProject = main.getSelectedProject();
    }
    
    public void close(){
        //shell.dispose();
        main.removeModel(this);
    }
    
    public SystemBrowserShell getShell(){
        if(shell == null)
            shell = new SystemBrowserShell(this);
        return shell;
    }
    
    public LinkedList<PackageModel> getPackages(){
        return selectedProject.getPackageList();
    }
    
    public void addPackage(PackageModel aPackage) throws NameAlreadyExistsException{
        selectedProject.addPackage(aPackage);
    }
}
