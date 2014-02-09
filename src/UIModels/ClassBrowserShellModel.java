/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Models.ClassModel;
import Models.MethodModel;
import Models.ProjectModel;
import Models.VariableModel;
import javax.swing.DefaultListModel;

/**
 *
 * @author Arthur
 */
public class ClassBrowserShellModel extends BaseUIModel{
    private DefaultListModel classList;
    private ClassModel selectedClass;
    private VariableModel selectedVariable;
    private MethodModel selectedMethod;
    private ProjectModel baseProject;
    
    public ClassBrowserShellModel(ProjectModel project){
        this.baseProject = project;
    }
}
