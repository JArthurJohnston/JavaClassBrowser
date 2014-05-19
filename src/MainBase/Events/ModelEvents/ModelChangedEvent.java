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
public class ModelChangedEvent extends BaseModelUpdatedEvent{

    public ModelChangedEvent(BaseModel source) {
        super(source);
    }
    
    @Override
    public boolean isChange(){
        return true;
    }
}
