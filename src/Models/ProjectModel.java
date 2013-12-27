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
    private ArrayList<ClassModel> classList;
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
        classList = new ArrayList();
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
    private boolean okToAddPackage(String packageName){
        return !packages.containsKey(packageName);
    }
    
    // addClass() happens at the Package level, which will call super okToAdd...
    public boolean okToAddClass(String className){
        return !classes.containsKey(className);
    }
    /**
     * Should only be called by a PackageModel
     * @param newClass 
     */
    protected ClassModel addClass(ClassModel newClass){
        classes.put(newClass.name(), newClass);
        classList.add(newClass);
        return newClass;
    }
    
    public ArrayList<ClassModel> classes(){
        return classList;
    }
    public ArrayList<PackageModel> packages(){
        return packageList;
    }

}
