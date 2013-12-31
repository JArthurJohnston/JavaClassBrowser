/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Types.ClassType;
import Types.ScopeType;
import java.util.ArrayList;

/**
 *
 * @author Arthur
 */
public class ClassModel extends PackageModel{
    //parent here means the class's package
    protected ScopeType scope;
    private ArrayList<MethodModel> methods;
    private ArrayList<MethodModel> inheritedMethods;
    //at this level, the classList variable is used to hold onto subclasses
     
    public ClassModel(){}
    
    public ClassModel(PackageModel parent, String name){
        this.project = parent.getProject();
        this.parent = parent;
        this.name = name;
        this.classList = new ArrayList();
        this.scope = ScopeType.PUBLIC;
    }
    
    /*
     * Getters
     */
    public ArrayList<MethodModel> inheritableMethods(){
        ArrayList<MethodModel> inhMethods = new ArrayList();
        for(MethodModel m : methods){
            
        }
        return inhMethods;
    }
    
    public ArrayList instanceMethods(){
        ArrayList classMethods = new ArrayList();
        for(MethodModel m : methods){
            if(m.getType() == ClassType.INSTANCE) {
                classMethods.add(m);
            }
        }
        return classMethods;
    }
    public ArrayList classMethods(){
        ArrayList classMethods = new ArrayList();
        for(MethodModel m : methods){
            if(m.getType() == ClassType.CLASS) {
                classMethods.add(m);
            }
        }
        return classMethods;
    }
}
