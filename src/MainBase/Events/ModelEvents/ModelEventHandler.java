/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainBase.Events.ModelEvents;

import MainBase.Events.BaseEventHandler;

/**
 * 
 * @author arthur
 */
public class ModelEventHandler extends BaseEventHandler{
    
    public static void addListener(ModelEventListener l){
        getList().add(ModelEventListener.class, l);
    }
    
    public static void removeListener(ModelEventListener l){
        listeners.remove(ModelEventListener.class, l);
        if(isEmpty())
            listeners = null;
    }
    
    public static void fireEvent(BaseModelUpdatedEvent e){
        for(ModelEventListener l : getList().getListeners(ModelEventListener.class)){
            if(e.isAdd())
                l.modelAdded((ModelAddedEvent)e);
            if(e.isChange())
                l.modelChanged((ModelChangedEvent)e);
            if(e.isRemove())
                l.modelRemoved((ModelRemovedEvent)e);
        }
    }
}
