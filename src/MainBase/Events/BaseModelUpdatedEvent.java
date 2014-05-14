/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainBase.Events;

import Models.BaseModel;
import java.util.EventObject;

/**
 *
 * @author arthur
 */
public class BaseModelUpdatedEvent extends EventObject{
    BaseModel updatedModel;

    public BaseModelUpdatedEvent(BaseModel source, BaseModel updatedModel){
        super(source);
        this.updatedModel = updatedModel;
    }
    
    public BaseModel getModel(){
        return updatedModel;
    }
    
    public boolean isChange(){
        return false;
    }
    public boolean isAdd(){
        return false;
    }
    public boolean isRemove(){
        return false;
    }
    
}
