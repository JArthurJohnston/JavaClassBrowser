/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Exceptions.NameAlreadyExistsException;
import Exceptions.VeryVeryBadException;
import MainBase.MainApplication;
import Models.ClassModel;
import Models.PackageModel;
import Models.ProjectModel;
import UIShells.ClassBrowserShell;
import java.util.LinkedList;

/**
 *
 * @author Arthur
 */
public class ClassBrowserShellModel extends BaseUIModel{
    private ClassBrowserShell shell;
    private ProjectModel selectedProject;
    private PackageModel selectedModel;
    
    public ClassBrowserShellModel(MainApplication main){
        super(main);
        selectedProject = main.getSelectedProject();
        this.openShell();
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
    public ClassBrowserShell openShell(){
        if(shell == null)
            shell = new ClassBrowserShell(this);
        return shell;
    }
    public void closeShell(){
        if(shell != null){
            shell.dispose();
            shell = null;
        }
    }
}
