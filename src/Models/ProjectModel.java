/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.MethodDoesNotExistException;
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
    private HashMap <String, MethodContainer> methods;
    private LinkedList packageClasses;
    private HashMap <String, PackageModel> packages;
    private ArrayList<PackageModel> packageList;
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
        packageClasses = new LinkedList();
    }
    
    public ProjectModel(MainApplication main, String name){
        this.userName = main.getUserName();
        this.name = name;
        this.setUpFields();
        packageClasses = new LinkedList();
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
        methods = new HashMap();
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
    
    @Override
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
    
    protected PackageModel addPackage(PackageModel newPackage) throws NameAlreadyExistsException{
        if(this.okToAddPackage(newPackage.name())){
            this.packages.put(newPackage.name(), newPackage);
            if(newPackage.getParent() == this) {
                this.packageList.add(newPackage);
            }
            return newPackage;
        }else {
            throw new NameAlreadyExistsException(this, newPackage);
        }
    }
    /**
     * Note: this method should be overridden and called ONLY in the 
     * PackageModel or ClassModel classes. these overridden methods should 
     * then call super.addClass(ClassModel)
     * adds a class to the hash.
     * @param ClassModel newClass
     * @return ClassModel
     */
    protected ClassModel addClass(ClassModel newClass) throws NameAlreadyExistsException{
        if(this.okToAddClass(newClass.name())){
            classes.put(newClass.name(), newClass);
            return newClass;
        }else {
            throw new NameAlreadyExistsException(this, newClass);
        }
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
    
    public LinkedList getPackageClasses(){
        return packageClasses;
    }
    
    public MethodModel addMethod(MethodModel newMethod){
        if(methods.containsKey(newMethod.name())){
            methods.get(newMethod.name()).addMethod(newMethod);
        }else{
            methods.put(newMethod.name(), new MethodContainer(newMethod));
        }
        return newMethod;
    }
    public LinkedList getMethodDefinitions(MethodModel aMethod) throws MethodDoesNotExistException{
        MethodContainer method = methods.get(aMethod.name());
        if(method != null){
            return method.getDefinitions();
        }
        throw new MethodDoesNotExistException(this, aMethod);
    }
    public LinkedList getMethodReferences(MethodModel aMethod) throws MethodDoesNotExistException{
        MethodContainer method = methods.get(aMethod.name());
        if(method != null){
            return method.getReferences();
        }
        throw new MethodDoesNotExistException(this, aMethod);
    }
    
    
    private class MethodContainer{
        private String name;
        private LinkedList definitions;
        private LinkedList references;
        
        public MethodContainer(MethodModel aMethod){
            this.name = aMethod.name();
            this.definitions = new LinkedList();
            this.definitions.add(aMethod);
            this.references = new LinkedList();
            this.references.add(aMethod);
        }
        
        public LinkedList getReferences(){
            return references;
        }
        public LinkedList getDefinitions(){
            return definitions;
        }
        public MethodModel addMethod(MethodModel newMethod){
            definitions.add(newMethod);
            references.add(newMethod);
            return newMethod;
        }
    }
}
