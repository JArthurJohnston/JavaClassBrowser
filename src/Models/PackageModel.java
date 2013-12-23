/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;

/**
 * Todo: in the future when I make a system browser, thisll be needed
 * for now everything will be done in the ProjectModel
 * @author Arthur
 */
public class PackageModel extends ProgramModel{
    public static String defaultPackageName = "<default package>";
    
    private ProgramModel parent;
    
    
    public PackageModel(ProgramModel parent){
        name = PackageModel.defaultPackageName;
        this.parent = parent;
    }
    public PackageModel(ProgramModel parent, String name){
        this.parent = parent;
        this.name = name;
    }
    
    /*
     * Getters
     */
    public String name(){
        return name;
    }
    
    
}
