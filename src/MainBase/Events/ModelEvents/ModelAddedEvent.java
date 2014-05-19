/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainBase.Events.ModelEvents;

import Models.BaseModel;

/**
 *
 * @author arthur
 */
public class ModelAddedEvent extends BaseModelUpdatedEvent{

    public ModelAddedEvent(BaseModel source, BaseModel updatedModel) {
        super(source, updatedModel);
    }
    
    @Override
    public boolean isAdd(){
        return true;
    }
    
}
