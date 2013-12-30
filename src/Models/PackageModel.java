/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;

/**
 *
 * @author Arthur
 */
public class PackageModel extends ProjectModel {
    protected ArrayList<ClassModel> classList;
    /*
     * at this level the packageList variable is used to store
     * top-level classes only
     */
    
    public PackageModel(){
        this.setUpFields();
    }
    /**
     * This constructor is for testing purposes only
     * @param aNameForTesting 
     */
    public PackageModel (String aNameForTesting){
        this.name = aNameForTesting;
    }
    
    public PackageModel(ProjectModel parent){
        this.parent = (ProjectModel)parent;
        this.name = "default package";
        this.setUpFields();
    }
    
    public PackageModel(BaseModel parent, String name){
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
        if(parent.getClass() == ProjectModel.class) {
            return ((ProjectModel)parent).okToAddPackage(this.name+"."+newPackageName);
        }
        return ((PackageModel)parent).okToAddPackage(this.name+"."+newPackageName);
    }
    @Override
    public PackageModel addPackage(PackageModel newPackage){
        
        if (newPackage.parent == this) {
            packageList.add(newPackage);
        }
        if(parent.getClass() == PackageModel.class) {
            ((PackageModel)parent).addPackage(newPackage);
        }
        if(parent.getClass() == ProjectModel.class) {
            ((ProjectModel)parent).addPackage(newPackage);
        }
        return newPackage;
    }
    @Override
    protected boolean okToAddClass(String newClassName){
        if(parent.getClass() == ProjectModel.class) {
            return ((ProjectModel)parent).okToAddClass(newClassName);
        }
        return ((PackageModel)parent).okToAddClass(newClassName);
    }
    
    /**
     * I hate statically typed languages...
     * if the new class' parent is this package it adds it to 
     * the classList, otherwise it tells its parent to add it
     * @param newClass
     * @return 
     */
    @Override
    public ClassModel addClass(ClassModel newClass){
        if(newClass.getParent() == this) {
            classList.add(newClass);
        }
        if(parent.getClass() == PackageModel.class) {
            ((PackageModel)parent).addClass(newClass);
        }
        if(parent.getClass() == ProjectModel.class) {
            ((ProjectModel)parent).addClass(newClass);
        }
        return newClass;
    }
    
    /*
     * Getters
     */
    public ArrayList<ClassModel> getClassList(){
        return classList;
    }
    
}
