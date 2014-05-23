/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.AlreadyExistsException;
import Exceptions.BaseException;
import Exceptions.CannotBeDeletedException;
import Exceptions.PackageDoesNotExistException;
import Exceptions.VeryVeryBadException;
import MainBase.MainApplication;
import MainBase.SortedList;
import Types.ClassType;
import Types.ScopeType;
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
    private MainApplication main;
    private HashMap <String, ClassModel> classes;
    private HashMap <String, LinkedList<MethodModel>> methods;
    private HashMap <String, PackageModel> packages;
    protected LinkedList<PackageModel> packageList;
    private String userName;
    
    private AllPackage all;
    
    protected Date dateCreated;
    
    public static String DELETE_WARNING = "You are about to delete this project\n"
            + "This operation cannot be undone.\n"
            + "Continue?";
    
    
    
    public ProjectModel(){
    }
    
    public ProjectModel(String name){
        this.initialize();
        this.name = name;
    }
    
    
    public static SortedList<String> getReservedWords(){
        return new SortedList()
                    .addElm("return")
                    .addElm("enum")
                    .addElm("final")
                    .addElm("synchronized")
                    .addElm("extends")
                    .addElm("implements")
                    .addElm("static")
                    .addElm("default")
                    .addElm("interface")
                .addElements(ClassModel.getPrimitiveTypes())
                .addElements(ScopeType.getStringValues())
                .addElements(ClassType.getStringValues());
    }
    
    private void initialize(){
        this.name = defaultName;
        dateCreated = new Date();
        classes = new HashMap();
        packages = new HashMap();
        packageList = new LinkedList();
        PackageModel defaultPackage = new PackageModel(this);
        packages.put(defaultPackage.name(), defaultPackage);
        packageList.add(defaultPackage);
        methods = new HashMap();
    }
    
    public AllPackage getAllPackage(){
        if(all == null)
            all = new AllPackage(this);
        return all;
    }
    
    /*
     * Abstract Methods
     */
    
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
    
    public LinkedList<PackageModel> getPackageList(){
        LinkedList aList = new LinkedList();
        for(PackageModel p : packageList)
            aList.addAll(p.getPackageList());
        return aList;
    }
    
    public LinkedList<PackageModel> getTopLevelPackages(){
        return packageList;
    }
    
    @Override
    public String getPath(){
        //file path
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Date getDateCreated(){
        return dateCreated;
    }
    
    public String getUserName(){
        return userName;
    }
    public PackageModel getDefaultPackage(){
        if(!this.packageList.isEmpty())
            return packageList.getFirst();
        return null;
    }
    
    /*
     * Setters
     */
    public void setUserName(String newUserName){
        this.userName = newUserName;
        fireChanged(this);
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
    public boolean okToAddPackage(String packageName){
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
     * @throws AlreadyExistsException 
     */
    public PackageModel addPackage(PackageModel newPackage) throws AlreadyExistsException{
        if(this.okToAddPackage(newPackage.name())){
            this.packages.put(newPackage.name(), newPackage);
            if(newPackage.getParent() == null) {
                newPackage.setParent(this);
                this.packageList.add(newPackage);
                fireAdded(this, newPackage);
            }
            return newPackage;
        }else {
            throw new AlreadyExistsException(this, newPackage);
        }
    }
    /**
     * Note: this method should be overridden and called ONLY in the 
     * PackageModel or ClassModel classes. these overridden methods should 
     * then call super.addClass(ClassModel)
     * adds a class to the hash.
     * 
     * @param newClass
     * @return ClassModel the class being added to the project
     * @throws Exceptions.AlreadyExistsException
     */
    public ClassModel addClass(ClassModel newClass) throws AlreadyExistsException{
        if(this.okToAddClass(newClass.name())){
            classes.put(newClass.name(), newClass);
            return newClass;
        }else 
            throw new AlreadyExistsException(this, newClass);
        
    }
    
    /**
     * If the package does indeed exist, it will be removed
     * 
     * @param aPackage the package being removed
     * @return the package being removed
     * @throws PackageDoesNotExistException 
     */
    public PackageModel removePackage(PackageModel aPackage) throws PackageDoesNotExistException{
        if(this.okToRemovePackage(aPackage)){
            this.packages.remove(aPackage.name());
            if(aPackage.parent == this){
                this.packageList.remove(aPackage);
                fireRemoved(this, aPackage);
            }
            return aPackage;
        }else {
            throw new PackageDoesNotExistException(this, aPackage);
        }
    }
    
    public ClassModel removeClass(ClassModel aClass) throws VeryVeryBadException{
        if(!classes.containsKey(aClass.name()))
            throw new VeryVeryBadException(this, aClass);
        return (ClassModel)classes.remove(aClass.name());
    }
    
    
    /**
     * Generates and returns a LinkedList of every class
     * in a project, or every class in a package, or every subclass 
     * in a class' heirarchy.
     * 
     * @return LinkedList of ClassModels
     */
    public LinkedList<ClassModel> getClassList(){
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
            methods.get(newMethod.name()).add(newMethod);
        }else{
            LinkedList newMethodList = new LinkedList();
            newMethodList.add(newMethod);
            methods.put(newMethod.name(), newMethodList);
        }
        return newMethod;
    }
    
    /**
     * returns a LinkedList of method definitions
     * @param aMethod 
     * @return a LinkedList of MethodModels
     */
    public LinkedList getMethodDefinitions(MethodModel aMethod){
        return methods.get(aMethod.name());
    }
    
    public MethodModel removeMethod(MethodModel aMethod) throws BaseException{
        if(!methods.get(aMethod.name()).remove(aMethod))
            throw new VeryVeryBadException(this, aMethod);
        if(methods.get(aMethod.name()).isEmpty())
            methods.remove(aMethod.name());
        return aMethod;
        /*
        need to add logic to warn the user if theyre removing a method with references
        */
    }
    
    @Override
    public ProjectModel getProject(){
        return this;
    }
    
    protected MainApplication getMain(){
        return main;
    }
    
    public void setMain(MainApplication main){
        this.main = main;
        this.userName = main.getUserName();
    }
    
    public ClassModel findClass(String aClassName){
        return classes.get(aClassName);
    }
    
    public PackageModel findPackage(String aPackageName){
        return packages.get(aPackageName);
    }

    @Override
    public BaseModel remove() throws CannotBeDeletedException, VeryVeryBadException {
        /*
        prompt the user if theyre sure they want to delete everything
        this is unrecoverable, etc...
        */
        return this;
    }
    
    @Override
    public boolean contains(BaseModel aModel){
        return aModel.getProject() == this;
    }
    
    public class AllPackage extends PackageModel{
        
        public AllPackage(ProjectModel aProject){
            this.name = "ALL";
            this.parent = aProject;
        }
        
        @Override
        public LinkedList getClassList(){
            return parent.getClassList();
        }

        @Override
        public LinkedList getPackageList(){
            return parent.getPackageList();
        }
        
        @Override
        public LinkedList<PackageModel> getTopLevelPackages(){
            return parent.getTopLevelPackages();
        }
        
        @Override
        public boolean contains(BaseModel aModel){
            return parent.contains(aModel);
        }
        
    }
    
}
