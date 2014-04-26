/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import MainBase.UsefulList;
import Models.BaseModel;

/**
 * Created to hold changes and check their
 * validity before saving them to a model.
 * 
 * @author arthur
 */
public class BaseModelBuffer {
    protected BaseModel entity;
    protected String name;
    protected UsefulList fields;
    protected boolean isValid;
    
    public BaseModelBuffer(){
        this.isValid = true;
    }
    
    public BaseModelBuffer(BaseModel aModel){
        this();
        this.entity = aModel;
        this.name = aModel.name();
    }
    
    public BaseModelBuffer newFrom(BaseModel aModel){
        return this;
    } 
    
    protected UsefulList testFields(){
        return new UsefulList().addElm(name);
    }
    
    public boolean isValid(){
        for(Object o : this.testFields())
            if(o == null)
                return false;
        if(this.name.isEmpty())
            return false;
        return isValid;
    }
    
    public BaseModel saveChanges(){
        if(this.isValid())
            return null;
        return this.getEntity();
    }
    
    public BaseModel getEntity(){
        return entity;
    }
    
    public void setName(String aName){
        if(aName == null)
            this.name = null;
        else if(aName.isEmpty())
            this.name = aName;
        else
            this.name = this.removeSemicolon(aName);
    }
    
    protected String removeSemicolon(String aString){
        if(aString.charAt(aString.length()-1) == ';')
            return aString.substring(0, aString.length()-1);
        return aString;
    }
    
    public void saveToModel(){
        if(this.isValid())
            this.getEntity().setName(name);
    }
    
}