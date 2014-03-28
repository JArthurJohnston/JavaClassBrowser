/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels;

import MainBase.MainApplication;
import Models.PackageModel;
import Models.ProjectModel;
import UIShells.PackageSelectionShell;
import java.util.ArrayList;

/**
 *
 * @author arthur
 */
public class PackageSelectionShellModel extends BaseUIModel{
    private PackageSelectionShell shell;
    private PackageModel selectedPackage;
    
    public PackageSelectionShellModel(MainApplication main){
        super(main);
    }
    
    public PackageModel selectedPackage(){
        return selectedPackage;
    }
    
    public ArrayList getPackageList(){
        return this.selectedProject().getPackageList();
    }
    
    private ProjectModel selectedProject(){
        return main.getSelectedProject();
    }
    
    public void close(){
        main.removeModel(this);
    }
    
    public PackageSelectionShell getShell(){
        if(shell == null)
            shell = new PackageSelectionShell(this);
        return shell;
    }
}
