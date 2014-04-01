/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Exceptions.NameAlreadyExistsException;
import MainBase.MainApplication;
import Models.ClassModel;
import Models.MethodModel;
import Models.PackageModel;
import Models.ProjectModel;
import Models.VariableModel;
import UIShells.ClassBrowserShell;
import java.util.LinkedList;

/**
 *
 * @author Arthur
 */
public class ClassBrowserShellModel extends BaseUIModel{
    private final ClassBrowserShell shell;
    private ProjectModel selectedProject;
    private PackageModel selectedModel;
    
    public ClassBrowserShellModel(MainApplication main){
        super(main);
        selectedProject = main.getSelectedProject();
        shell = new ClassBrowserShell(this);
    }
    
    public LinkedList getClasses(){
        return this.selectedProject.getClassList();
    }
    
    public void setSelected(PackageModel newSelected){
        selectedModel = newSelected;
    }
    
    public PackageModel getSelected(){
        if(selectedModel == null)
            selectedModel = this.selectedPackage();
        return selectedModel;
    }
    
    public ClassModel addClass(ClassModel newClass) throws NameAlreadyExistsException{
        return this.getSelected().addClass(newClass);
    }
    
    public PackageModel selectedPackage(){
        return main.selectedPackage();
    }
}
