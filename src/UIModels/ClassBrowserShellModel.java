/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

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
    
    public ClassBrowserShellModel(ProjectModel project){
        super();
        this.baseProject = project;
        shell = new ClassBrowserShell(this);
    }
    
    private DefaultListModel setUpAndFillListModel(List<BaseModel> aList){
        DefaultListModel model = new DefaultListModel();
        for(BaseModel b : aList){
            model.addElement(b);
        }
        return model;
    }
    
    public ProjectModel getProject(){
        return baseProject;
    }
    
}
