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
    public static PackageModel defaultPackage = new PackageModel();
    
    /**
     * default constructor should not be called anywhere
     */
    public PackageModel(){
        name = PackageModel.defaultPackageName;
    }
    public PackageModel(String name){
        this.name = name;
    }
    
    public String name(){
        return name;
    }
    
    
}
