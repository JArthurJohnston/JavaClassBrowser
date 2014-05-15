/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.AlreadyExistsException;
import Exceptions.CannotBeDeletedException;
import Exceptions.VeryVeryBadException;
import MainBase.Events.*;
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
    protected final String defaultName = "DefaultName";
    protected LinkedList<String> warnings; // this can be pushed down to ProjectModel
    
    //getters
    public String name(){
        return name;
    }
    public LinkedList<String> getWarnings(){
        if(warnings == null);
            warnings = new LinkedList();
        return warnings;
    }
    public String defaultName(){
        return defaultName;
    }
    public String getDescription(){ //could probably just be combined with comment
        return description;
    }
    public String getComment(){
        return comment;
    }
    //setters

    public void setName(String name){
        this.name = name;
        fireChanged(this);
    }
    public void setPath(String path){
        this.path = path;
        fireChanged(this);
    }
    public void setComment(String comment){
        this.comment = comment;
        fireChanged(this);
        
    }
    public void setDescription(String description){ //see above
        this.description = description;
        fireChanged(this);
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
    public ClassType getType(){ // can probably be pushed down to ClassModel
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
    
    public void fireAdded(BaseModel source, BaseModel target){
        ModelEventHandler.fireEvent(new ModelAddedEvent(source, target));
    }
    public void fireRemoved(BaseModel source, BaseModel target){
        ModelEventHandler.fireEvent(new ModelRemovedEvent(source, target));
    }
    public void fireChanged(BaseModel source){
        ModelEventHandler.fireEvent(new ModelChangedEvent(source));
    }
}
