/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import MainBase.MainApplication;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 * Top level Model for all UIModel classes
 * each UIModel class must extend BaseUIModel
 * @author Arthur
 */
public class BaseUIModel {
    public MainApplication application;
    
    
    public void fillListModel(ArrayList newList, DefaultListModel myList){
        for(int i=0; i<newList.size(); i++){
            myList.addElement(newList.get(i));
        }
    }
    
    /**
     * each instance of a UIModel class MUST be constructed
     * with the currently running MainApplication
     */
}
