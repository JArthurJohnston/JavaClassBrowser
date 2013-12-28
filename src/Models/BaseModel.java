/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Arthur
 */
public class BaseModel extends BasePathModel{
    protected String name;
    protected Boolean hasChange;
    protected final String defaultName = "DefaultName";
    protected Boolean isDefault = false;
    public static final ClassModel defaultParentClass = new ClassModel();
    public static final PackageModel defaultPackage = new PackageModel();
    
    //static methods
    
    
    //getters
    public String name(){
        return name;
    }
    @Override
    public String path(){
        return super.path() + this.path;
    }
    public boolean hasChange(){
        return hasChange;
    }
    public String defaultName(){
        return defaultName;
    }
    public Boolean isDefault(){
        return isDefault;
    }
    //setters
    public void setName(String name){
        this.name = name;
    }
    public void setPath(String path){
        this.path = path;
    }
    public void isDefault(Boolean isDefault){
        this.isDefault = isDefault;
    }
    @Override
    public String toString(){
        return this.name;
    }
}
