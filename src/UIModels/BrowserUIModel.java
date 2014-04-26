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
public class BrowserUIModel extends BaseUIModel{
    private SystemBrowserShell shell;
    private ProjectModel selectedProject;
    private BaseModel selectedModel;
    private ClassModel selectedClass;
    
    public BrowserUIModel(MainApplication main){
        super(main);
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
        shell.selectionChanged(aClass);
    }
    
    public ProjectModel getSelectedProject(){
        return selectedProject;
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
    
    @Override
    public void setSelected(BaseModel aModel){
        if(aModel != null)
            selectedModel = aModel;
    }
    
    public BaseModel getSelected(){
        return selectedModel;
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