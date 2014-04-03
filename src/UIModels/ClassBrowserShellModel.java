/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Exceptions.DoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Exceptions.VeryVeryBadException;
import MainBase.MainApplication;
import Models.ClassModel;
import Models.PackageModel;
import Models.ProjectModel;
import UIShells.ClassBrowserShell;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            selectedModel = this.selectedProject.getDefaultPackage();
        if(!selectedModel.getClassList().isEmpty())
            selectedModel = (PackageModel) selectedModel.getClassList().getFirst();
        return selectedModel;
    }
    
    public ClassModel addClass(ClassModel newClass) throws NameAlreadyExistsException{
        return this.getSelected().addClass(newClass);
    }
    public ClassModel removeClass(ClassModel aClass) throws VeryVeryBadException{
        return this.getSelected().removeClass(aClass);
    }
    
    public ProjectModel selectedProject(){
        return selectedProject;
    }
    public void close(){
        main.removeModel(this);
    }
}
