/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainBase.Events.ModelEvents;

import MainBase.Events.BaseEvent;
import Models.BaseModel;

/**
 *
 * @author arthur
 */
public class BaseModelUpdatedEvent extends BaseEvent{
    BaseModel updatedModel;
    
    protected BaseModelUpdatedEvent(BaseModel source){
        super(source);
    }

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
