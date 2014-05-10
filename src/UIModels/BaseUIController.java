/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import MainBase.MainApplication;
import Models.BaseModel;
import Models.ProjectModel;
import java.util.List;
import javax.swing.DefaultListModel;

/**
 * a class for any shared methods for all the UI stuff
 * 
 * @author Arthur
 */
public class BaseUIController {
    protected MainApplication main;
    protected ProjectModel selectedProject;
    
    public BaseUIController(){}
    
    public BaseUIController(MainApplication main){
        this.main = main;
        this.selectedProject = main.getSelectedProject();
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
    
     public void modelAdded(BaseModel newModel){}
     public void modelChanged(BaseModel newModel){}
     public void modelRemoved(BaseModel newModel){}
     public void setSelected(BaseModel aModel){}
}
