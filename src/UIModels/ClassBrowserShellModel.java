/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Exceptions.NameAlreadyExistsException;
import Exceptions.VeryVeryBadException;
import MainBase.MainApplication;
import Models.BaseModel;
import Models.ClassModel;
import Models.MethodModel;
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
    private ClassModel selectedClass;
    private ProjectModel selectedProject;
    private PackageModel selectedModel;
    
    
    public ClassBrowserShellModel(MainApplication main){
        super(main);
        selectedProject = main.getSelectedProject();
        if(selectedProject != null)
            if(!selectedProject.getClassList().isEmpty())
                selectedClass = selectedProject.getClassList().getFirst();
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
        PackageModel selectedObject;
        if(this.selectedClass() == null)
            selectedObject = this.getSelected();
        else 
            selectedObject = this.selectedClass;
        return selectedObject.addClass(newClass);
    }
    
    public ClassModel removeClass(ClassModel aClass) throws VeryVeryBadException{
        return this.getSelected().removeClass(aClass);
    }
    
    public ProjectModel selectedProject(){
        return selectedProject;
    }
    
    public ClassModel selectedClass(){
        return selectedClass;
    }
    
    public void setSelectedMethod(MethodModel aMethod){
        this.setSelected(aMethod);
    }
    
    public void setSelectedClass(ClassModel aClass){
        this.selectedClass = aClass;
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
    
    public void modelAdded(BaseModel newModel){
        if(newModel.isClass())
            this.openShell().addClass(newModel);
        //if(newModel.isVar)
    }
}
