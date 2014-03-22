/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import MainBase.MainApplication;
import Models.*;
import UIShells.BaseUIShell;
import java.util.List;
import javax.swing.DefaultListModel;

/**
 * a class for any shared methods for all the UI stuff
 * 
 * @author Arthur
 */
public class BaseUIModel {
    protected BaseUIShell shell;
    protected MainApplication main;
    
    public BaseUIModel(){}
    public BaseUIModel(MainApplication main){
        this.main = main;
    }
    
    /**
     * a method for filling list models.
     * I used a traditional for-loop in leu of a for-each loop
     * to make the method a bit more dynamic. this way, you can pass
     * in a list containing any object you want, and it will work.
     * @param list
     * @param listModel 
     */
    public void fillListModel(List list, DefaultListModel listModel){
        for(int i=0; i < list.size(); i++){
            listModel.addElement(list.get(i));
        }
    }
    /*
    each shell overrides this if applicable
    this way main can tell each open shell that something has been added,
    if a shell needs to know this, it will. otherwise its a noop
    */
    public void projectAdded(ProjectModel added){}
    public void classAdded(ClassModel added){}
    public void methodAdded(MethodModel added){}
    public void packageAdded(PackageModel added){}
    public void variableAdded(VariableModel added){}
    
    public void projectRemoved(ProjectModel removed){}
    public void classRemoved(ClassModel removed){}
    public void packageRemoved(PackageModel removed){}
    public void methodRemoved(MethodModel removed){}
    public void variableRemoved(VariableModel removed){}
}
