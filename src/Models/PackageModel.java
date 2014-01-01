/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.ClassDoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import java.util.ArrayList;

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
    protected PackageModel addPackage(PackageModel newPackage){
        project.addPackage(newPackage);
        this.packageList.add(newPackage);
        return newPackage;
    }
    
    public ClassModel addClass(String newClassName) throws NameAlreadyExistsException{
        if(this.okToAddClass(newClassName)){
            ClassModel newClass = new ClassModel(this, newClassName);
            return project.addClass(this.addClass(newClass));
        }else {
            throw new NameAlreadyExistsException(this, newClassName);
        }
    }
    
    @Override
    protected ClassModel addClass(ClassModel newClass){
        this.classList.add(newClass);
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
            this.getClassList().remove(aClass);
        }
        project.getClasses().remove(aClass.name());
        return aClass;
    }
    
    /*
     * Getters
     */
    public ArrayList<ClassModel> getClassList(){
        return classList;
    }
    public ProjectModel getProject(){
        return project;
    }
    public ProjectModel getParent(){
        return parent;
    }
    
}
