/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.NameAlreadyExistsException;
import Exceptions.PackageDoesNotExistException;
import MainBase.MainApplication;
import java.util.ArrayList;
import java.util.Date;
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
    protected Date dateCreated;
    private String userName;
    
    /*
     * Constructors
     */
    
    //default constructor is for testing purposes.
    public ProjectModel(){
        this.name = defaultName;
        isDefault = true;
        this.setUpFields();
    }
    
    public ProjectModel(MainApplication main, String name){
        this.userName = main.getUserName();
        this.name = name;
        this.setUpFields();
    }
    
    /*
     * Abstract Methods
     */
    
    @Override
    protected void setUpFields(){
        dateCreated = new Date();
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
    
    @Override
    public boolean isProject(){
        return true;
    }
    
    protected PackageModel getParentPackage(){
        return null;
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
    public Date getDateCreated(){
        return dateCreated;
    }
    
    public String getUserName(){
        return userName;
    }
    
    /*
     * Setters
     */
    public void setUserName(String newUserName){
        this.userName = newUserName;
    }
    
    public String getDescription(){
        return "Project Name: "+this.name+"\n"+
                "Author: "+this.getUserName()+"\n"+
                "Date Created: "+this.getDateCreated().toString()+"\n"+
                "Number of Packages: "+packages.size()+"\n"+
                "Number of Classes: "+classes.size();
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
    protected boolean okToRemovePackage(PackageModel aPackage){
        return this.packages.containsValue(aPackage);
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
        this.packages.put(newPackage.name(), newPackage);
        if(newPackage.getParent() == this) {
            this.packageList.add(newPackage);
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
    
    protected PackageModel removePackage(PackageModel aPackage) throws PackageDoesNotExistException{
        if(this.okToRemovePackage(aPackage)){
            this.packages.remove(aPackage.name());
            if(aPackage.parent == this){
                this.packageList.remove(aPackage);
            }
            return aPackage;
        }else {
            throw new PackageDoesNotExistException(this, aPackage);
        }
    }
    
    //static methods
    public static String getSelectionString(){
        return "Project";
    }
}
