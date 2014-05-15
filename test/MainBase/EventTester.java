/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainBase;

import MainBase.Events.*;

/**
 *
 * @author arthur
 */
public class EventTester implements ModelEventListener{
    private BaseModelUpdatedEvent event;
    
    public EventTester(){
        ModelEventHandler.addModelListener(this);
    }

    @Override
    public void modelAdded(ModelAddedEvent e) {
        event = e;
    }

    @Override
    public void modelRemoved(ModelRemovedEvent e) {
        event = e;
    }

    @Override
    public void modelChanged(ModelChangedEvent e) {
        event = e;
    }
    
    public BaseModelUpdatedEvent getEvent(){
        return event;
    }
    
    public void clearEvent(){
        event = null;
    }
    
    public boolean eventTriggered(){
        return event != null;
    }
    
}
