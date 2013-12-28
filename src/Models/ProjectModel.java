/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.NameAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * An object for keeping track of all the classes in a single project
 * @author Arthur
 */
public class ProjectModel extends BaseModel {
    //private variables
    private HashMap <String, ClassModel> classes;
    private HashMap <String, PackageModel> packages;
    private ArrayList<PackageModel> packageList;
    
    /*
     * #todo:
     * might consider changing the arraylists to trees for logN traversal,
     * insertion and removal.
     */
    
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
        packageList = new ArrayList();
    }
    /*
     * #todo: throw errors when not ok to add
     */
    public PackageModel addPackage(String newPackageName)throws NameAlreadyExistsException{
        if(this.okToAddPackage(newPackageName)){
            PackageModel newPackage = new PackageModel(this, newPackageName);
            packages.put(newPackageName, newPackage);
            packageList.add(newPackage);
            return newPackage;
        }else 
            throw new NameAlreadyExistsException(this, newPackageName);
        
    }
    protected boolean okToAddPackage(String packageName){
        return !packages.containsKey(packageName);
    }
    
    
    protected boolean okToAddClass(String className){
        return !classes.containsKey(className);
    }
    /**
     * Should only be called by a PackageModel
     * @param newClass 
     */
    protected void addClass(ClassModel newClass){
        classes.put(newClass.name(), newClass);
    }
    
    public HashMap classes(){
        return classes;
    }
    public HashMap<String, PackageModel> packages(){
        return packages;
    }
    public ArrayList<PackageModel> packageList(){
        return packageList;
    }
    

}
