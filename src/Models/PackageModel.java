/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.AlreadyExistsException;
import Exceptions.PackageDoesNotExistException;
import Exceptions.VeryVeryBadException;
import MainBase.MainApplication;
import java.util.LinkedList;

/**
 *
 * @author Arthur
 */
public class PackageModel extends ProjectModel {
    protected ProjectModel parent;
    protected LinkedList<ClassModel> classList;
    
    private static final String DEFAULT_PACKAGE_NAME = "default package";
    /*
     * at this level the packageList variable is used to store
     * top-level classes only
     */
    
    /**
     * Default constructor
     * Should not be called AT ALL!
     */
    protected PackageModel(){
        packageList = new LinkedList();
        classList = new LinkedList();
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
     * @param parent 
     */
    public PackageModel(ProjectModel parent){
        this();
        this.parent = parent;
        this.name = DEFAULT_PACKAGE_NAME;
    }
    
    /**
     * New Sub Package Constructor
     * Used to add a PackageModel to an existing PackageModel
     * Should only be called from a PackageModel
     * @param name String
     */
    public PackageModel(String name){
        this();
        this.name = name;
    }
    
    @Override
    protected MainApplication getMain(){
        return this.getProject().getMain();
    }
    
    @Override
    public PackageModel addPackage(PackageModel newPackage) throws AlreadyExistsException{
        if(newPackage.getParent() == null){
            newPackage.setParent(this);
            this.packageList.add(newPackage);
        }
        this.fireAdded(this, this.getProject().addPackage(newPackage));
        return newPackage;
    }
    
    @Override
    public ClassModel addClass(ClassModel newClass) throws AlreadyExistsException{
        this.getProject().addClass(newClass);
        if(newClass.parent == null){
            classList.add(newClass);
            newClass.setParent(this);
        }
        this.fireAdded(this, newClass);
        return newClass;
    }
    
    public void rename(String newName) throws AlreadyExistsException{
        //write me
    }
    
    /**
     * used to accept a class that is being moved from a package to this
     * package
     * @param aClass the class being moved
     * @return the class being moved
     */
    public ClassModel adoptClass(ClassModel aClass){
        aClass.setParent(this);
        this.classList.add(aClass);
        fireAdded(this, aClass);
        return aClass;
    }
    
    @Override
    public ClassModel removeClass (ClassModel aClass) throws VeryVeryBadException{
        if(aClass.parent == this){
            if(!this.classList.remove(aClass))
                throw new VeryVeryBadException(false, aClass);
            fireRemoved(this, aClass);
        }
        return this.getProject().removeClass(aClass);
    }
    
    protected ClassModel classMoved(ClassModel aClass) throws VeryVeryBadException{
        if(this.classList.remove(aClass)){
            //need to add aClass's subClasses, and ONLT its subclasses DAMMIT!!!
            fireRemoved(this, aClass);
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
            fireRemoved(this, aPackage);
            return this.getProject().removePackage(aPackage);
        }else {
            throw new PackageDoesNotExistException(this, aPackage);
        }
    }
    
    /*
     * Getters
     */
    @Override
    public LinkedList<ClassModel> getClassList(){
        LinkedList myClassList = new LinkedList();
        for(PackageModel p : this.packageList){
            myClassList.addAll(p.getClassList());
        }
        for(ClassModel c : classList){
            myClassList.addAll(c.getClassList());
        }
        return myClassList;
    }
    
    public LinkedList<ClassModel> getTopLevelClasses(){
        return classList;
    } 
    
    @Override
    public LinkedList<PackageModel> getPackageList(){
        LinkedList aList = new LinkedList();
        aList.add(this);
        for(PackageModel p : packageList)
            aList.addAll(p.getPackageList());
        return aList;
    }
    
    @Override
    public ProjectModel getProject(){
        return parent.getProject();
    }
    
    public ProjectModel getParent(){
        return parent;
    }
    
    public void setParent(ProjectModel aProjectOrPackage){
        this.parent = aProjectOrPackage;
        fireChanged(this);
    }
    
    @Override
    public boolean isPackage(){
        return true;
    }
    
    public boolean isDefault(){
        return this.name.compareTo(DEFAULT_PACKAGE_NAME) == 0;
    }
    
    @Override
    public boolean contains(BaseModel aModel){
        if(this.getProject().contains(aModel))
            if(aModel.isClass() || aModel.isPackage() || aModel.isMethod())
                return ((PackageModel)aModel).getParentPackage() == this;
        return false;
    }
}
