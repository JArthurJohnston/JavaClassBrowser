/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Arthur
 */
public class ProgramModel {
    private static int numberOfProjects;
    /*
     * #notes: search the project folder for files called "new project"
     * increment a counter when on is found, repeat.
     */
    private static String defaultName = "New Project" + numberOfProjects;
    
    
    public String name;
    public HashMap <String, ClassModel> classes;
    /*
     * #Notes
     * each class and package needs to keep track of the scope of its elements
     * list worldMethods    methods available to the world
     * list packageMethods  methods only available to the package
     * list classMethods    methods only available to the class
     * same with vars
     */
    private HashMap <String, PackageModel> packages;
        //same, hashes here, lists in project
     
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
        //#Todo:
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
        //these methods should probably be on ProjectModel
        return !packages.containsKey(newPackage.name());
    }
    public void addPackage(PackageModel newPackage){
        if(this.okToAddPackage(newPackage))
            packages.put(newPackage.name(), newPackage);
    }
    
    public String toSourceString(){
        //Subclass Responsibility
        return "";
    }
}
