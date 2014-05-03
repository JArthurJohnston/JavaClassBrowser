/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.CannotBeDeletedException;
import Exceptions.VeryVeryBadException;
import Types.ClassType;
import java.util.LinkedList;

/**
 *
 * @author Arthur
 */
public abstract class BaseModel {
    protected String description;
    protected String path;
    protected String comment;
    protected String name;
    protected Boolean hasChange;
    protected final String defaultName = "DefaultName";
    protected LinkedList<String> warnings;
    
    //getters
    public String name(){
        return name;
    }
    public LinkedList<String> getWarnings(){
        if(warnings == null);
            warnings = new LinkedList();
        return warnings;
    }
    public boolean hasChange(){
        return hasChange;
    }
    public String defaultName(){
        return defaultName;
    }
    public String getDescription(){
        return description;
    }
    public String getComment(){
        return comment;
    }
    //setters
    public void setName(String name){
        this.name = name;
    }
    public void setPath(String path){
        this.path = path;
    }
    public void setComment(String comment){
        this.comment = comment;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setChanged(boolean isChange){
        this.hasChange = isChange;
    }
    
    public boolean isClass(){
        return false;
    }
    public boolean isProject(){
        return false;
    }
    public boolean isMethod(){
        return false;
    }
    public boolean isVariable(){
        return false;
    }
    
    public ClassType getType(){
        return null;
    }
    
    @Override
    public String toString(){
        return this.name;
    }
    
    public boolean isPackage(){
        return false;
    }
    
    //Abstract Methods
    abstract public String toSourceString();
    abstract public String getPath();
    abstract public BaseModel remove() throws CannotBeDeletedException,
            VeryVeryBadException;
}
