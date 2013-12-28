/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.NameAlreadyExistsException;
import java.util.ArrayList;

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
    
    public PackageModel(){}
    
    public PackageModel(ProjectModel parent, String name){
        this.setUpDataStructures();
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
        if(this.okToAddClass(newClassName)){
            ClassModel newClass = new ClassModel(this, newClassName);
            this.addClass(newClass);
            return newClass;
        }else
            throw new NameAlreadyExistsException(newClassName, this);
    }
    
    @Override
    protected void addClass (ClassModel newClass){
        if(newClass.isTopLevel())
            this.classList.add(newClass);
    }
    
    @Override
    protected boolean okToAddClass(String newClassName){
        return super.okToAddClass(newClassName);
    }
    
    
    public ArrayList<ClassModel> classList(){
        return classList;
    }
    
}
