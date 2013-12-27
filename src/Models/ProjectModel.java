/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An object for keeping track of all the classes in a single project
 * @author Arthur
 */
public class ProjectModel extends BaseModel {
    //private variables
    private HashMap <String, ClassModel> classes;
    private ArrayList<ClassModel> classList;
    private HashMap <String, PackageModel> packages;
    private ArrayList<ClassModel> packageList;
    
    //Constructors
    public ProjectModel(){
        this.setUpDataStructures();
        this.name = defaultName;
        isDefault = true;
    }
    public ProjectModel(String name){
        this.setUpDataStructures();
        this.name = name;
    }
    
    protected void setUpDataStructures(){
        classes = new HashMap();
        packages = new HashMap();
        classList = new ArrayList();
        packageList = new ArrayList();
    }
    /*
     * #todo: throw errors when not ok to add
     */
    public PackageModel addPackage(PackageModel newPackage){
        if(this.okToAddPackage(newPackage.name())){
            packages.put(newPackage.name(), newPackage);
            return newPackage;
        }
        return null;
    }
    private boolean okToAddPackage(String packageName){
        return !packages.containsKey(packageName);
    }
    
    public void addClass(ClassModel newClass){
        if(this.okToAddClass(newClass.name()))
            classes.put(newClass.name(), newClass);
    }
    
    public boolean okToAddClass(String className){
        return !classes.containsKey(className);
    }
    
    public ArrayList classes(){
        return classList;
    }
    public ArrayList packages(){
        return packageList;
    }
}
