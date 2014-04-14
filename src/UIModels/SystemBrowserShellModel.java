/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Exceptions.NameAlreadyExistsException;
import MainBase.MainApplication;
import Models.*;
import UIShells.SystemBrowserShell;
import java.util.LinkedList;

/**
 *
 * @author Arthur
 */
public class SystemBrowserShellModel extends BaseUIModel{
    private SystemBrowserShell shell;
    private ProjectModel selectedProject;
    private BaseModel selectedModel;
    private ClassModel selectedClass;
    
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
    
    public void setSelectedClass(ClassModel aClass){
        this.selectedClass = aClass;
    }
    
    public ClassModel getSelectedClass(){
        if(selectedClass == null)
            if(!selectedProject.getClassList().isEmpty())
                selectedClass = selectedProject.getClassList().getFirst();
        return this.selectedClass;
    }
    
    public LinkedList<PackageModel> getPackages(){
        return selectedProject.getPackageList();
    }
    
    public void addPackage(PackageModel aPackage) throws NameAlreadyExistsException{
        selectedProject.addPackage(aPackage);
    }
    
    public void setSelected(BaseModel aModel){
        selectedModel = aModel;
    }

    @Override
    public void modelAdded(BaseModel newModel) {
        this.getShell().addModel(newModel);
    }

    @Override
    public void modelChanged(BaseModel newModel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void modelRemoved(BaseModel newModel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
