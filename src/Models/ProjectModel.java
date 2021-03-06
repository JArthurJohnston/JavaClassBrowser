/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.AlreadyExistsException;
import Exceptions.CannotBeDeletedException;
import Exceptions.DoesNotExistException;
import Exceptions.PackageDoesNotExistException;
import Exceptions.VeryVeryBadException;
import MainBase.MainApplication;
import MainBase.SortedList;
import Models.MethodSignature;
import Types.SyntaxCharacters;
import UIModels.Buffer.BaseModelBuffer;
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
    private HashMap <String, ClassModel> classes; //this is gettin changed
    private HashMap <MethodSignature, LinkedList<MethodModel>> methodDefinitions;
    private HashMap <String, LinkedList<MethodSignature>> methodNames;
    private HashMap <String, PackageModel> packages;
    private String userName;
    
    protected LinkedList<PackageModel> packageList;
    
    private static HashMap <String, String> RESERVED_WORDS;
    private static HashMap <Character, SyntaxCharacters> SYNTAX_CHARACTERS;
    
    private AllPackage all;
    
    protected Date dateCreated;
    
    public static String DELETE_WARNING = "You are about to delete this project\n"
            + "This operation cannot be undone.\n"
            + "Continue?";
    
    
    
    public ProjectModel(){
        dateCreated = new Date();
        classes = new HashMap();
        packages = new HashMap();
        packageList = new LinkedList();
    }
    
    public ProjectModel(String name){
        this();
        this.initialize();
        this.name = name;
    }
    
    public static HashMap<Character, SyntaxCharacters>getLanguageSymbols(){
        if(SYNTAX_CHARACTERS == null){
                SYNTAX_CHARACTERS = new HashMap();
            for (SyntaxCharacters key : SyntaxCharacters.values()) {
                SYNTAX_CHARACTERS.put(key.getSyntaxCharacter(), key);
            }     
        }
        return SYNTAX_CHARACTERS;
    }
    
    public static HashMap<String, String> getReservedWords(){
        if(RESERVED_WORDS == null){
            RESERVED_WORDS = new HashMap();
            for(String s : new String[]{"return", "enum", "final", "synchronized", 
                "extends", "implements", "static", "default", "interface", "", 
                "try", "catch", "break", "for", "if", "else", "new", "static", 
                "public", "private", "protected", "do", "class", "while", "switch"}) {
                    RESERVED_WORDS.put(s, s);
            }
            for(String s : ClassModel.getPrimitiveTypes())
                RESERVED_WORDS.put(s, s);
        }
        return RESERVED_WORDS;
    }
    
    private void initialize(){
        this.name = defaultName;
        PackageModel defaultPackage = new PackageModel(this);
        packages.put(defaultPackage.name(), defaultPackage);
        packageList.add(defaultPackage);
        methodDefinitions = new HashMap();
        methodNames = new HashMap();
    }
    
    public AllPackage getAllPackage(){
        if(all == null)
            all = new AllPackage(this);
        return all;
    }
    
    public MainApplication getMain(){
        return main;
    }
    
    /*
     * Abstract Methods
     */
    
    @Override
    public String toSourceString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean isProject() {
        return true;
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
        if(dateCreated == null)
            dateCreated = new Date();
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
    public void setUserName(String newUserName) {
        this.userName = newUserName;
        fireChanged(this);
    }
    
    @Override
    public String getDescription() {
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
    public boolean okToAddClass(String className){
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
     * an existing methods list of references or definitions.
     * 
     * This always sets the methods signature to an existing signature
     * in the hash. unless said signature does not exits. Then garbage collection
     * should come along and clean up the old signature that was generated
     * by the method. This is done to ensure object equality when comparing
     * signatures when adding new methods.
     * 
     * @param newMethod the method being added
     * @return the method being added
     * @throws Exceptions.AlreadyExistsException
     */
    public MethodModel addMethod(MethodModel newMethod) throws AlreadyExistsException{
        MethodSignature sig = newMethod.getSignature();
        if(methodNames.containsKey(newMethod.name())){
            for(MethodSignature ms: methodNames.get(newMethod.name())){
                if(ms.equals(sig)){
                    methodDefinitions.get(ms).add(newMethod);
                    newMethod.setSignature(ms);
                    return newMethod;
                }
            }
            methodDefinitions.put(sig, new SortedList().addElm(newMethod));
            methodNames.get(newMethod.name()).add(sig);
            newMethod.setSignature(sig);//could just lazy init this on the method.... maybe...
            return newMethod;
        }
        methodNames.put(newMethod.name(), new SortedList().addElm(sig));
        methodDefinitions.put(sig, new SortedList().addElm(newMethod));
        newMethod.setSignature(sig);
        return newMethod;
    }
    
    /**
     * Returns a list of Method
     * @param methodName
     * @return
     * @throws DoesNotExistException 
     */
    public LinkedList<MethodModel> findMethods(String methodName) throws DoesNotExistException{
        if(methodNames.containsKey(methodName)){
            LinkedList methods = new LinkedList();
            for(MethodSignature ms : methodNames.get(methodName))
                methods.addAll(methodDefinitions.get(ms));
            return methods;
        }
        throw new DoesNotExistException(this, methodName);
    }
    
    /**
     * returns a LinkedList of method definitions
     * @param aMethod 
     * @return a LinkedList of MethodModels
     */
    public LinkedList getMethodDefinitions(MethodModel aMethod){
        return methodDefinitions.get(aMethod.getSignature());
    }
    
    public MethodModel removeMethod(MethodModel aMethod) throws DoesNotExistException{
        if(!methodDefinitions.containsKey(aMethod.getSignature()))
            throw new DoesNotExistException(this, aMethod);
        if(!methodDefinitions.get(aMethod.getSignature()).remove(aMethod))
            throw new DoesNotExistException(this, aMethod);
        if(methodDefinitions.get(aMethod.getSignature()).isEmpty())
            methodDefinitions.remove(aMethod.getSignature());
        
        if(!methodNames.containsKey(aMethod.name()))
            throw new DoesNotExistException(this, aMethod);
        if(!methodNames.get(aMethod.name()).remove(aMethod.getSignature()))
            throw new DoesNotExistException(this, aMethod);
        if(methodNames.get(aMethod.name()).isEmpty())
            methodNames.remove(aMethod.name());
        return aMethod;
    }
    
    @Override
    public ProjectModel getProject(){
        return this;
    }
    
    public void setMain(MainApplication main){
        this.main = main;
        this.userName = main.getUserName();
    }
    
    public ClassModel findClass(String aClassName) throws DoesNotExistException{
        if(classes.get(aClassName) == null)
            if(ClassModel.getPrimitive(aClassName) != null)
                return ClassModel.getPrimitive(aClassName);
        if(classes.get(aClassName) == null)
            throw new DoesNotExistException(this, aClassName);
        return classes.get(aClassName);
    }
    
    public LinkedList<BaseModel> findObjectFromSource(final String source){
        BaseModel reference = null;
        reference = this.classes.get(source);
        if(reference == null)
             this.methodNames.get(source);
        return SortedList.with(reference);
    }
    
    public InterfaceModel findInterface(String name) throws DoesNotExistException{
        if(this.findClass(name).isInterface())
            return (InterfaceModel)this.findClass(name);
        return null;
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

    @Override
    public BaseModelBuffer getBuffer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
