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
public class ClassModel extends BaseModel{
    private PackageModel parentPackage;
    private ArrayList<MethodModel> methods;
     
    public ClassModel(){
        
    }
    
    public ClassModel(PackageModel parentPackage, String name){
        this.parentPackage = parentPackage;
        this.methods = new ArrayList();
        this.name = name;
    }
    
    public ArrayList methods(){
        return methods;
    }
}
