/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Exceptions.NameAlreadyExistsException;
import MainBase.MainApplication;
import Models.BaseModel;
import Models.ClassModel;
import Models.MethodModel;
import Models.ProjectModel;
import Models.VariableModel;
import UIShells.ClassBrowserShell;
import java.util.List;
import javax.swing.DefaultListModel;

/**
 *
 * @author Arthur
 */
public class ClassBrowserShellModel extends BaseUIModel{
    private final ClassBrowserShell shell;
    private ClassModel selectedClass;
    private VariableModel selectedVariable;
    private MethodModel selectedMethod;
    private final ProjectModel baseProject;
    
    public ClassBrowserShellModel(MainApplication main){
        super(main);
        this.baseProject = main.getSelectedProject();
        shell = new ClassBrowserShell(this);
    }
    
    public ProjectModel getProject(){
        return baseProject;
    }
    
    public ClassModel getSelectedClass(){
        return selectedClass;
    }
    
    public ClassModel addClassToPackage(ClassModel newClass) throws NameAlreadyExistsException{
        if(this.selectedClass == null)
            selectedClass = newClass;
        return baseProject.addClass(newClass);
    }
    
    public ClassModel addClassToClass(ClassModel newClass){
        
    }
}
