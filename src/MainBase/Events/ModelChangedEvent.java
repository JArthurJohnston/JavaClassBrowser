/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainBase.Events;

import Models.BaseModel;

/**
 *
 * @author arthur
 */
public class ModelChangedEvent extends BaseModelUpdatedEvent{

    public ModelChangedEvent(BaseModel source, BaseModel updatedModel) {
        super(source, updatedModel);
    }
    
    @Override
    public boolean isChange(){
        return true;
    }
}
