/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

/**
 * Made to make JaxB happy.
 * Will be deleted after some re-factoring
 * 
 * @author arthur
 */
public class PrimitiveTypeModel extends ClassModel{
    
    public PrimitiveTypeModel(String name){
        this.name = name;
    }
    
    public String getPath(){
        return "";
    }
    
    public PackageModel getParentPackage(){
        return new PackageModel();
    }
    
    public ProjectModel getProject(){
        return new ProjectModel();
    }
}
