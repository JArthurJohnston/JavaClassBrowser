/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import Models.BaseModel;

/**
 * Created to hold changes and check their
 * validity before saving them to a model.
 * 
 * @author arthur
 */
public class BaseModelBuffer {
    protected BaseModel entity;
    
    public BaseModelBuffer(){}
    
    public BaseModelBuffer(BaseModel aModel){
        this.entity = aModel;
    }
    
    public BaseModelBuffer newFrom(BaseModel aModel){
        return this;
    } 
    
    public boolean isValid(){
        return true;
    }
    public void saveChanges(){
        if(this.isValid())
            return;
    }
    
    public BaseModel getEntity(){
        return entity;
    }
    
}
