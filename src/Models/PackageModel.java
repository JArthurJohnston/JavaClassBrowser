/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.NameAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Arthur
 */
public class PackageModel extends ProjectModel {
    private ArrayList<ClassModel> classList;
    /*
     * at this level the packageList variable is used to store
     * top-level classes only
     */
    
    public PackageModel(){
        this.name = "default package";
        classes = new HashMap();
        packages = new HashMap();
        packageList = new ArrayList();
        classList = new ArrayList();
    }
    
    public PackageModel(ProjectModel parent, String name){
        classes = new HashMap();
        packages = new HashMap();
        packageList = new ArrayList();
        classList = new ArrayList();
        this.name = name;
    }
    
    /**
     *
     */
    @Override
    protected void setUpDataStructures(){
        super.setUpDataStructures();
        classList = new ArrayList();
    }
    
    public ClassModel addClass(String newClassName)throws NameAlreadyExistsException{
        if(super.okToAddClass(newClassName)){
            ClassModel newClass = new ClassModel(newClassName);
            this.addClass(newClass);
            return newClass;
        }else {
            throw new NameAlreadyExistsException(newClassName, this);
        }
    }
    
    @Override
    protected ClassModel addClass (ClassModel newClass){
        if(newClass.isTopLevel()) {
            this.classList.add(newClass);
        }
        super.addClass(newClass);
        return newClass;
    }
    
    
    
    public ArrayList<ClassModel> classList(){
        return classList;
    }
    
}
