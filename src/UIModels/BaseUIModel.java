/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

/**
 * Top level Model for all UIModel classes
 * each UIModel class must extend BaseUIModel
 * @author Arthur
 */
public class BaseUIModel {
    public MainModel applicationMain;
    
    //the default constructor should only be used in testing, if at all.
    public BaseUIModel(){
        //Never used... I hate java.
    }
    /**
     * each instance of a UIModel class MUST be constructed
     * with the currently running MainModel
     * @param currentMain 
     */
    public BaseUIModel(MainModel currentMain){
        this.applicationMain = currentMain;
    }
    
}
