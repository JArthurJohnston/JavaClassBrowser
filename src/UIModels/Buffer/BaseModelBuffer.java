/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import MainBase.SortedList;
import Models.BaseModel;
import Models.ProjectModel;
import Types.ClassType;
import Types.ScopeType;

/**
 * Created to hold changes and check their
 * validity before saving them to a model.
 * 
 * @author arthur
 */
public class BaseModelBuffer {
    protected BaseModel entity;
    protected SortedList fields;
    protected boolean isValid;
    
    protected String name;
    protected ScopeType scope;
    protected boolean isFinal;
    protected ClassType type;
    
    public BaseModelBuffer(){
        this.isValid = true;
    }
    
    public BaseModelBuffer(BaseModel aModel){
        this();
        this.entity = aModel;
        this.name = aModel.name();
    }
    
    protected SortedList testFields(){
        return new SortedList().addElm(name);
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
            return;
        if(!aName.isEmpty()
                || !ProjectModel.getReservedWords().contains(aName))
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
    
    public String getName(){
        if(this.name == null)
            this.name = new String();
        return this.name;
    }
    public ScopeType getScope(){
        if(this.scope == null)
            this.scope = ScopeType.NONE;
        return this.scope;
    }
    public ClassType getType(){
        return this.type;
    }
    public boolean isFinal(){
        return isFinal;
    }
    public String getSourceString(){
        return this.getEntity().toSourceString();
    }
    
}
