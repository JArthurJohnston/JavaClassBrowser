/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.ClassDoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Exceptions.PackageDoesNotExistException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Arthur
 */
public class PackageModel extends ProjectModel {
    private ArrayList<PackageModel> packageList;
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
    
    /**
     * Default Project Constructor
     * Constructor used to add a top-level package to a project.
     * It should only be called from a ProjectModel
     * 
     * Note:I know overridable calls in the constructor are bad, but in this case
     * it cuts down on repitition. always make sure its called lase in the constructor
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
    
    @Override
    protected void setUpFields(){
        packageList = new ArrayList();
        classList = new ArrayList();
    }
    
    @Override
    protected boolean okToAddPackage(String newPackageName){
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
        classList.add(newClass);
        return newClass;
    }
    
    
    public ClassModel removeClass(String aClassName) throws ClassDoesNotExistException{
        if(!this.okToAddClass(aClassName)){
            return this.removeClass(project.getClasses().get(aClassName));
        }else {
            throw new ClassDoesNotExistException(this, aClassName);
        }
    }
    
    public ClassModel removeClass (ClassModel aClass){
        if(aClass.parent == this) {
            this.classList.remove(aClass);
        }
        project.getClasses().remove(aClass.name());
        return aClass;
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
            myClassList.add(c);
            myClassList.addAll(c.getClassList());
        }
        for(PackageModel p : this.packageList){
            myClassList.addAll(p.getClassList());
        }
        return myClassList;
    }
    
    @Override
    public ArrayList<PackageModel> getPackageList(){
        return packageList;
    }
    public ProjectModel getProject(){
        return project;
    }
    public ProjectModel getParent(){
        return parent;
    }
    
    
}
