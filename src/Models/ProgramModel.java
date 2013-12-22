/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;


import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Arthur
 */
public class ProgramModel {
    public static int numberOfProjects = 0;
    public static String defaultName = "New Project" + ProgramModel.numberOfProjects;
    
    
    public String name;
    private ProjectModel project;
    public HashMap <String, ClassModel> classes;
    private HashMap <String, PackageModel> packages;
     
    /**
     * checks the classes set to ensure it does not already
     * contain a class with the same name as the class
     * being added.
     * @param newClass
     * @return 
     */
    public boolean okToAddClass(ClassModel newClass){
        if(classes.containsKey(newClass.name()))
            return false;
        return true;
        //Todo:
    }
    public void addClass(ClassModel newClass){
        if(this.okToAddClass(newClass)){
            classes.put(newClass.name(), newClass);
        }
    }
    public boolean OkToDeleteClass(ClassModel someClass){
        return classes.containsValue(someClass);
    }
    public void deleteClass(ClassModel someClass){
        if (this.OkToDeleteClass(someClass))
            classes.remove(someClass.name());
        /*
         * its a bit wonky to check if the class is there by its value
         * and delete it by its key. but so long as oktoAdd works as expected
         * it shouldnt be a problem.
         */
    }
    
    public boolean okToAddPackage(PackageModel newPackage){
        return !packages.containsKey(newPackage.name());
    }
    public void addPackage(PackageModel newPackage){
        if(this.okToAddPackage(newPackage))
            packages.put(newPackage.name(), newPackage);
    }
    
}
