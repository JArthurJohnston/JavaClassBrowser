/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.NameAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * ProjectModel keeps a hash of all classes and packages that have been added
 * to the project. 
 * For fast iteration, it keep a list of all top-level packages
 * and a master list which is generated when the project is saved
 * @author Arthur
 */
public class ProjectModel extends BaseModel {
    //private variables
    private HashMap <String, ClassModel> classes;
    private HashMap <String, PackageModel> packages;
    private ArrayList<PackageModel> packageList;
    private LinkedList<BaseModel> masterList;
    
    
    /*
     * Constructors
     */
    public ProjectModel(){
        this.setUpFields();
        this.name = defaultName;
        isDefault = true;
    }
    
    public ProjectModel(String name){
        this.setUpFields();
        this.name = name;
    }
    
    /*
     * Abstract Methods
     */
    
    @Override
    protected void setUpFields(){
        classes = new HashMap();
        packages = new HashMap();
        packageList = new ArrayList();
        PackageModel defaultPackage = new PackageModel(this);
        packages.put(defaultPackage.name(), defaultPackage);
        packageList.add(defaultPackage);
    }
    @Override
    public String toSourceString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    /*
     * Getters
     */
    public HashMap<String, ClassModel> getClasses(){
        return classes;
    }
    public HashMap<String, PackageModel> getPackages(){
        return packages;
    }
    public ArrayList<PackageModel> getPackageList(){
        return packageList;
    }
    @Override
    public String getPath(){
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /*
     ************************* Logic **********************************
     */
    
    
    /**
     * checks the hash to see if a class with the desired name
     * already exists
     * @param String className
     * @return boolean
     */
    protected boolean okToAddClass(String className){
        return !this.classes.containsKey(className);
    }
    protected boolean okToAddPackage(String packageName){
        return !this.packages.containsKey(packageName);
    }
    /**
     * Checks the packages hash for duplicates, if none
     * it creates a new package and calls addPackage(PackageModel)
     * to have it added to the hash and/or the list
     * 
     * @param String newPackageName
     * @return PageModel newPackage
     * @throws NameAlreadyExistsException 
     */
    public PackageModel addPackage(String newPackageName) throws NameAlreadyExistsException{
        if(this.okToAddPackage(newPackageName)){
            PackageModel newPackage = new PackageModel(this, newPackageName);
            this.addPackage(newPackage);
            return newPackage;
        }else {
            throw new NameAlreadyExistsException(this, newPackageName);
        }
    }
    
    protected PackageModel addPackage(PackageModel newPackage){
        packages.put(newPackage.name(), newPackage);
        if(newPackage.getParent() == this) {
            packageList.add(newPackage);
        }
        return newPackage;
    }
    /**
     * Note: this method should be overridden and called ONLY in the 
     * PackageModel or ClassModel classes. these overridden methods should 
     * then call super.addClass(ClassModel)
     * adds a class to the hash.
     * @param ClassModel newClass
     * @return ClassModel
     */
    protected ClassModel addClass(ClassModel newClass){
        classes.put(newClass.name(), newClass);
        return newClass;
    }
}
