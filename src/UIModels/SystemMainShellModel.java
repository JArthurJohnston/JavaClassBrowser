/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Models.*;
import UIShells.SystemMainShell;
import javax.swing.DefaultListModel;

/**
 *
 * @author Arthur
 */
public class SystemMainShellModel extends BaseUIModel {
    private SystemMainShell shell;
    private DefaultListModel packages;
    private PackageModel selectedPackage;
    
    
    public SystemMainShellModel(ProjectModel project){
        packages = new DefaultListModel();
        this.fillListModel(project.getPackageList(), packages);
    }
    
    public DefaultListModel getPackageList(){
        return packages;
    }
    
}
