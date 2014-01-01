/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels.UIPanelsModels;

import Models.ClassModel;
import UIModels.BaseUIModel;
import UIShells.UIPanels.ClassBrowserPanel;
import javax.swing.DefaultListModel;

/**
 *
 * @author Arthur
 */
public class ClassBrowserPanelModel extends BaseUIModel{
    private ClassBrowserPanel shell;
    private ClassModel model;
    private DefaultListModel instanceMethods;
    private DefaultListModel classMethods;
    private DefaultListModel instanceVariables;
    private DefaultListModel classVariables;
    
    public ClassBrowserPanelModel(ClassModel model){
        this.model = model;
        this.setUpListModels();
        this.fillLists();
        this.shell = new ClassBrowserPanel(this);
    }
    
    private void setUpListModels(){
        classVariables = new DefaultListModel();
        instanceVariables = new DefaultListModel();
        instanceMethods = new DefaultListModel();
        classMethods = new DefaultListModel();
    }
    
    private void fillLists(){
        this.fillListModel(model.getClassMethods(), classMethods);
        this.fillListModel(model.getInstanceMethods(), instanceMethods);
        this.fillListModel(model.getClassMethods(), classVariables);
        this.fillListModel(model.getInstanceVariables(), instanceVariables);
    }
    
    public DefaultListModel getInstVarList(){
        return instanceVariables;
    }
    public DefaultListModel getClassVarsList(){
        return classVariables;
    }
    public DefaultListModel getInstMethodList(){
        return instanceMethods;
    }
    public DefaultListModel getClassMethodList(){
        return classMethods;
    }
    /**
     * Used for testing
     * @return 
     */
    public ClassBrowserPanel getShell(){
        return shell;
    }
    
}
