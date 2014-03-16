/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.DoesNotExistException;
import Exceptions.MethodDoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Exceptions.PackageDoesNotExistException;
import Exceptions.VeryVeryBadException;
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
    }
    public ProjectModel(String name){
        this.name = name;
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
     * @param className
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
     * takes in a package and checks to see if a package with that name
     * and path already exists in the project.
     * 
     * @param newPackage the package the user wants to add to the project
     * @return PackageModel, the package being added to the project
     * @throws NameAlreadyExistsException 
     */
    public PackageModel addPackage(PackageModel newPackage) throws NameAlreadyExistsException{
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
     * 
     * @param ClassModel the class being added to the project
     * @return ClassModel the class being added to the project
     */
    public ClassModel addClass(ClassModel newClass) throws NameAlreadyExistsException{
        if(this.okToAddClass(newClass.name())){
            classes.put(newClass.name(), newClass);
            return newClass;
        }else {
            throw new NameAlreadyExistsException(this, newClass);
        }
    }
    
    /**
     * If the package does indeed exist, it will be removed
     * 
     * @param aPackage the package being removed
     * @return the package being removed
     * @throws PackageDoesNotExistException 
     */
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
    
    protected ClassModel removeClass(ClassModel aClass) throws VeryVeryBadException{
        if(!classes.containsKey(aClass.name()))
            throw new VeryVeryBadException(this, aClass);
        return (ClassModel)classes.remove(aClass.name());
    }
    
    //static methods
    public static String getSelectionString(){
        return "Project";
    }
    
    /**
     * Generates and returns a LinkedList of every class
     * in a project, or every class in a package, or every subclass 
     * in a class' heirarchy.
     * 
     * @return LinkedList of ClassModels
     */
    public LinkedList getClassList(){
        LinkedList classList = new LinkedList();
        for(PackageModel p : packageList){
            classList.addAll(p.getClassList());
        }
        return classList;
    }
    
    /**
     * adds a method to the project's method hash, or adds it to 
     * an existing methods list of references or definitions
     * 
     * @param newMethod the method being added
     * @return the method being added
     */
    public MethodModel addMethodDefinition(MethodModel newMethod){
        if(methods.containsKey(newMethod.name())){
            methods.get(newMethod.name()).addMethod(newMethod);
        }else{
            methods.put(newMethod.name(), new MethodContainer(newMethod));
        }
        return newMethod;
    }
    
    /**
     * returns a LinkedList of method definitions
     * @param aMethod 
     * @return a LinkedList of MethodModels
     * @throws MethodDoesNotExistException 
     */
    public LinkedList getMethodDefinitions(MethodModel aMethod){
        MethodContainer method = methods.get(aMethod.name());
        if(method != null){
            return method.getDefinitions();
        }
        return new LinkedList();
    }
    
    /**
     * returns a LinkedList of method references
     * @param aMethod
     * @return a LinkedList of MethodModels
     * @throws MethodDoesNotExistException 
     */
    public LinkedList getMethodReferences(MethodModel aMethod) {
        MethodContainer method = methods.get(aMethod.name());
        if(method != null){
            return method.getReferences();
        }
        return new LinkedList();
    }
    
    public MethodModel removeMethod(MethodModel aMethod) throws DoesNotExistException{
        MethodContainer existingMethod = methods.get(aMethod.name());
        if(existingMethod == null)
            throw new DoesNotExistException(this, aMethod);
        MethodModel found = null;
        for(MethodModel m : existingMethod.getDefinitions()){
            if(m == aMethod){
                found = m;
                break;
            }
        }
        if(found != null)
            existingMethod.getDefinitions().remove(found);
        found = null;
        for(MethodModel m : existingMethod.getReferences()){
            if(m == aMethod){
                found = m;
                break;
            }
        }
        if(found != null)
            existingMethod.getDefinitions().remove(found);
        return aMethod;
    }
    
    
    /**
     * a wrapper class for containing a list of methods
     * and their definitions and references.
     */
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
        
        public LinkedList<MethodModel> getReferences(){
            return references;
        }
        
        public LinkedList<MethodModel> getDefinitions(){
            return definitions;
        }
        
        public MethodModel addMethod(MethodModel newMethod){
            definitions.add(newMethod);
            references.add(newMethod);
            return newMethod;
        }
            /*
            renaming means removing a definition and a reference
            if those are the only definitions and references, the
            method needs to be removed from the hash
            AND the re-named method has to be added to the hash (if 
            it does not already exist)
            if theres already a method in the hash with that name
            the re-named method must be added to its
            definitions/references.
            
            alternatively, a method rename = a method add. leaving the original
            in place.
            */
    }
}
