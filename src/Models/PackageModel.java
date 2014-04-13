/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.NameAlreadyExistsException;
import Exceptions.PackageDoesNotExistException;
import Exceptions.VeryVeryBadException;
import MainBase.MainApplication;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Arthur
 */
public class PackageModel extends ProjectModel {
    private LinkedList<PackageModel> packageList;
    protected ProjectModel project;
    protected ProjectModel parent;
    protected ArrayList<ClassModel> classList;
    /*
     * at this level the packageList variable is used to store
     * top-level classes only
     */
    
    /**
     * Default constructor
     * Should not be called AT ALL!
     */
    public PackageModel(){}
    public PackageModel(String name){
        this.name = name;
    }
    
    /**
     * Default Project Constructor
     * Constructor used to add a top-level package to a project.
     * It should only be called from a ProjectModel
     * 
     * Note:I know overridable calls in the constructor are bad, but in this case
     * it cuts down on repitition. always make sure its called last in the constructor
     * unless absolutely necessary
     * 
     * @param ProjectModel parent 
     */
    public PackageModel(ProjectModel parent){
        this.parent = parent;
        this.project = parent;
        this.name = "default package";
        this.setUpFields();
    }
    
    /**
     * New Package Constructor
     * Constructor used to add a top-level package to a project.
     * It should only be called from a ProjectModel
     * @param parent ProjectModel
     * @param name String
     */
    public PackageModel(ProjectModel parent, String name){
        this.project = parent;
        this.parent = parent;
        this.name = name;
        this.setUpFields();
    }
    
    /**
     * New Sub Package Constructor
     * Used to add a PackageModel to an existing PackageModel
     * Should only be called from a PackageModel
     * @param parent PackageModel
     * @param name String
     */
    public PackageModel(PackageModel parent, String name){
        this.project = parent.getProject();
        this.parent = parent;
        this.name = name;
        this.setUpFields();
    }
    
    protected MainApplication getMain(){
        return project.getMain();
    }
    
    @Override
    protected void setUpFields(){
        packageList = new LinkedList();
        classList = new ArrayList();
    }
    
    @Override
    public boolean okToAddPackage(String newPackageName){
        return project.okToAddPackage(this.name()+"."+newPackageName);
    }
    
    @Override
    protected boolean okToAddClass(String newClassName){
        return project.okToAddClass(newClassName);
    }
    
    @Override
    protected boolean okToRemovePackage(PackageModel aPackage){
        return project.okToRemovePackage(aPackage);
    }
    
    @Override
    public PackageModel addPackage(PackageModel newPackage) throws NameAlreadyExistsException{
        if(newPackage.parent == this) {
            this.packageList.add(newPackage);
        }
        return project.addPackage(newPackage);
    }
    
    @Override
    public ClassModel addClass(ClassModel newClass) throws NameAlreadyExistsException{
        project.addClass(newClass);
        if(newClass.parent == this)
            classList.add(newClass);
        return newClass;
    }
    
    /**
     * used to accept a class that is being moved from a package to this
     * package
     * @param aClass the class being moved
     * @return the class being moved
     */
    public ClassModel adoptClass(ClassModel aClass){
        this.classList.add(aClass);
        return aClass;
    }
    
    @Override
    public ClassModel removeClass (ClassModel aClass) throws VeryVeryBadException{
        if(aClass.parent == this){
            if(!this.classList.remove(aClass))
                throw new VeryVeryBadException(false, aClass);
        }
        return project.removeClass(aClass);
    }
    
    protected ClassModel classMoved(ClassModel aClass) throws VeryVeryBadException{
        if(this.classList.remove(aClass)){
            //need to add aClass's subClasses, and ONLT its subclasses DAMMIT!!!
            return aClass;
        }else
            throw new VeryVeryBadException(this, aClass);
    }
    
    @Override
    public PackageModel removePackage(PackageModel aPackage) throws PackageDoesNotExistException{
        if(this.okToRemovePackage(aPackage)){
            if(aPackage.getParent() == this){
                this.packageList.remove(aPackage);
            }
            return project.removePackage(aPackage);
        }else {
            throw new PackageDoesNotExistException(this, aPackage);
        }
    }
    
    /*
     * Getters
     */
    @Override
    public LinkedList getClassList(){
        LinkedList myClassList = new LinkedList();
        for(ClassModel c : classList){
            myClassList.addAll(c.getClassList());
        }
        for(PackageModel p : this.packageList){
            myClassList.addAll(p.getClassList());
        }
        return myClassList;
    }
    
    @Override
    public LinkedList<PackageModel> getPackageList(){
        return packageList;
    }
    public ProjectModel getProject(){
        return project;
    }
    public ProjectModel getParent(){
        return parent;
    }
    
    @Override
    public boolean isPackage(){
        return true;
    }
}
