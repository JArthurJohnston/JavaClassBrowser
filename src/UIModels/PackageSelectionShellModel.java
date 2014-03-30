/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels;

import Exceptions.NameAlreadyExistsException;
import Exceptions.PackageDoesNotExistException;
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
    
    public PackageModel getSelectedPackage(){
        if(selectedPackage == null)
            if(!this.getPackageList().isEmpty())
                selectedPackage = (PackageModel)this.getPackageList().get(0);
        return selectedPackage;
    }
    
    public PackageModel setSelectedPackage(PackageModel aPackage){
        if(selectedPackage != aPackage)
            selectedPackage = aPackage;
        return selectedPackage;
    }
    
    public ArrayList getPackageList(){
        return this.selectedProject().getPackageList();
    }
    
    public ProjectModel selectedProject(){
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
    
    public PackageModel addPackage(PackageModel newPackage) throws NameAlreadyExistsException{
        return this.selectedProject().addPackage(newPackage);
    }
    
    public PackageModel removePackage(PackageModel aPackage) throws PackageDoesNotExistException{
        return this.selectedProject().removePackage(aPackage);
    }
    
}
