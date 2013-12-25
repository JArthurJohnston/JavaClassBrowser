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
    protected MainApplication application;
    protected Object shell;
    
    /**
     * each instance of a UIModel class MUST be constructed
     * with the currently running MainApplication
     */
}
