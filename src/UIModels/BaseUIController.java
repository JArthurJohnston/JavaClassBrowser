/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import MainBase.Events.ModelEvents.ModelAddedEvent;
import MainBase.Events.ModelEvents.ModelChangedEvent;
import MainBase.Events.ModelEvents.ModelEventHandler;
import MainBase.Events.ModelEvents.ModelEventListener;
import MainBase.Events.ModelEvents.ModelRemovedEvent;
import MainBase.MainApplication;
import Models.BaseModel;
import Models.ProjectModel;
import UIShells.BaseUIShell;
import java.util.List;
import javax.swing.DefaultListModel;

/**
 * a class for any shared methods for all the UI stuff
 * 
 * @author Arthur
 */
public class BaseUIController implements ModelEventListener{
    protected MainApplication main;
    protected ProjectModel selectedProject;
    
    public BaseUIController(){}
    
    public BaseUIController(MainApplication main){
        this.main = main;
        this.selectedProject = main.getSelectedProject();
        ModelEventHandler.addListener(this); //
    }
    
    public void setProject(ProjectModel aProject){
        selectedProject = aProject;
    }
    
    protected BaseUIShell shell(){
        return null;
    }
    
    public void close(){
        ModelEventHandler.removeListener(this);
    }
    
    /**
     * for testing purposes only.
     * @param main
     * @return 
     */
    protected BaseUIController setMain(MainApplication main){
        this.main = main;
        return this;
    }
    
    public ProjectModel getSelectedProject(){
        return selectedProject;
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
    public void setSelected(BaseModel aModel){
        this.shell().selectionChanged(aModel);
    }

    @Override
    public void modelAdded(ModelAddedEvent e) {
        this.shell().modelAdded(e.getModel());
    }

    @Override
    public void modelRemoved(ModelRemovedEvent e) {
        this.shell().modelRemoved(e.getModel());
    }

    @Override
    public void modelChanged(ModelChangedEvent e) {
        this.shell().modelChanged(e.getModel());
    }
}
