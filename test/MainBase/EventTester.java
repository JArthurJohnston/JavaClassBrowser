/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainBase;

import MainBase.Events.ModelEvents.ModelAddedEvent;
import MainBase.Events.ModelEvents.ModelChangedEvent;
import MainBase.Events.ModelEvents.BaseModelUpdatedEvent;
import MainBase.Events.ModelEvents.ModelEventHandler;
import MainBase.Events.ModelEvents.ModelRemovedEvent;
import MainBase.Events.ModelEvents.ModelEventListener;

/**
 *
 * @author arthur
 */
public class EventTester implements ModelEventListener{
    private BaseModelUpdatedEvent event;
    
    public EventTester(){
        ModelEventHandler.addListener(this);
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
