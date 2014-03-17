/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Models.ClassModel;
import Models.MethodModel;
import Models.ProjectModel;
import Models.VariableModel;
import UIShells.ClassBrowserShell;
import javax.swing.DefaultListModel;

/**
 *
 * @author Arthur
 */
public class ClassBrowserShellModel extends BaseUIModel{
    private final ClassBrowserShell shell;
    private DefaultListModel classList;
    private DefaultListModel variableList;
    private DefaultListModel methodList;
    private ClassModel selectedClass;
    private VariableModel selectedVariable;
    private MethodModel selectedMethod;
    private ProjectModel baseProject;
    
    public ClassBrowserShellModel(ProjectModel project){
        this.baseProject = project;
        shell = new ClassBrowserShell(this);
    }
    
    private void fillListModels(){
        classList = new DefaultListModel();
        //this.fillListModel(baseProject.getClasses(), classList);
    }
    
}
