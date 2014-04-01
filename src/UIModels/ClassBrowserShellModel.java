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
    private ClassModel selectedClass;
    private VariableModel selectedVariable;
    private MethodModel selectedMethod;
    private PackageModel selectedModel;
    
    public ClassBrowserShellModel(MainApplication main){
        super(main);
        shell = new ClassBrowserShell(this);
    }
    
    public LinkedList getClasses(){
        return this.selectedProject().getClassList();
    }
    
    public ClassModel getSelectedClass(){
        if(selectedClass == null)
            if(!this.selectedProject().getClassList().isEmpty())
                selectedClass = (ClassModel)this.selectedProject().getClassList().get(0);
        return selectedClass;
    }
    
    public PackageModel setSelected(PackageModel newSelected){
        
    }
    
    public PackageModel selectedPackage(){
        return main.selectedPackage();
    }
    
    public ProjectModel selectedProject(){
        return main.getSelectedProject();
    }
}
